package me.ksafin.DynamicEconomy;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Bukkit;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;

import me.smickles.DynamicMarket.Invoice;
import net.milkbowl.vault.economy.Economy;

public class Transaction {
	
	private static Economy economy;
	private ExtrasColour color = new ExtrasColour();
	private ExtrasInventory inv = new ExtrasInventory();
	private FileConfiguration itemConfig;
	private FileConfiguration config;
	private File itemsFile;
	private static Logger log = Logger.getLogger("Minecraft");
	
	public static FileConfiguration regionConfigFile;
	
    File confFile;
    
	public static DecimalFormat decFormat = new DecimalFormat("#.##");
	public static DecimalFormat changeFormat = new DecimalFormat("#.#####");

	
	public Transaction(Economy eco, FileConfiguration conf, FileConfiguration confMain, File iFile, File cf) {
		economy = eco;
		itemConfig = conf;
		config = confMain;
		confFile = cf;
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
		
		if (DynamicEconomy.useRegions) {
			Location loc = player.getLocation();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			
			withinRegion = regionUtils.withinRegion(x, y, z);
			
			if (withinRegion == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are not within an economy region!");
				Utility.writeToLog(stringPlay + " called /buy outside of an economy region.");
				return false;
			}
			
		}
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
			int itemID = Integer.parseInt(itemInfo[6]);
			
		if (itemName.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2This item does not exist. ");
			Utility.writeToLog(stringPlay + " attempted to buy the non-existent item '" + itemName + "'");

			return false;
		}
		
		String bannedItem;
		
		for (int x = 0; x < DynamicEconomy.bannedPurchaseItems.length; x++) {
			bannedItem = Item.getTrueName(DynamicEconomy.bannedPurchaseItems[x]);
			if (bannedItem.equals(itemName)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2This item is banned, and not allowed to be purchased in the economy.");
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
				 color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Stock Disabled, cannot use keyword 'all' ");
				 Utility.writeToLog(stringPlay + " attempted to buy 'all' of '" + itemName + "', but stock is disabled.");
			 }
			} else {
				try {
					purchaseAmount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You entered the command arguments in the wrong order, or your amount was invalid. Try again. ");
					Utility.writeToLog(stringPlay + " entered an invalid purchase amount, or entered command arguments in the wrong order.");
					return false;
				}
			
			}
		}
		
		if (purchaseAmount < 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Cannot buy a negative amount!");
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
		
			
		double tax = DynamicEconomy.purchasetax * totalCost;
		totalCost += tax;
		
		if (DynamicEconomy.depositTax == true) {
			try {
				economy.depositPlayer(DynamicEconomy.taxAccount, tax);
			} catch (Exception e) {
				log.info("Tax-Account " + DynamicEconomy.taxAccount + " not found.");
				Utility.writeToLog("Attempted to deposit tax of $" + tax + " to account " + DynamicEconomy.taxAccount + " but account not found.");
			}
		}
		
		
		double newPrice = itemPrice;
		
		if (balance < totalCost) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You do not have enough money to purchase this.");
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Your balance: &f$" + balance + "   &2Your order total: &f$" + totalCost);
			Utility.writeToLog(stringPlay + " attempted to buy " + purchaseAmount + " of '" + itemName + "' for " + totalCost + " but could not afford it.");
			return false;
		} else if (DynamicEconomy.usestock) {
			if (itemStock < purchaseAmount) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2There is not enough stock of this item.");
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Your request: &f" + purchaseAmount + "   &2Current Stock: &f" + itemStock);
				Utility.writeToLog(stringPlay + " attempted to buy " + purchaseAmount + " of '" + itemName + "' but there was only " + itemStock + " remaining");
				return false;
			}
		}
		
			economy.withdrawPlayer(player.getName(), totalCost);
			
			String requestPrice = itemName + ".price";
			double change = newPrice - oldPrice;
			
			
			
			itemConfig.set(requestPrice,newPrice);
			
			if (DynamicEconomy.usestock) {
				String requestStock = itemName + ".stock";
				int newStock = itemStock - purchaseAmount;
				itemConfig.set(requestStock,newStock);
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Purchase Success!");
			Utility.writeToLog(stringPlay + " bought " + purchaseAmount + " of '" + itemName + "' for " + totalCost);
			
			
			totalCost = Double.valueOf(decFormat.format(totalCost));
			oldPrice = Double.valueOf(decFormat.format(oldPrice));
			double percentTax = DynamicEconomy.purchasetax * 100;			
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&f" + purchaseAmount + " &2" + itemName + "&f @ $" + oldPrice + "&2  + " + percentTax + "% tax = &f$" + totalCost + " &2TOTAL" );
			
			newPrice = Double.valueOf(decFormat.format(newPrice));
			change = Double.valueOf(changeFormat.format(change));
			
			if (DynamicEconomy.globalNotify) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f$" + newPrice + "&2 (+" + change + ")");
				} 
			} else if (DynamicEconomy.localNotify) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f$" + newPrice + "&2 (+" + change + ")");
			}
			
			Utility.writeToLog("[DynamicEconomy] New price of " + itemName + " changed dynamically to " + newPrice + "(+" + change + ")");
			
			inv.addToInventory(player, itemID, purchaseAmount);
			player.updateInventory();
			
			 try {
				 itemConfig.save(itemsFile);
			 } catch (Exception e) {
				 log.info("[DynamicEconomy] Error saving new item info after /buy execution.");
				 Utility.writeToLog("[DynamicEconomy] Error saving new item info after /buy execution");
				 e.printStackTrace();
			 }
			
			 return true;
			 
		}
	

	
public boolean sell(Player player, String[] args) {
		String stringPlay = player.getName();
		
		if ((args.length == 0) || (args.length > 2)) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/sell [Item] (Amount)");
			Utility.writeToLog(stringPlay + " incorrectly called /sell");
			return false;
		} 
		
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
			int itemID = Integer.parseInt(itemInfo[6]);
			
			String spreadRequest = itemName + ".spread";
			double itemSpread = itemConfig.getDouble(spreadRequest,itemVelocity);
			
			if (DynamicEconomy.useRegions) {
				Location loc = player.getLocation();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				
				boolean withinRegion = regionUtils.withinRegion(x, y, z);
				
				if (withinRegion == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are not within an economy region!");
					Utility.writeToLog(stringPlay + " called /buy outside of an economy region.");
					return false;
				}
				
			}
			
			
		if (itemName.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2This item does not exist. ");
			Utility.writeToLog(stringPlay + " attempted to sell the non-existent item '" + itemName + "'");
			return false;
		}
		
		String bannedItem;
		
		for (int x = 0; x < DynamicEconomy.bannedSaleItems.length; x++) {
			bannedItem = Item.getTrueName(DynamicEconomy.bannedSaleItems[x]);
			if (bannedItem.equals(itemName)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2This item is banned, and not allowed to be sold in the economy.");
				Utility.writeToLog(stringPlay + " attempted to sell the banned item: " + bannedItem);
				return false;
			}
		}
		
		
		int saleAmount = 0;
		
			
		
		if (args.length == 1) {
			saleAmount = DynamicEconomy.defaultAmount;
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("all")){
				saleAmount = inv.getAmountOf(player, itemID);
			} else {
				try {
					saleAmount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You entered the command arguments in the wrong order, or your amount was invalid. Try again. ");
					Utility.writeToLog(stringPlay + " entered an invalid purchase amount, or entered command arguments in the wrong order.");
					return false;
				}
			}
		}
		
		if (saleAmount < 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Cannot sell a negative amount!");
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
		
		
		
		double newPrice = itemPrice;
		
		
		int playerItemAmount = inv.getAmountOf(player, itemID);
		
		if (playerItemAmount < saleAmount) {
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2You do not have &f" + saleAmount + " " + itemName + "&2, you have &f" + playerItemAmount);
			Utility.writeToLog(stringPlay + " attempted to sell " + saleAmount + " of " + itemName + ", but only had " + playerItemAmount);
			return false;
		}
		
		double tax = DynamicEconomy.salestax * totalSale;
		totalSale -= tax;
		
		if (DynamicEconomy.depositTax == true) {
			try {
				economy.depositPlayer(DynamicEconomy.taxAccount, tax);
			} catch (Exception e) {
				log.info("Tax-Account " + DynamicEconomy.taxAccount + " not found.");
				Utility.writeToLog("Attempted to deposit tax of $" + tax + " to account " + DynamicEconomy.taxAccount + " but account not found.");
			}
		}
			
			economy.depositPlayer(player.getName(), totalSale);
			
			String requestPrice = itemName + ".price";
			double change = newPrice - oldPrice;
			
			
			
			itemConfig.set(requestPrice,newPrice);
			
			if (DynamicEconomy.usestock) {
				String requestStock = itemName + ".stock";
				int newStock = itemStock + saleAmount;
				itemConfig.set(requestStock,newStock);
			}
			
			
			
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Sale Success!");
			
			
			
			double percentDur;
			double maxDur;
			double playerDur;
			double indivsale;
			
			
			if (((itemID >= 256 ) && (itemID <= 258)) || ((itemID >= 267) && (itemID <= 279)) || ((itemID >= 298) && (itemID <= 317)) || ((itemID >= 283) && (itemID <= 286)) || ((itemID >= 290) && (itemID <= 294))) {
				totalSale = 0;
				
				for (int x = 0; x < saleAmount; x++) {
					
					ItemStack itemStack = new ItemStack(itemID);
					String itemMCname = itemStack.getType().toString();
					maxDur = Item.getMaxDur(itemMCname);
					
					int slot = player.getInventory().first(itemID);
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
					
					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&f1 &2" + itemName + "&f with " + percentDur + "% &2durability = &2$" + indivsale);
					Utility.writeToLog(stringPlay + " sold a '" + itemName + "' at " + percentDur + "% durability for " + indivsale);
				}
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2TOTAL SALE: &f$" + totalSale );
				
			} else {
				ItemStack i = new ItemStack(itemID,saleAmount);
				Material mat = Material.getMaterial(itemID);
				//removeInventoryItems(player.getInventory(),mat,saleAmount);
				player.getInventory().removeItem(new ItemStack (mat, saleAmount));
				
				//player.getInventory().removeItem(i);
				player.updateInventory();
				totalSale = Double.valueOf(decFormat.format(totalSale));
				oldPrice = Double.valueOf(decFormat.format(oldPrice));
				newPrice = Double.valueOf(decFormat.format(newPrice));
				double percentTax = DynamicEconomy.salestax * 100;	
				
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&f" + saleAmount + " &2" + itemName + "&f @ $" + newPrice + "&2 - " + percentTax + "% tax = &f$" + totalSale + " &2TOTAL" );
				Utility.writeToLog(stringPlay + " succesfully sold " + saleAmount + " of '" + itemName + "' for " + totalSale);
			}
			
			
			
			newPrice = Double.valueOf(decFormat.format(newPrice));
			change = Double.valueOf(changeFormat.format(change));
			
			if (DynamicEconomy.globalNotify) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f$" + newPrice + "&2 (" + change + ")");
				} 
			} else if (DynamicEconomy.localNotify) {
				color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2New Price of &f" + itemName + "&2 is &f$" + newPrice + "&2 (" + change + ")");
			}
			
			Utility.writeToLog("[DynamicEconomy] New price of " + itemName + " changed dynamically to " + newPrice + "(" + change + ")");
			
			
			//inv.removeFromInventory(player, itemID, saleAmount);
			
			
			
			
			
			 try {
				 itemConfig.save(itemsFile);
			 } catch (Exception e) {
				 log.info("[DynamicEconomy] Error saving new item info after /sell execution.");
				 Utility.writeToLog("[DynamicEconomy] Error saving new item info after /sell execution");
				 e.printStackTrace();
			 }
			
		
		
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
 			
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Stock succesfully added.");
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Previous Stock: &f" + currentStock + "&2 | New Stock: &f" + newStock);
 		    Utility.writeToLog(stringPlay + " added " + addStock + " stock of " + item + " for a new stock total of " + newStock);
 			
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
 			
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Stock succesfully added.");
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Previous Stock: &f" + currentStock + "&2 | New Stock: &f" + newStock);
 			Utility.writeToLog(stringPlay + " removed " + removeStock + " stock of " + item + " for a new stock total of " + newStock);
 			
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
		if ((args.length > 2) || (args.length < 2)) {
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2Wrong Command Usage. &f/settax [sale/purchase] [amount]");
 			Utility.writeToLog(stringPlay + " incorrectly called /settax");
 		} else {
 			if (args[0].equalsIgnoreCase("sale")) {
 				try {
 					config.set("salestax", Double.parseDouble(args[1]));
 					config.save(confFile);
 				} catch (Exception e) {
 					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2 " + args[1] + "&f% is an invalid amount");
 				}
 			} else if (args[0].equalsIgnoreCase("purchase")) {
 				try {
 					config.set("purchasetax", Double.parseDouble(args[1]));
 					config.save(confFile);
 				} catch (Exception e) {
 					color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2 " + args[1] + "&f% is an invalid amount");
 				}
 			}
 			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2" + args[0] + "tax set to &f" + (Double.parseDouble(args[1])*100) + "%");
 			Utility.writeToLog(stringPlay + " set " + args[0] + "tax to " + args[1]);
 			DynamicEconomy.relConfig();
 		}
	}
	
}
