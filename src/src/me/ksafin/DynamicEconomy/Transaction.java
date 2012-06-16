package me.ksafin.DynamicEconomy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Bukkit;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;

import net.milkbowl.vault.economy.Economy;


public class Transaction implements Runnable {
	
	private static Economy economy;
	private ExtrasColour color = new ExtrasColour();
	private ExtrasInventory inv = new ExtrasInventory();
	private FileConfiguration itemConfig;
	private FileConfiguration config;
	private File itemsFile;
	private static Logger log = Logger.getLogger("Minecraft");
	
	public static FileConfiguration regionConfigFile;
	
    File confFile;
    
    static NumberFormat f = NumberFormat.getNumberInstance(Locale.US);
    
	public static DecimalFormat decFormat = (DecimalFormat)f;
	public static DecimalFormat changeFormat = (DecimalFormat)f;
	
	

	
	public Transaction(Economy eco, FileConfiguration conf, FileConfiguration confMain, File iFile, File cf) {
		economy = eco;
		itemConfig = conf;
		config = confMain;
		confFile = cf;
		itemsFile = iFile;
	}
	
	public Transaction( FileConfiguration conf, File iFile) {
		itemConfig = conf;
		itemsFile = iFile;
	}
	
	public boolean buy(Player player, String[] args) {
		String stringPlay = player.getName();
		
		if ((args.length == 0) || (args.length > 2)) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/buy [Item] (Amount)");
			   Utility.writeToLog(stringPlay + " incorrectly called /buy");
			return false;
		} 
		
		boolean withinRegion = true;
		
		double tax = DynamicEconomy.purchasetax;
		String bannedItem;
		
		
		String[] itemInfo = new String[7];
		
		try {
			itemInfo = Item.getAllInfo(args[0]);
		} catch (Exception e) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You entered the command arguments in the wrong order, or your item name was invalid ");
			Utility.writeToLog(stringPlay + " entered an invalid item, or entered command arguments in the wrong order");
			return false;
		}
			String itemName = itemInfo[0];
			double itemPrice = Double.parseDouble(itemInfo[1]);
			double itemFloor = Double.parseDouble(itemInfo[2]);
			double itemCeiling = Double.parseDouble(itemInfo[3]);
			double itemVelocity = Double.parseDouble(itemInfo[4]);
			int itemStock = Integer.parseInt(itemInfo[5]);
			long itemID = Long.parseLong(itemInfo[6]);
			
			
			if (DynamicEconomy.useRegions) {
				Location loc = player.getLocation();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				
				withinRegion = regionUtils.withinRegion(x, y, z);
				
				if (withinRegion == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.notWithinRegion);
					Utility.writeToLog(stringPlay + " called /buy outside of an economy region.");
					return false;
				}
				
				if (DynamicEconomy.useRegionFlags) {
					
				String reg = regionUtils.getRegion(x, y, z);
				String node = "regions." + reg + ".flags";
				tax = DynamicEconomy.regionConfig.getDouble(node + ".purchasetax");
				
				
				String[] regionBannedItems = DynamicEconomy.regionConfig.getString(node + ".banned-purchase-items","").split(",");
				
				for (int i = 0; i < regionBannedItems.length; i++) {
					bannedItem = Item.getTrueName(regionBannedItems[i]);
					if (bannedItem.equals(itemName)) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.bannedInRegion);
						Utility.writeToLog(stringPlay + " attempted to buy the banned item: " + bannedItem);
						return false;
					}
				}
				
				if (DynamicEconomy.groupControl) {
					List<String> allowedGroups = DynamicEconomy.regionConfig.getStringList(node + ".allowed-purchase-groups");
					boolean inRegion = Item.isItemInRegionGroup(allowedGroups,itemName);
				
					if (inRegion == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You cannot buy &f" + itemName + "&2 in this region.");
						Utility.writeToLog(stringPlay + " attempted to buy " + itemName + " in region " + reg + ", but it's not in any allowed item groups.");
						return false;
					}
			    }
			}
	      }	
			
		if (DynamicEconomy.groupControl) {
			if(Item.canBuy(player, itemName) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You are not permitted to purchase &f" + itemName);
				Utility.writeToLog(stringPlay + " tried to purchase " + itemName + " but was denied access.");
				return false;
			}
		}
			
		if (itemName.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to buy the non-existent item '" + itemName + "'");

			return false;
		}
		
		
		
		for (int x = 0; x < DynamicEconomy.bannedPurchaseItems.length; x++) {
			bannedItem = Item.getTrueName(DynamicEconomy.bannedPurchaseItems[x]);
			if (bannedItem.equals(itemName)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.bannedItem);
				Utility.writeToLog(stringPlay + " attempted to buy the banned item: " + bannedItem);
				return false;
			}
		}
		
		
		
		int purchaseAmount = 0;
		
		if (args.length == 1) {
			purchaseAmount = DynamicEconomy.defaultAmount;
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("all")) {
			 if (DynamicEconomy.usestock) {
				purchaseAmount = itemStock;
			 } else {
				 color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.cannotBuyAll);
				 Utility.writeToLog(stringPlay + " attempted to buy 'all' of '" + itemName + "', but stock is disabled.");
			 }
			} else {
				try {
					purchaseAmount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.invalidCommandArgs);
					Utility.writeToLog(stringPlay + " entered an invalid purchase amount, or entered command arguments in the wrong order.");
					return false;
				}
			
			}
		}
		
		if (purchaseAmount <= 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.negativeBuyAmount);
			 Utility.writeToLog(stringPlay + " attempted to buy " + purchaseAmount + " of '" + itemName + "', but this amount is invalid.");
			 return false;
		}
		
		double balance = economy.getBalance(player.getName());
		
		if (DynamicEconomy.useboundaries != true) {
			itemFloor = .01;
			itemCeiling = 1000000000;
		}
		
		
		double totalCost = 0;
		double oldPrice = itemPrice;
		
		if (DynamicEconomy.usePercentVelocity) {
			//totalCost = itemPrice * purchaseAmount;
			//itemPrice = itemPrice + (itemPrice * itemVelocity);
		/*	for (int x = 0; x < purchaseAmount; x++) {
				totalCost += itemPrice;	
				if (itemPrice == itemCeiling) {
					itemPrice = itemCeiling;
				} else {
					itemPrice = itemPrice + (itemPrice * itemVelocity);
					if (itemPrice > itemCeiling) {
						itemPrice = itemCeiling;
					}
				}
				
				//System.out.println(x + ": Price - $" + itemPrice +", totalCost - " + totalCost);
			} */
			for (int x = 0; x < purchaseAmount; x++) {
				totalCost += itemPrice;
				itemPrice = itemPrice / (Math.pow((1 - itemVelocity), purchaseAmount));
			
				if (itemPrice > itemCeiling) {
				itemPrice = itemCeiling;
				}
				if (itemPrice < itemFloor) {
				itemPrice = itemFloor;
				}
			}
			
		} else {
			for (int x = 0; x < purchaseAmount; x++) {
				totalCost += itemPrice;	
				if (itemPrice == itemCeiling) {
					itemPrice = itemCeiling;
				} else {
					itemPrice = itemPrice + itemVelocity;
					if (itemPrice > itemCeiling) {
						itemPrice = itemCeiling;
					}
				}
				
				//System.out.println(x + ": Price - $" + itemPrice +", totalCost - " + totalCost);
			} 
		}
		
		
		double percentTax = tax * 100;		
		tax *= totalCost;
		totalCost += tax;
		
		if (DynamicEconomy.depositTax == true) {
			try {
				economy.depositPlayer(DynamicEconomy.taxAccount, tax);
			} catch (Exception e) {
				log.info("Tax-Account " + DynamicEconomy.taxAccount + " not found.");
				Utility.writeToLog("Attempted to deposit tax of " + DynamicEconomy.currencySymbol + tax + " to account " + DynamicEconomy.taxAccount + " but account not found.");
			}
		}
		
		
		double newPrice = itemPrice;
		
		decFormat.applyPattern("#.##");
		
		if (balance < totalCost) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.notEnoughMoney);
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Your balance: &f" + DynamicEconomy.currencySymbol + decFormat.format(balance) + "   &2Your order total: &f" + DynamicEconomy.currencySymbol + decFormat.format(totalCost));
			Utility.writeToLog(stringPlay + " attempted to buy " + decFormat.format(purchaseAmount) + " of '" + itemName + "' for " + decFormat.format(totalCost) + " but could not afford it.");
			return false;
		} else if (DynamicEconomy.usestock) {
			if (itemStock < purchaseAmount) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.notEnoughStock);
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Your request: &f" + decFormat.format(purchaseAmount) + "   &2Current Stock: &f" + itemStock);
				Utility.writeToLog(stringPlay + " attempted to buy " + decFormat.format(purchaseAmount) + " of '" + itemName + "' but there was only " + itemStock + " remaining");
				return false;
			}
		}
		
			economy.withdrawPlayer(player.getName(), totalCost);
			
			String requestPrice = itemName + ".price";
			double change = newPrice - oldPrice;
			
			
			
			itemConfig.set(requestPrice,newPrice);
			int changeStock = 0;
			
			if (DynamicEconomy.usestock) {
				String requestStock = itemName + ".stock";
				int newStock = itemStock - purchaseAmount;
				changeStock = newStock - itemStock;
				itemConfig.set(requestStock,newStock);
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.purchaseSuccess);
			Utility.writeToLog(stringPlay + " bought " + purchaseAmount + " of '" + itemName + "' for " + totalCost);
			
			
			totalCost = Double.valueOf(decFormat.format(totalCost));
			oldPrice = Double.valueOf(decFormat.format(oldPrice));
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fYou bought " + purchaseAmount + " &2of " + itemName + "&f + " + decFormat.format(percentTax) + "&2% tax = &f" + DynamicEconomy.currencySymbol + totalCost + " &2TOTAL" );
			
			changeFormat.applyPattern("#.#####");
			
			newPrice = Double.valueOf(decFormat.format(newPrice));
			change = Double.valueOf(changeFormat.format(change));
			
			
			if (oldPrice != newPrice) { 
				if (DynamicEconomy.globalNotify) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!(Utility.isQuiet(p))) {
						color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f" + DynamicEconomy.currencySymbol + newPrice + "&2 (+" + change + ")");
					}
				} 
				} else if (DynamicEconomy.localNotify) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f" + DynamicEconomy.currencySymbol + newPrice + "&2 (+" + change + ")");
				}
			
				Utility.writeToLog(DynamicEconomy.prefix + " New price of " + itemName + " changed dynamically to " + newPrice + "(+" + change + ")");
			}
			itemConfig.set(itemName + ".buytime", Calendar.getInstance().getTimeInMillis());
			
			
			int mat = 0;
			short dmg = 0;
			byte data = 0;
			
			if (itemID > 3000L) {
				mat = getMat(itemID);
				dmg = getDmg(itemID);
				
				ItemStack items = new ItemStack(mat,purchaseAmount,dmg);
				player.getInventory().addItem(items);
			} else if (itemID == 999) {
				//int totalExp = player.getTotalExperience();
				//float exp = player.getExp();
				float totalExp = player.getTotalExperience();
				//int level = player.getLevel();
				//int level = player.getLevel();
				int newExp = (int)(totalExp + purchaseAmount);
				
				log.info("Player Total Exp In TRANS: " + player.getTotalExperience());
				log.info("Player Level In TRANS: " + player.getLevel());
				log.info("Player Exp In TRANS: " + player.getExp());
				
				player.setTotalExperience(0);
				player.setLevel(0);
				player.setExp(0);
				
				//CraftPlayer cp = (CraftPlayer) player;
				//EntityPlayer ep = cp.getHandle();
				//ep.expTotal = totalExp + purchaseAmount;
				//ep.exp = exp;
				//ep.expLevel = level;
				
				
				//((CraftPlayer)player).getHandle().h(4);
				player.giveExp(newExp);
				//for (int x = 0; x < newExp; x++) {
				//	player.giveExp(1);
				//}
				
				log.info("Player Total Exp In TRANS: " + player.getTotalExperience());
				log.info("Player Level In TRANS: " + player.getLevel());
				log.info("Player Exp In TRANS: " + player.getExp());
			} else {
				inv.addToInventory(player, (int)itemID, purchaseAmount);
			}
			
			
			player.updateInventory();
			
			 try {
				 itemConfig.save(itemsFile);
			 } catch (Exception e) {
				 log.info("[DynamicEconomy] Error saving new item info after /buy execution.");
				 Utility.writeToLog("[DynamicEconomy] Error saving new item info after /buy execution");
				 e.printStackTrace();
			 }
			 
			 
			 dataSigns.checkForUpdates(itemName,changeStock,change);
			
			 return true;
			 
		}
	
	public int getMat(long itemID) {
		String idStr = String.valueOf(itemID);
		String[] split = idStr.split("00");
		int mat = Integer.parseInt(split[0]);
		return mat;
	}
	
	public short getDmg(long itemID) {
		String idStr = String.valueOf(itemID);
		String[] split = idStr.split("00");
		short dmg = Short.parseShort(split[1]);
		return dmg;
	}
	
	public boolean sellInventory(Player player) {
		
		decFormat.setGroupingUsed(false);
		
		Inventory inv = player.getInventory();
		
		ItemStack[] contents = inv.getContents();
		String[] itemInfo = new String[7];
		
		double totalSale = 0;
		String itemName;
		double itemPrice;
		double itemFloor;
		double itemCeiling;
		double itemVelocity;
		int itemStock;
		long itemID;
		int saleAmount;
		
		double percentDur;
		double maxDur = 0;
		double playerDur;
		double indivsale;
		
		double percentTax;
		
		double tax = DynamicEconomy.salestax;
		
		double dur;
		String itemSearchID = "";
		
		int changeStock = 0;
		double oldPrice;
		double change;
		
		ArrayList<String> banned = new ArrayList<String>();
		
		if (DynamicEconomy.useRegions) {
			Location loc = player.getLocation();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			
			boolean withinRegion = regionUtils.withinRegion(x, y, z);
			
			if (withinRegion == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.notWithinRegion);
				Utility.writeToLog(player.getName() + " called /buy outside of an economy region.");
				return false;
			}
			
			if (DynamicEconomy.useRegionFlags) {
			
				
			  String reg = regionUtils.getRegion(x, y, z);
			  String node = "regions." + reg + ".flags";;
			  tax = DynamicEconomy.regionConfig.getDouble(node + ".salestax",0.0);
			
			}
			
		}
		
		
		
		for (int x = 0; x < DynamicEconomy.bannedSaleItems.length; x++) {
			banned.add(DynamicEconomy.bannedSaleItems[x]);
		}
		
		String bannedItem;
		
		Inv:
		for(ItemStack item : contents) {
			
			if (item == null) {
				continue;
			}
			
			dur = item.getDurability();
			itemID = item.getTypeId();
			
			if ((dur == 0) || (((itemID >= 256 ) && (itemID <= 258)) || ((itemID >= 267) && (itemID <= 279)) || ((itemID >= 298) && (itemID <= 317)) || ((itemID >= 283) && (itemID <= 286)) || ((itemID >= 290) && (itemID <= 294)))) {
				itemSearchID = String.valueOf(item.getTypeId());
			} else  {
				itemSearchID = item.getTypeId() + ":" + (int)dur;
			}
			
			
			itemInfo = Item.getAllInfo(itemSearchID);
			
			itemName = itemInfo[0];
			itemPrice = Double.parseDouble(itemInfo[1]);
			itemFloor = Double.parseDouble(itemInfo[2]);
			itemCeiling = Double.parseDouble(itemInfo[3]);
			itemVelocity = Double.parseDouble(itemInfo[4]);
			itemStock = Integer.parseInt(itemInfo[5]);
			itemID = Long.parseLong(itemInfo[6]);
			saleAmount = item.getAmount();
			
			if (DynamicEconomy.groupControl) {
				if(Item.canSell(player, itemName) == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You are not permitted to sell &f" + itemName);
					Utility.writeToLog(player.getName() + " tried to sell " + itemName + " but was denied access.");
					continue;
				}
			}
			
			if (DynamicEconomy.useRegions) {
				Location loc = player.getLocation();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				
				if (DynamicEconomy.useRegionFlags) {
				
				String reg = regionUtils.getRegion(x, y, z);
				String node = "regions." + reg + ".flags";;
				
				String[] regionBannedItems = DynamicEconomy.regionConfig.getString(node + ".banned-sale-items","").split(",");
						
				
				for (int i = 0; i < regionBannedItems.length; i++) {
					bannedItem = Item.getTrueName(regionBannedItems[i]);
					if (bannedItem.equals(itemName)) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.bannedInRegion);
						Utility.writeToLog(player.getName() + " attempted to sell the banned item: " + bannedItem);
						continue Inv;
					}
				}
				
				if (DynamicEconomy.groupControl) {
					List<String> allowedGroups = DynamicEconomy.regionConfig.getStringList(node + ".allowed-sale-groups");
					boolean inRegion = Item.isItemInRegionGroup(allowedGroups,itemName);
				
					if (inRegion == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You cannot sell &f" + itemName + "&2 in this region.");
						Utility.writeToLog(player.getName() + " attempted to sell " + itemName + " in region " + reg + ", but it's not in any allowed item groups.");
						continue Inv;
					}
			    }
			}
			}
			
			oldPrice = itemPrice;
			
			if (banned.contains(itemName)) {
				continue;
			}
			
			if (((itemID >= 256 ) && (itemID <= 258)) || ((itemID >= 267) && (itemID <= 279)) || ((itemID >= 298) && (itemID <= 317)) || ((itemID >= 283) && (itemID <= 286)) || ((itemID >= 290) && (itemID <= 294))) {
				itemPrice -= itemVelocity;	
				
				playerDur = item.getDurability();
					
					String itemMCname = item.getType().toString();
					maxDur = Item.getMaxDur(itemMCname);
					
					playerDur = maxDur - playerDur;
					percentDur = playerDur / maxDur;
					
					totalSale += itemPrice * percentDur;
			}  else {
				if (DynamicEconomy.usePercentVelocity) {
					for (int x = 0; x < saleAmount; x++) {
						itemPrice = itemPrice * (Math.pow((1-itemVelocity),saleAmount));
					
						if (itemPrice > itemCeiling) {
							itemPrice = itemCeiling;
						}
						if (itemPrice < itemFloor) {
							itemPrice = itemFloor;
						}
					
						totalSale += itemPrice;
					}
				} else {
					for (int x = 0; x < saleAmount; x++) {
						if (itemPrice == itemFloor) {
							itemPrice = itemFloor;
						} else {
							itemPrice = itemPrice - itemVelocity;
							if (itemPrice < itemFloor) {
								itemPrice = itemFloor;
							}
						}
						totalSale += itemPrice;
						
					}
				}
			} 
			
			
			if (DynamicEconomy.usestock) {
				String requestStock = itemName + ".stock";
				int newStock = itemStock + saleAmount;
				itemConfig.set(requestStock,newStock);
				changeStock = newStock - itemStock;
			}
			
			change = itemPrice - oldPrice;
			
			String requestPrice = itemName + ".price";
			itemConfig.set(requestPrice,itemPrice);
			
			dataSigns.checkForUpdates(itemName,changeStock,change);
			
			inv.removeItem(item);
			
		}
			
		percentTax = tax * 100;
		tax *= totalSale;
			
			if (DynamicEconomy.depositTax == true) {
				try {
					economy.depositPlayer(DynamicEconomy.taxAccount, tax);
				} catch (Exception e) {
					log.info("Tax-Account " + DynamicEconomy.taxAccount + " not found.");
					Utility.writeToLog("Attempted to deposit tax of " + DynamicEconomy.currencySymbol + tax + " to account " + DynamicEconomy.taxAccount + " but account not found.");
				}
			}
				
				economy.depositPlayer(player.getName(), totalSale);
				
				
				
				
				
				
				
				color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.saleSuccess);
				
				
					player.updateInventory();
					totalSale = Double.valueOf(decFormat.format(totalSale));
					
					totalSale -= tax;
					
					
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fYou sold&f your inventory &2 - " + decFormat.format(percentTax) + "&2% tax = &f" + DynamicEconomy.currencySymbol + totalSale + " &2TOTAL" );
				
				
				Utility.writeToLog(DynamicEconomy.prefix + player.getName() + " sold his entire inventory for " + totalSale);
				
				
				
				
				
				
				 try {
					 itemConfig.save(itemsFile);
				 } catch (Exception e) {
					 log.info("[DynamicEconomy] Error saving new item info after /sell execution.");
					 Utility.writeToLog("[DynamicEconomy] Error saving new item info after /sell execution");
					 e.printStackTrace();
				 }
			
		player.updateInventory();
		return true;
	}

	
public boolean sell(Player player, String[] args) {
		String stringPlay = player.getName();
		
		if ((args.length == 0) || (args.length > 2)) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/sell [Item] (Amount)");
			Utility.writeToLog(stringPlay + " incorrectly called /sell");
			return false;
		} 
		
		if ((args[0].equalsIgnoreCase("inventory")) && (args.length == 1)) {
			sellInventory(player);
			return true;
		}
		
		String[] itemInfo = new String[7];
		
		try {
			if (!((args.length == 1) && (args[0].equals("hand")))) {
				itemInfo = Item.getAllInfo(args[0]);
			} else {
				ItemStack handItem = player.getInventory().getItemInHand();
				
				int dur = handItem.getDurability();
				int id = handItem.getTypeId();
				
				log.info(id + ":" + dur);
				
				String name = "";
				
				if (dur == 0) {
					name = String.valueOf(id);
				} else {
					name = id + ":" + dur;
				}
				
				log.info("Name: " + name);
				
				itemInfo = Item.getAllInfo(name);
				
			}
		} catch (Exception e) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You entered the command arguments in the wrong order, or your item name was invalid ");
			Utility.writeToLog(stringPlay + " entered an invalid item, or entered command arguments in the wrong order");
			return false;
		}
			String itemName = itemInfo[0];
			double itemPrice = Double.parseDouble(itemInfo[1]);
			double itemFloor = Double.parseDouble(itemInfo[2]);
			double itemCeiling = Double.parseDouble(itemInfo[3]);
			double itemVelocity = Double.parseDouble(itemInfo[4]);
			int itemStock = Integer.parseInt(itemInfo[5]);
			long itemID = Long.parseLong(itemInfo[6]);
			
			String spreadRequest = itemName + ".spread";
			double itemSpread = itemConfig.getDouble(spreadRequest,itemVelocity);
			
			double tax = DynamicEconomy.salestax;
			String bannedItem;
			
			if (DynamicEconomy.useRegions) {
				Location loc = player.getLocation();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				
				boolean withinRegion = regionUtils.withinRegion(x, y, z);
				
				if (withinRegion == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.notWithinRegion);
					Utility.writeToLog(stringPlay + " called /buy outside of an economy region.");
					return false;
				}
				
				if (DynamicEconomy.useRegionFlags == true) {
				
				
				String reg = regionUtils.getRegion(x, y, z);
				String node = "regions." + reg + ".flags";;
				tax = DynamicEconomy.regionConfig.getDouble(node + ".salestax");
				
				
				String[] regionBannedItems = DynamicEconomy.regionConfig.getString(node + ".banned-sale-items","").split(",");
						
				
				for (int i = 0; i < regionBannedItems.length; i++) {
					bannedItem = Item.getTrueName(regionBannedItems[i]);
					if (bannedItem.equals(itemName)) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.bannedInRegion);
						Utility.writeToLog(stringPlay + " attempted to sell the banned item: " + bannedItem);
						return false;
					}
				}
				
				if (DynamicEconomy.groupControl) {
					List<String> allowedGroups = DynamicEconomy.regionConfig.getStringList(node + ".allowed-sale-groups");
					boolean inRegion = Item.isItemInRegionGroup(allowedGroups,itemName);
				
					if (inRegion == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You cannot sell &f" + itemName + "&2 in this region.");
						Utility.writeToLog(stringPlay + " attempted to sell " + itemName + " in region " + reg + ", but it's not in any allowed item groups.");
						return false;
					}
			    }
			  }
			}
			
			
			
			if (DynamicEconomy.groupControl) {
				if(Item.canSell(player, itemName) == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You are not permitted to sell &f" + itemName);
					Utility.writeToLog(stringPlay + " tried to sell " + itemName + " but was denied access.");
					return false;
				}
			}
			
			
		if (itemName.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to sell the non-existent item '" + itemName + "'");
			return false;
		}
		
		
		for (int x = 0; x < DynamicEconomy.bannedSaleItems.length; x++) {
			bannedItem = Item.getTrueName(DynamicEconomy.bannedSaleItems[x]);
			if (bannedItem.equals(itemName)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.bannedItem);
				Utility.writeToLog(stringPlay + " attempted to sell the banned item: " + bannedItem);
				return false;
			}
		}
		
		
		int saleAmount = 0;
		boolean isAll = false;
		


		int mat = 0;
		short dmg = 0;
		byte data = 0;
		ItemStack saleItem;
		int userAmount = 0;
			
		
		if (args.length == 1) {
			if (args[0].equals("hand")) {
				saleAmount = player.getInventory().getItemInHand().getAmount();
				
				if (saleAmount == 0) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You have no item in your hand.");
					Utility.writeToLog(stringPlay + " called /sell hand, but had no item in hand.");
					return false;
				}
				
			} else {
				saleAmount = DynamicEconomy.defaultAmount;
			}
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("all")){
				//saleAmount = inv.getAmountOf(player, itemID);
				if (itemID > 3000L) {
					mat = getMat(itemID);
					dmg = getDmg(itemID);
					
					saleItem = new ItemStack(mat,saleAmount,dmg);
					saleAmount = inv.getAmountOfDataValue(player,saleItem);
				} else if (itemID == 999 ){
					saleAmount = player.getTotalExperience();
				} else {
					saleAmount = inv.getAmountOf(player,(int)itemID);
				}
				
				isAll = true;
			} else {
				try {
					saleAmount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.invalidCommandArgs);
					Utility.writeToLog(stringPlay + " entered an invalid purchase amount, or entered command arguments in the wrong order.");
					return false;
				}
			}
		}
		
		if (saleAmount <= 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.negativeSellAmount);
			 Utility.writeToLog(stringPlay + " attempted to sell " + saleAmount + " of '" + itemName + "', but this amount is invalid.");
			 return false;
		}
		
		double balance = economy.getBalance(player.getName());
		
		if (DynamicEconomy.useboundaries != true) {
			itemFloor = .01;
			itemCeiling = 1000000000;
		}
		
		double totalSale = 0;
		double oldPrice = itemPrice;
		
		
		
		if (DynamicEconomy.usePercentVelocity) {
			for (int x = 0; x < saleAmount; x++) {
				itemPrice = itemPrice * (Math.pow((1-itemVelocity),saleAmount));
			
				if (itemPrice > itemCeiling) {
					itemPrice = itemCeiling;
				}
				if (itemPrice < itemFloor) {
					itemPrice = itemFloor;
				}
			
				totalSale += itemPrice;
			}
		} else {
			for (int x = 0; x < saleAmount; x++) {
				if (itemPrice == itemFloor) {
					itemPrice = itemFloor;
				} else {
					itemPrice = itemPrice - itemVelocity;
					if (itemPrice < itemFloor) {
						itemPrice = itemFloor;
					}
				}
				totalSale += itemPrice;
				
			}
		}
		
				
		
		
		double newPrice = itemPrice;
		
		
		//int playerItemAmount = inv.getAmountOf(player, itemID);
		
		if (args[0].equalsIgnoreCase("hand")) {
			userAmount = player.getInventory().getItemInHand().getAmount();
		} else if (itemID > 3000L) {
			mat = getMat(itemID);
			dmg = getDmg(itemID);
			
			saleItem = new ItemStack(mat,saleAmount,dmg);
			userAmount = inv.getAmountOfDataValue(player,saleItem);
		} else if (itemID == 999 ){
				userAmount = player.getTotalExperience();
		} else {
			userAmount = inv.getAmountOf(player,(int)itemID);
		}
		
		//boolean containsItem = player.getInventory().contains(saleItem);
		
		if (isAll) {
			//saleAmount = userAmount - 1;
			if (saleAmount <= 0) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You have no &f" + itemName);
				Utility.writeToLog(stringPlay + " attempted to sell all of their " + itemName + ", but had none.");
				return false;
			}
		}
		
		if ((saleAmount > userAmount) && !(isAll)) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You do not have &f" + saleAmount + " " + itemName);
			Utility.writeToLog(stringPlay + " attempted to sell " + saleAmount + " of " + itemName + ", but didn't have that many.");
			return false;
		}
		
		double percentTax = tax * 100;	
		tax *= totalSale;
		totalSale -= tax;
		
		if (DynamicEconomy.depositTax == true) {
			try {
				economy.depositPlayer(DynamicEconomy.taxAccount, tax);
			} catch (Exception e) {
				log.info("Tax-Account " + DynamicEconomy.taxAccount + " not found.");
				Utility.writeToLog("Attempted to deposit tax of " + DynamicEconomy.currencySymbol + tax + " to account " + DynamicEconomy.taxAccount + " but account not found.");
			}
		}
			
			economy.depositPlayer(player.getName(), totalSale);
			
			String requestPrice = itemName + ".price";
			double change = newPrice - oldPrice;
			
			
			
			itemConfig.set(requestPrice,newPrice);
			
			int changeStock = 0;
			
			if (DynamicEconomy.usestock) {
				String requestStock = itemName + ".stock";
				int newStock = itemStock + saleAmount;
				itemConfig.set(requestStock,newStock);
				changeStock = newStock - itemStock;
			}
			
			
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.saleSuccess);
			
			
			
			double percentDur;
			double maxDur;
			double playerDur;
			double indivsale;
			
			
			decFormat.applyPattern("#.##");
			
			if (((itemID >= 256 ) && (itemID <= 258)) || ((itemID >= 267) && (itemID <= 279)) || ((itemID >= 298) && (itemID <= 317)) || ((itemID >= 283) && (itemID <= 286)) || ((itemID >= 290) && (itemID <= 294))) {
				totalSale = 0;
				
				for (int x = 0; x < saleAmount; x++) {
					
					ItemStack itemStack = new ItemStack((int)itemID);
					String itemMCname = itemStack.getType().toString();
					maxDur = Item.getMaxDur(itemMCname);
					
					int slot = player.getInventory().first((int)itemID);
					ItemStack playerItem = player.getInventory().getItem(slot);
					playerDur = playerItem.getDurability();
					
					playerDur = maxDur - playerDur;
					
					percentDur = playerDur / maxDur;
					
					totalSale += itemPrice * percentDur;
					indivsale = itemPrice * percentDur;
					
					player.getInventory().clear(slot);
					
					totalSale = Double.valueOf(decFormat.format(totalSale));
					itemPrice = Double.valueOf(decFormat.format(itemPrice));
					percentDur *= 100;
					percentDur = Double.valueOf(decFormat.format(percentDur));
					indivsale = Double.valueOf(decFormat.format(indivsale));
					
					
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&f1 &2" + itemName + "&f with " + percentDur + "% &2durability = &2"+ DynamicEconomy.currencySymbol + indivsale);
					//color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fYou sold &2" + saleAmount + "&f of &2" + itemName + "&f - " + percentTax + "&2% tax = &f$" + totalSale + " &2TOTAL" );
					Utility.writeToLog(stringPlay + " sold a '" + itemName + "' at " + percentDur + "% durability for " + indivsale);
				}
				percentTax = tax * 100;
				tax = DynamicEconomy.salestax * totalSale;
				totalSale -= tax;
				totalSale = Double.valueOf(decFormat.format(totalSale));
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2TOTAL SALE (with " + decFormat.format(percentTax) + "% tax): &f" + DynamicEconomy.currencySymbol + totalSale );
				
			} else {
				
				
				ItemStack i;
				Material material;
				
				if (args[0].equalsIgnoreCase("hand")) {
					i = player.getInventory().getItemInHand();
					player.getInventory().removeItem(i);
				} else if (itemID > 3000L) {
					mat = getMat(itemID);
					dmg = getDmg(itemID);
					
					i = new ItemStack(mat,saleAmount,dmg);
					player.getInventory().removeItem(i);
				} else if (itemID == 999) {
					float exp = player.getTotalExperience();
					int newExp = (int)(exp - saleAmount);
					
					player.setTotalExperience(0);
					player.setLevel(0);
					player.setExp(0);
					player.giveExp(newExp);
				} else {
					material = Material.getMaterial((int)itemID);
					i = new ItemStack(material,saleAmount);
					player.getInventory().removeItem(i);
				}
				
				//ItemStack i = new ItemStack(itemID,saleAmount);
				//Material mat = Material.getMaterial(itemID);
				//removeInventoryItems(player.getInventory(),mat,saleAmount);
				
				
				
				//player.getInventory().removeItem(i);
				player.updateInventory();
				totalSale = Double.valueOf(decFormat.format(totalSale));
				oldPrice = Double.valueOf(decFormat.format(oldPrice));
				newPrice = Double.valueOf(decFormat.format(newPrice));
				
				
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fYou sold &2" + saleAmount + "&f of &2" + itemName + "&f - " + decFormat.format(percentTax) + "&2% tax = &f" + DynamicEconomy.currencySymbol + totalSale + " &2TOTAL" );
				Utility.writeToLog(stringPlay + " succesfully sold " + saleAmount + " of '" + itemName + "' for " + totalSale);
			}
			
			changeFormat.applyPattern("#.#####");
			
			newPrice = Double.valueOf(decFormat.format(newPrice));
			change = Double.valueOf(changeFormat.format(change));
			
			if (oldPrice != newPrice) { 
				if (DynamicEconomy.globalNotify) {
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (!(Utility.isQuiet(p))) {
							color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f" + DynamicEconomy.currencySymbol + newPrice + "&2 (" + change + ")");
						}
					} 
				} else if (DynamicEconomy.localNotify) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f" + DynamicEconomy.currencySymbol + newPrice + "&2 (" + change + ")");
				}
			
				Utility.writeToLog(DynamicEconomy.prefix + " New price of " + itemName + " changed dynamically to " + newPrice + "(" + change + ")");
			}
			itemConfig.set(itemName + ".selltime", Calendar.getInstance().getTimeInMillis());
			
			//inv.removeFromInventory(player, itemID, saleAmount);
			
			
			
			
			
			 try {
				 itemConfig.save(itemsFile);
			 } catch (Exception e) {
				 log.info("[DynamicEconomy] Error saving new item info after /sell execution.");
				 Utility.writeToLog("[DynamicEconomy] Error saving new item info after /sell execution");
				 e.printStackTrace();
			 }
			 
			 
			 
			
			 dataSigns.checkForUpdates(itemName,changeStock,change);
		
		return true;
		
	}

public static void removeInventoryItems(Inventory inv, Material type, int amount) {
    for (ItemStack is : inv.getContents()) {
        if (is != null && is.getType() == type) {
            int newamount = is.getAmount() - amount;
            if (newamount > 0) {
                is.setAmount(newamount);
                break;
            } else {
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) break;
            }
        }
    }
}

 	public void addStock(Player player, String[] args) {
 		String stringPlay = player.getName();
 		if ((args.length < 2) || (args.length > 2)) {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/addStock [Item] [AdditionalStock]");
 		   Utility.writeToLog(stringPlay + " incorrectly called /addstock");
 		} else {
 			int addStock = Integer.parseInt(args[1]);
 			String item = Item.getTrueName(args[0]);
 			
 			String request = item + ".stock";
 			
 			int currentStock = itemConfig.getInt(request,0);
 			int newStock = currentStock;
 			
 		    newStock = currentStock + addStock;
 			
 			
 			itemConfig.set(request, newStock);
 			
 			Item.saveItemFile();
 			
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.stockAdded);
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Previous Stock: &f" + currentStock + "&2 | New Stock: &f" + newStock);
 		    Utility.writeToLog(stringPlay + " added " + addStock + " stock of " + item + " for a new stock total of " + newStock);
 		   dataSigns.checkForUpdates(item, (newStock - currentStock), 0);
 			
 		}
 		
 	}
 	
 	public void removeStock(Player player, String[] args) {
 		String stringPlay = player.getName();
 		if ((args.length < 2) || (args.length > 2)) {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/addStock [Item] [AdditionalStock]");
 			Utility.writeToLog(stringPlay + " incorrectly called /removestock");
 		} else {
 			int removeStock = Integer.parseInt(args[1]);
 			String item = Item.getTrueName(args[0]);
 			
 			String request = item + ".stock";
 			
 			int currentStock = itemConfig.getInt(request,0);
 			int newStock = currentStock;
 			
 		    newStock = currentStock - removeStock;
 			
 			
 			itemConfig.set(request, newStock);
 			
 			Item.saveItemFile();
 			
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  Messages.stockRemoved);
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Previous Stock: &f" + currentStock + "&2 | New Stock: &f" + newStock);
 			Utility.writeToLog(stringPlay + " removed " + removeStock + " stock of " + item + " for a new stock total of " + newStock);
 			dataSigns.checkForUpdates(item, (newStock - currentStock), 0);
 			
 		}
 		
 	}
	
	public void setConfig(FileConfiguration conf) {
		config = conf;
	}
	
	public void curTaxes(Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length > 0) {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/curTaxes");
 			Utility.writeToLog(stringPlay + " incorrectly called /curTaxes");
 		} else {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fSales Tax: &2" + DynamicEconomy.salestax * 100 + "%");
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&fPurchase Tax: &2" + DynamicEconomy.purchasetax * 100 + "%");
 			Utility.writeToLog(stringPlay + " called /curtaxes");
 		}
		
		
	}
	
	public void setTaxes(Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length != 3) {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/settax [region] [sale|purchase] [amount]");
 			Utility.writeToLog(stringPlay + " incorrectly called /settax");
 		} else {
 			String region = args[0].toUpperCase();
 			Double tax = 0.0;
 			try {
 				tax = Double.parseDouble(args[2]);
 			} catch (Exception e) {
 				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2 " + args[2] + "&f% is an invalid amount.");
 	 			return;
 			}
 			
 			if ((args[1].equalsIgnoreCase("sale") == false) && (args[1].equalsIgnoreCase("purchase") == false)) {
 				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2This is an invalid tax name. Use either &fsale or &fpurchase");
 	 			Utility.writeToLog(stringPlay + " tried to set tax '" + args[1] + "', which doesn't exist.");
 	 			return;
 			}
 			
 			FileConfiguration conf;
 			File file;
 			
 			if (region.equalsIgnoreCase("GLOBAL")) {
 				if (args[1].equalsIgnoreCase("sale")) {
 					config.set("salestax", tax);
 				} else if (args[1].equalsIgnoreCase("purchase")) {
 					config.set("purchasetax", tax);
 				}
 				conf = config;
 				file = confFile;
 			} else {
 				
 				if(DynamicEconomy.regionConfig.contains("regions." + region) == false) {
 					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Region &f" + region + "&2 doesn't exist.");
 	 	 			Utility.writeToLog(stringPlay + " tried to set tax of region '" + region + "', which doesn't exist.");
 	 	 			return;
 				}
 				
 				
 				String node = "regions." + region + ".flags";
 				if (args[1].equalsIgnoreCase("sale")) {
 					node += ".salestax";
 				} else if (args[1].equalsIgnoreCase("purchase")) {
 					node += ".purchasetax";
 				}
 				DynamicEconomy.regionConfig.set(node, tax);
 				conf = DynamicEconomy.regionConfig;
 				file = DynamicEconomy.regionFile;
 			}
 			
 			try {
 				conf.save(file);
 			} catch (Exception e) {
 				log.info("[DynamicEconomy] Error saving config in /settax");
 				e.printStackTrace();
 			}
 			
 			
 			decFormat.applyPattern("###.###");
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2" + args[0] + " " + args[1] + "tax set to &f" + decFormat.format(tax*100) + "%");
 			Utility.writeToLog(stringPlay + " set " + region + " " + args[1] + "tax to " + decFormat.format(tax * 100));
 			DynamicEconomy.relConfig();
 		}
	}
	
	public void run() {
		try{
			itemConfig.load(itemsFile);
		} catch (Exception e) {
			Utility.writeToLog("Error loading items.yml");
		}
		
		Set<String> itemsSet = itemConfig.getKeys(false);
		//ConfigurationSection root = itemConfig.getConfigurationSection("STONE").getParent();
		
		//log.info(root.toString());
		
		Object[] itemsObj = (itemsSet.toArray());
		String[] items = new String[itemsObj.length];
		
		for (int i = 0; i < items.length; i++) {
			items[i] = itemsObj[i].toString();
		}
		
		long buyTime;
		long sellTime;
		double price;
		long period = DynamicEconomy.overTimePriceChangePeriod * 60 * 1000;
		
		double floor;
		double ceiling;
		
		for (int x = 0; x < items.length; x++) {
			buyTime = itemConfig.getLong(items[x] + ".buytime");
			sellTime = itemConfig.getLong(items[x] + ".selltime");
			long buyDifference = Calendar.getInstance().getInstance().getTimeInMillis() - buyTime;
			long sellDifference = Calendar.getInstance().getInstance().getTimeInMillis() - sellTime;
			
			price = itemConfig.getDouble(items[x] + ".price");
			floor = itemConfig.getDouble(items[x] + ".floor");
			ceiling = itemConfig.getDouble(items[x] + ".ceiling");
			
			if (DynamicEconomy.enableOverTimePriceDecay) {
			if ((buyDifference >= period) && (buyTime != 0)) {
				price = price - (price * DynamicEconomy.overTimePriceDecayPercent);
				itemConfig.set(items[x] + ".price",price);
				
				decFormat.applyPattern("#.##");
				
				if (DynamicEconomy.globalNotify) {
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (!(Utility.isQuiet(p))) {
							price = Double.valueOf(decFormat.format(price));
							color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + items[x] + "&2 is &f" + DynamicEconomy.currencySymbol + price + "&2 ( -" + (DynamicEconomy.overTimePriceDecayPercent * 100 ) + "% )");
						}
					} 
				}
				itemConfig.set(items[x] + ".buytime",Calendar.getInstance().getTimeInMillis());
			}
		}
			
			if (DynamicEconomy.enableOverTimePriceInflation) {
				if ((sellDifference >= period) && (sellTime != 0)) {
					price = price + (price * DynamicEconomy.overTimePriceInflationPercent);
					itemConfig.set(items[x] + ".price",price);
					
					decFormat.applyPattern("#.##");
					
					if (DynamicEconomy.globalNotify) {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							if (!(Utility.isQuiet(p))) {
								price = Double.valueOf(decFormat.format(price));
								color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + items[x] + "&2 is &f" + DynamicEconomy.currencySymbol + price + "&2 ( +" + (DynamicEconomy.overTimePriceInflationPercent * 100 ) + "% )");
							}
						} 
					}
					itemConfig.set(items[x] + ".selltime",Calendar.getInstance().getTimeInMillis());
				}
			}
			
			
		}
		
		try {
			itemConfig.save(itemsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//try {
			//this.wait(DynamicEconomy.overTimePriceDecayPeriodCheck * 60 * 20);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		
	}
	
	public Transaction() {
		
	}
	
}
