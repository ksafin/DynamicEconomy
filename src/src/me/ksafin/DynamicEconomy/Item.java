package me.ksafin.DynamicEconomy;


import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;



public class Item {

	private static FileConfiguration itemConfig;
	private static ExtrasColour color = new ExtrasColour();
	static Logger log = Logger.getLogger("Minecraft");
	private static File itemsFile;
	
	static NumberFormat f = NumberFormat.getNumberInstance(Locale.US);
    public static DecimalFormat decFormat = (DecimalFormat)f;
	
	private ExtrasInventory inv = new ExtrasInventory();
	
	//public static boolean DynamicEconomy.usestock = DynamicEconomy.DynamicEconomy.usestock;
	//public static boolean DynamicEconomy.useboundaries = DynamicEconomy.DynamicEconomy.useboundaries;
	//public static String prefix = DynamicEconomy.prefix;
	//public static int defaultAmount = DynamicEconomy.defaultAmount;
	
	public Item(FileConfiguration config) {
		itemConfig = config;
	}
	
	public static void setItemsFile(File file) {
		itemsFile = file;
	}
	
	public static String getTrueName(String arg) {

		arg = arg.toUpperCase();
		
				if (DynamicEconomy.aliasConfig.contains(arg)) {
					String item = DynamicEconomy.aliasConfig.getString(arg);
					return item;
				} else {
					return "";
				}
		}
	
	public static void addAlias(Player player, String[] args) {
		if (args.length != 2) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/addAlias [Alias] [Item]");
			Utility.writeToLog(player.getName() + " incorrectly called /addAlias");
			return;
		} else {
			String alias = args[0];
			String item = args[1];
			
			alias = alias.toUpperCase();
			item = item.toUpperCase();
			
			DynamicEconomy.aliasConfig.set(alias, item);
			try {
				DynamicEconomy.aliasConfig.save(DynamicEconomy.aliasFile);
			} catch (Exception e) {
				log.info("[DynamicEconomy] Error saving alias.yml");
			}
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + alias + "&2 has been added as an alias for &f" + item);
			Utility.writeToLog(player.getName() + " added " + alias + " as an alias for " + item);
			
			DynamicEconomy.relConfig();
		}
	}
	
	public static void removeAlias(Player player, String[] args) {
		if (args.length != 1) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removeAlias [Alias]");
			Utility.writeToLog(player.getName() + " incorrectly called /removeAlias");
			return;
		} else {
			String alias = args[0];
			alias = alias.toUpperCase();
			
			if (DynamicEconomy.aliasConfig.contains(alias) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + alias + "&2 does not exist as an alias.");
				Utility.writeToLog(player.getName() + " tried to remove " + alias + " as an alias, but it doesn't exist.");
				return;
			}
			
			DynamicEconomy.aliasConfig.set(alias, null);
			try {
				DynamicEconomy.aliasConfig.save(DynamicEconomy.aliasFile);
			} catch (Exception e) {
				log.info("[DynamicEconomy] Error saving alias.yml");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + alias + "&2 has been removed as an alias");
			Utility.writeToLog(player.getName() + " removed " + alias + " as an alias");
			
			DynamicEconomy.relConfig();
		}
	}
	
	public static boolean canBuy(Player player, String item) {
		List<String> allowedGroups = DynamicEconomy.usersConfig.getStringList(player.getName() + ".PURCHASEGROUPS");
		
		if (allowedGroups.contains("*")) {
			return true;
		}
		
		String group = getGroup(item);
		
		if (group.equals("")) {
			Utility.writeToLog(player.getName() + " tried to buy " + item + ", but this item is not in any groups.");
			return false;
		}
		
		if (allowedGroups.contains(group)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean canSell(Player player, String item) {
		List<String> allowedGroups = DynamicEconomy.usersConfig.getStringList(player.getName() + ".SALEGROUPS");
		
		if (allowedGroups.contains("*")) {
			return true;
		}
		
		String group = getGroup(item);
		
		if (group.equals("")) {
			Utility.writeToLog(player.getName() + " tried to sell " + item + ", but this item is not in any groups.");
			return false;
		}
		
		if (allowedGroups.contains(group)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String getGroup(String item) {
		try {
			DynamicEconomy.groupsConfig.load(DynamicEconomy.groupsFile);
		} catch (Exception e) {
			log.info(DynamicEconomy.prefix + " Error loading groups.yml in getGroup()");
		}
		
		Set<String> groupSet = DynamicEconomy.groupsConfig.getKeys(false);
		Iterator<String> i = groupSet.iterator();
		
		List<String> groupItems;
		String curGroupItem;
		
		String group;
		
		while (i.hasNext()) {
			group = i.next().toString();
			groupItems = DynamicEconomy.groupsConfig.getStringList(group);
			for (int x = 0; x < groupItems.size(); x++) {
				curGroupItem = groupItems.get(x);
				if (curGroupItem.equalsIgnoreCase(item)) {
					return group;
				}
			}
		}
		
		return "";
		
	}
	
	public static void canIBuy(Player player, String[] args) {
		if (args.length != 1) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/canibuy [Item]");
			 Utility.writeToLog(player.getName() + " incorrectly called /canibuy");
			 return;
		} else {
			String item = getTrueName(args[0]);
			
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + args[0] + " &2doesn't exist.");
				Utility.writeToLog(player.getName() + " called /canibuy for item " + args[0] + ", which doesn't exist.");
			} else {
				List<String> bannedItems = Arrays.asList(DynamicEconomy.bannedPurchaseItems);
				
				if(bannedItems.contains(item)) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCANNOT &2buy &f" + item);
					return;
				}	
				
				if (DynamicEconomy.groupControl == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCAN &2buy &f" + item);
					return;
				}
				
				boolean canbuy = canBuy(player,item);
				
				if (canbuy) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCAN &2buy &f" + item);
				} else {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCANNOT &2buy &f" + item);
				}
			}
			
		}
	}
	
	public static void canISell(Player player, String[] args) {
		if (args.length != 1) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/canisell [Item]");
			 Utility.writeToLog(player.getName() + " incorrectly called /canisell");
			 return;
		} else {
			String item = getTrueName(args[0]);
			
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + args[0] + " &2doesn't exist.");
				Utility.writeToLog(player.getName() + " called /canisell for item " + args[0] + ", which doesn't exist.");
			} else {
				List<String> bannedItems = Arrays.asList(DynamicEconomy.bannedSaleItems);
				
				if(bannedItems.contains(item)) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCANNOT &2sell &f" + item);
					return;
				}
				
				if (DynamicEconomy.groupControl == false) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCAN &2sell &f" + item);
					return;
				}
				
				boolean cansell = canSell(player,item);
				
				if (cansell) {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCAN &2sell &f" + item);
				} else {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You &fCANNOT &2sell &f" + item);
				}
			}
			
		}
	}
	
	public static void addItemGroup(Player player, String[] args) {
		if (args.length != 1) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/creategroup [ItemGroupName]");
			 Utility.writeToLog(player.getName() + " incorrectly called /creategroup");
			 return;
		} else {
			args[0] = args[0].toUpperCase();
			
			if (DynamicEconomy.groupsConfig.contains(args[0])) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2already exists.");
				Utility.writeToLog(player.getName() + " attempted to create new Item Group " + args[0] + " but it already exists.");
				return;
			}
			
			ArrayList<String> list = new ArrayList<String>();
			DynamicEconomy.groupsConfig.set(args[0], list);
			
			try {
				DynamicEconomy.groupsConfig.save(DynamicEconomy.groupsFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in addItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2created.");
			Utility.writeToLog(player.getName() + " created new Item Group " + args[0] + ".");
		}
	}
	
	public static void addItemToGroup(Player player, String[] args) {
		if (args.length != 2) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/addtogroup [ItemGroup] [Item]");
			 Utility.writeToLog(player.getName() + " incorrectly called /addtogroup");
			 return;
		} else {
			args[0] = args[0].toUpperCase();
			String item = getTrueName(args[1]);
			
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + args[1] + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " attempted to add " + args[1] + " to group " + args[0] + ", but this item doesn't exist.");
				return;
			}
			
			if (DynamicEconomy.groupsConfig.contains(args[0]) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2doesn't exist.");
				Utility.writeToLog(player.getName() + " attempted to add an item to Item Group " + args[0] + " but this group doesn't exist.");
				return;
			}
			
			ArrayList<String> list = new ArrayList<String>();
			List<String> curGroup = DynamicEconomy.groupsConfig.getStringList(args[0]);
			curGroup.add(item);
			
			DynamicEconomy.groupsConfig.set(args[0], curGroup);
			
			try {
				DynamicEconomy.groupsConfig.save(DynamicEconomy.groupsFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in addItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + item + " &2added to group " + args[0]);
			Utility.writeToLog(player.getName() + " added item " + item + " to group " + args[0]);
		}
	}
	
	public static void removeItemFromGroup(Player player, String[] args) {
		if (args.length != 2) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removefromgroup [ItemGroup] [Item]");
			 Utility.writeToLog(player.getName() + " incorrectly called /removefromgroup");
			 return;
		} else {
			args[0] = args[0].toUpperCase();
			String item = getTrueName(args[1]);
			
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + args[1] + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " attempted to remove " + args[1] + " from group " + args[0] + ", but this item doesn't exist.");
				return;
			}
			
			if (DynamicEconomy.groupsConfig.contains(args[0]) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2doesn't exist.");
				Utility.writeToLog(player.getName() + " attempted to remove an item from Item Group " + args[0] + " but this group doesn't exist.");
				return;
			}
			
			ArrayList<String> list = new ArrayList<String>();
			List<String> curGroup = DynamicEconomy.groupsConfig.getStringList(args[0]);
			
			if (curGroup.contains(item) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2doesn't contain &f" + item);
				Utility.writeToLog(player.getName() + " attempted to remove an item from Item Group " + args[0] + " but this group doesn't contain "+ item);
				return;
			}
			
			curGroup.remove(item);
			
			DynamicEconomy.groupsConfig.set(args[0], curGroup);
			
			try {
				DynamicEconomy.groupsConfig.save(DynamicEconomy.groupsFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in addItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item &f" + item + " &2removed from group " + args[0]);
			Utility.writeToLog(player.getName() + " removed item " + item + " from group " + args[0]);
		}
	}
	
	public static void addGroupToUser(Player player, String[] args) {
		if (args.length != 3) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/addgrouptouser [User] [ItemGroupName] [Purchase|Sale]");
			 Utility.writeToLog(player.getName() + " incorrectly called /addgrouptouser");
			 return;
		} else {
			String user = args[0];
			String group = args[1].toUpperCase();
			String type = args[2].toUpperCase();
			
			if (DynamicEconomy.usersConfig.contains(user) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The user &f" + user + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to add a group for user " + user + " but this user doesn't exist.");
				return;
			}
			
			List<String> curGroups;
			String node;
			
			if (type.equalsIgnoreCase("PURCHASE")) {
				node = user + ".PURCHASEGROUPS";
			} else if (type.equalsIgnoreCase("SALE")) {
				node = user + ".SALEGROUPS";
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group type &f" + type + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to add a group of type " + type + " but this type doesn't exist.");
				return;
			}
			
			curGroups = DynamicEconomy.usersConfig.getStringList(node);
			
			if ((DynamicEconomy.groupsConfig.contains(group) == false) && (group.equals("*") == false)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group &f" + group + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to add group  " + group + " for user " + user + ", but this group doesn't exist.");
				return;
			}
			
			if ((curGroups.contains(group)) || (curGroups.contains("*"))) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The user &f" + user + "&2 already has access to group &f" + group);
				Utility.writeToLog(player.getName() + " tried to add a group for user " + user + " but this user already has access to it.");
				return;
			}
			
			curGroups.add(group);
			
			DynamicEconomy.usersConfig.set(node,curGroups);
			
			try {
				DynamicEconomy.usersConfig.save(DynamicEconomy.usersFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in addItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group &f" + group + "&2 has been added for user &f" + user);
			Utility.writeToLog(player.getName() + " added group  " + group + " for user " + user + ".");
			
		}
	}
	
	public static void removeGroupFromUser(Player player, String[] args) {
		if (args.length != 3) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removegroupfromuser [User] [ItemGroupName] [Purchase|Sale]");
			 Utility.writeToLog(player.getName() + " incorrectly called /removegroupfromuser");
			 return;
		} else {
			String user = args[0];
			String group = args[1].toUpperCase();
			String type = args[2].toUpperCase();
			
			if (DynamicEconomy.usersConfig.contains(user) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The user &f" + user + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to remove a group from user " + user + " but this user doesn't exist.");
				return;
			}
			
			List<String> curGroups;
			String node;
			
			if (type.equalsIgnoreCase("PURCHASE")) {
				node = user + ".PURCHASEGROUPS";
			} else if (type.equalsIgnoreCase("SALE")) {
				node = user + ".SALEGROUPS";
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group type &f" + type + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to remove a group of type " + type + " but this type doesn't exist.");
				return;
			}
			
			curGroups = DynamicEconomy.usersConfig.getStringList(node);
			
			if ((DynamicEconomy.groupsConfig.contains(group) == false) && (group.equals("*") == false)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group &f" + group + "&2 doesn't exist.");
				Utility.writeToLog(player.getName() + " tried to remove group  " + group + " from user " + user + ", but this group doesn't exist.");
				return;
			}
			
			if ((curGroups.contains("*")) && (group.equals("*") == false)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The user &f" + user + "&2 has access to all groups via group &f'*'");
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You must remove group &f'*'");
				Utility.writeToLog(player.getName() + " tried to remove group from user " + user + " but this user has access to all groups via '*'");
				return;
			}
			
			if (curGroups.contains(group) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2User &f" + user + "&2 doesn't have access to group &f" + group);
				Utility.writeToLog(player.getName() + " tried to remove group from user " + user + " but this user doesn't have access to this group.");
				return;
			}
			
			curGroups.remove(group);
			
			DynamicEconomy.usersConfig.set(node,curGroups);
			
			try {
				DynamicEconomy.usersConfig.save(DynamicEconomy.usersFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in addItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group &f" + group + "&2 has been removed from user &f" + user);
			Utility.writeToLog(player.getName() + " removed group  " + group + " from user " + user + ".");
			
		}
	}
	
	public static void viewGroup(Player player, String[] args) {
		int page = 0;
		if ((args.length != 1) && (args.length != 2)) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/viewgroup [ItemGroupName] (Page)");
			Utility.writeToLog(player.getName() + " incorrectly called /viewgroup");
			return;
		} else if (args.length == 2) {
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (Exception e) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The (Page) argument must be an &finteger");
				Utility.writeToLog(player.getName() + " called /viewgroup but passed a non-integer page.");
				return;
			}
		}
		
		try {
			DynamicEconomy.groupsConfig.load(DynamicEconomy.groupsFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[DynamicEconomy] Error loading groups.yml in viewGroup()");
		}
		
		String group = args[0].toUpperCase();
		
		if (DynamicEconomy.groupsConfig.contains(group) == false) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The group &f" + group + "&2 doesn't exist.");
			Utility.writeToLog(player.getName() + " called /viewgroup for group " + group + ", which doesn't exist.");
			return;
		}
		
		List<String> items = DynamicEconomy.groupsConfig.getStringList(group);
		
		int startIndex = (page * 5);
		int endIndex = startIndex + 5;
		
		int numPages = (int)((items.size()/5.0) + 1);
		
		if (endIndex > (items.size() - 1)) {
			endIndex = items.size() - 1;
		}
		
		color.sendColouredMessage(player, "&2---------------&f" + group + " Items&2---------------");
		
		for (int x = startIndex; x <= endIndex; x++) {
			color.sendColouredMessage(player, "&f" + items.get(x));
		}
		
		color.sendColouredMessage(player, "&2----------------&fPage &f" + (page + 1) + "/" + numPages + "&2----------------");
		
	}
	
	public static boolean isItemInRegionGroup(List<String> allowedItemGroups, String item) {
		if (allowedItemGroups.contains("*")) {
			return true;
		}
		
		for (int x = 0; x < allowedItemGroups.size(); x++) {
			if (DynamicEconomy.groupsConfig.getStringList(allowedItemGroups.get(x)).contains(item)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void removeItemGroup(Player player, String[] args) {
		if (args.length != 1) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removegroup [ItemGroupName]");
			 Utility.writeToLog(player.getName() + " incorrectly called /removegroup");
			 return;
		} else {
			args[0] = args[0].toUpperCase();
			
			if (DynamicEconomy.groupsConfig.contains(args[0]) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2doesn't exist.");
				Utility.writeToLog(player.getName() + " attempted to remove Item Group " + args[0] + " but it doesn't exist.");
				return;
			}
			
			DynamicEconomy.groupsConfig.set(args[0], null);
			
			try {
				DynamicEconomy.groupsConfig.save(DynamicEconomy.groupsFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving groups.yml in removeItemGroup()");
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2removed.");
			Utility.writeToLog(player.getName() + " removed Item Group " + args[0] + ".");
		}
	}
	
public boolean getPrice(Player player, String[] args) {
	int amt = 0;
	String stringPlay = player.getName();
	double total = 0;
	if ((args.length < 1) || (args.length > 3)) {
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/price [Item]");
		 Utility.writeToLog(stringPlay + " incorrectly called /price");
		 return false;
	 } else if (args.length == 1) {
		 String item = "";
		 if (args[0].equalsIgnoreCase("hand")) {
			 int id = player.getInventory().getItemInHand().getTypeId();
			 int dur = player.getInventory().getItemInHand().getDurability();
			 
			 if (id == 17) {
				 if (dur == 1) {
					// dur = 2;
				 } else if (dur == 2) {
					// dur = 1;
				 }
			 }
			 
			 String name = "";
			 
			 if (dur == 0) {
				 name = String.valueOf(id);
			 } else {
				 name = id + ":" + dur;
			 }
			 
			 item = getTrueName(name);
		 } else {
			 item = getTrueName(args[0]);
		 }
		 
		 if (item.equals("")) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item does not exist.");
			 Utility.writeToLog(stringPlay + " called /price on non-existant item " + args[0]);
		 }
		 
		 decFormat.applyPattern("#.##");
		 
		 String reqDesc = item + ".description";
		 
		 String[] itemInfo = getAllInfo(item);
		 Double price = Double.parseDouble(itemInfo[1]);
		 int stock = Integer.parseInt(itemInfo[5]);
		 String desc = itemConfig.getString(reqDesc,"");
		 String priceStr = decFormat.format(price);
		 int itemID = Integer.parseInt(itemInfo[6]);
		 
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2-----------------------------------");
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price of &f" + item + "&2 is &f" + DynamicEconomy.currencySymbol + priceStr);
	     color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Description: &f" + desc);
			if (DynamicEconomy.usestock){	
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Stock: &f" + stock);
			}
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2-----------------------------------");
		 Utility.writeToLog(stringPlay + " called /price for item " + item);
			return true;
	 } else if (args.length == 2){
		 amt = DynamicEconomy.defaultAmount;
	 } else if (args.length == 3){
		 String reqID = getTrueName(args[0]) + ".id";
		 long itemID = itemConfig.getLong(reqID);
		 String reqStock = getTrueName(args[0]) + ".stock";
		 int stock = itemConfig.getInt(reqStock);
		 
		 String type1 = args[1];
		 
		 if ((args[2].equalsIgnoreCase("all")) && (type1.equals("sell"))){
				amt = inv.getAmountOf(player, (int)itemID);
				amt--;
			} else if ((args[2].equalsIgnoreCase("all")) && (type1.equals("buy"))) {
				amt = stock;
			} else {
				amt = Integer.parseInt(args[2]);
			}
	 } 
	
	String item = getTrueName(args[0]);
	String type = args[1];
	String reqDesc = item + ".description";
	double tax;
	
	
	String[] itemInfo = getAllInfo(item);
	
	Double price = Double.parseDouble(itemInfo[1]);
	Double floor = Double.parseDouble(itemInfo[2]);
	Double ceiling = Double.parseDouble(itemInfo[3]);
	Double velocity = Double.parseDouble(itemInfo[4]);
	int stock = Integer.parseInt(itemInfo[5]);
	int id = Integer.parseInt(itemInfo[6]);
	String desc = itemConfig.getString(reqDesc,"");
	
	if (DynamicEconomy.useboundaries != true) {
		floor = .01;
		ceiling = 1000000000.0;
	}
	
	if (type.equalsIgnoreCase("sale") || type.equalsIgnoreCase("sell")) {
		total = 0;
		double newPrice = 0;
		
		if (DynamicEconomy.usePercentVelocity) {
			for (int x = 0; x < amt; x++) {
				price = price * (Math.pow((1-velocity),amt));
			
				if (price > ceiling) {
					price = ceiling;
				}
				if (price < floor) {
					price = floor;
				}
			
				total += price;
			}
		} else {
			for (int x = 0; x < amt; x++) {
				if (price == floor) {
					price = floor;
				} else {
					price = price - velocity;
					if (price < floor) {
						price = floor;
					}
				}
				total += price;
			}	
		}
		
		
		
		tax = DynamicEconomy.salestax * total;
		total -= tax;
		
		
	} else if (type.equalsIgnoreCase("purchase") || type.equalsIgnoreCase("buy")) {
		total = 0;
		double newPrice;
		
		if (DynamicEconomy.usePercentVelocity) {
			for (int x = 0; x < amt; x++) {
				total += price;
				price = price / (Math.pow((1 - velocity), amt));
			
				if (price > ceiling) {
				price = ceiling;
				}
				if (price < floor) {
				price = floor;
				}
			}
		} else {
			for (int x = 0; x < amt; x++) {
				total += price;	
				if (price == ceiling) {
					price = ceiling;
				} else {
					price = price + velocity;
					if (price > ceiling) {
						price = ceiling;
					}
				}
		}
		}
		
		
		
		tax = DynamicEconomy.purchasetax * total;
		total += tax;
	} else {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The type &f" + type + "&2 does not exist. Use either buy or sell.");
		Utility.writeToLog(stringPlay + " called invalid type '" + type + "'");
		return false;
	}
	String priceStr = decFormat.format(price);
	String totalStr = decFormat.format(total);
	tax = Double.valueOf(decFormat.format(tax));
	
	color.sendColouredMessage(player, DynamicEconomy.prefix + "&2-----------------------------------");
	color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price of &f" + item + "&2 is &f" + DynamicEconomy.currencySymbol + priceStr);
	color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Description: &f" + desc);
	if (DynamicEconomy.usestock){	
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Stock: &f" + stock);
	}
	if (type.equalsIgnoreCase("sale") || type.equalsIgnoreCase("sell")) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Selling &f" + amt + "&2 of &f" + item + "&2 + " + DynamicEconomy.currencySymbol + tax + " tax &fyields &2" + totalStr);
	} else if (type.equalsIgnoreCase("purchase") || type.equalsIgnoreCase("buy")) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Buying &f" + amt + "&2 of &f" + item + "&2 + " + DynamicEconomy.currencySymbol + tax + " tax &fcosts &2" + totalStr);
	}
	color.sendColouredMessage(player, DynamicEconomy.prefix + "&2-----------------------------------");
	if (args.length == 2) {
		Utility.writeToLog(stringPlay + " called /price " + args[0] + " " + args[1]);
	} else if (args.length == 3) {
		Utility.writeToLog(stringPlay + " called /price " + args[0] + " " + args[1] + " " + args[2]);
	}
	return true;
}


public void setPrice(Player player, String[] args) {
	String stringPlay = player.getName();
	if (args.length !=2) {
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/setprice [Item] [Price]");
		 Utility.writeToLog(stringPlay + " incorrectly called /setprice");
	 } else {
	String item = getTrueName(args[0]);
	
	String reqFloor = item + ".floor";
	String reqCeiling = item + ".ceiling";
	
	Double price = Double.parseDouble(args[1]);
	Double oldPrice = itemConfig.getDouble(item + ".price",0);
	Double changePrice = price - oldPrice;
	
	Double floor = itemConfig.getDouble(reqFloor,0);
	Double ceiling = itemConfig.getDouble(reqCeiling,0);
	
	decFormat.applyPattern("#.##");
	
	price = Double.valueOf(decFormat.format(price));
	floor = Double.valueOf(decFormat.format(floor));
	ceiling = Double.valueOf(decFormat.format(ceiling));
	
	if (item.equals("")) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
		Utility.writeToLog(stringPlay + " called /setprice for the non-existent item '" + item + "'");
	} else {
		
		boolean withinbounds;
		
		if (DynamicEconomy.useboundaries == false) {
			withinbounds = true;
		} else {
			withinbounds = ((price >= floor) && (price <= ceiling));
		}
		
		if (withinbounds) {
			String request = item + ".price";
			itemConfig.set(request, price);
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price of " + item + " set to &f" + DynamicEconomy.currencySymbol + price);
			saveItemFile();
			Utility.writeToLog(stringPlay + " set the price of " + item + " to " + price);
			dataSigns.checkForUpdates(item, 0, changePrice);
		} else {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Desired price is not within bounds.");
			color.sendColouredMessage(player, DynamicEconomy.prefix +  "&2MIN: &f" + DynamicEconomy.currencySymbol + floor + " &2MAX: &f" + DynamicEconomy.currencySymbol + ceiling);
			Utility.writeToLog(stringPlay + " attempted to set the price of " + item + "outside of bounds");
		}
		
	}
}
	
	
	
}



public static void saveItemFile(){
	try {
		itemConfig.save(itemsFile);
	} catch (Exception e) {
		log.info("[DynamicEconomy] IOException saving Items.yml.");
		Utility.writeToLog("[DynamicEconomy] IOException saving Items.yml.");
	}
}



public void setFloor(Player player, String[] args) {
	String stringPlay = player.getName();
 if (args.length !=2) {
	 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/setfloor [Item] [FloorPrice]");
	 Utility.writeToLog(stringPlay + " incorrectly called /setfloor");
 } else if (DynamicEconomy.useboundaries == false) {
	 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries such as floor and ceiling are disabled.");
	 Utility.writeToLog(stringPlay + " called /setfloor, but boundaries are disabled");
 } else {
	String item = getTrueName(args[0]);
	
	Double floor = Double.parseDouble(args[1]);
	Double oldFloor = itemConfig.getDouble(item + ".floor",0);
	Double change = floor - oldFloor;
	
	String request = item + ".floor";
	String priceRequest = item + ".price";
	Double price = itemConfig.getDouble(priceRequest,0);
	
	decFormat.applyPattern("#.##");
	
	price = Double.valueOf(decFormat.format(price));
	floor = Double.valueOf(decFormat.format(floor));
	
	boolean withinbounds;
	
	if (DynamicEconomy.useboundaries == false) {
		withinbounds = true;
	} else {
		withinbounds = (floor <= price);
	}
	
	if (item.equals("")) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
		Utility.writeToLog(stringPlay + " attempted to set the floor of the non-existent item '" + item + "'");
	} else { 
		if (withinbounds == false) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The price of " + item + " is lower than desired floor.");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You must increase price above desired floor, or set lower floor.");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2DESIRED FLOOR: &f" + DynamicEconomy.currencySymbol + floor + " PRICE: &f" + DynamicEconomy.currencySymbol + price);
			Utility.writeToLog(stringPlay + " attempted to set floor of " + item + " to " + floor + ", but the price is lower than the desired floor.");
		} else {
			itemConfig.set(request,floor);
			saveItemFile();
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price Floor of &f" + item + " set to&f " + DynamicEconomy.currencySymbol + floor);
			Utility.writeToLog(stringPlay + " set the floor of " + item + " to " + floor);
			dataSigns.checkForUpdatesNonRegular(item, 0, 0, change);
		}
	}
	
 } 
}

public void setCeiling(Player player, String[] args) {
	String stringPlay = player.getName();
if (args.length !=2) {
   color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/setceiling [Item] [FloorPrice]");
   Utility.writeToLog(stringPlay + " incorrectly called /setceiling");
} else if (DynamicEconomy.useboundaries == false) {
	 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries such as floor and ceiling are disabled.");
	 Utility.writeToLog(stringPlay + " called /setceiling, but boundaries are disabled.");
} else {
	String item = getTrueName(args[0]);
	
	Double ceiling = Double.parseDouble(args[1]);
	Double oldCeiling = itemConfig.getDouble(item + ".ceiling",0);
	Double change = ceiling - oldCeiling;
	
	String request = item + ".ceiling";
	String priceRequest = item + ".price";
	Double price = itemConfig.getDouble(priceRequest,0);
	
	decFormat.applyPattern("#.##");
	
	price = Double.valueOf(decFormat.format(price));
	ceiling = Double.valueOf(decFormat.format(ceiling));
	
	boolean withinbounds;
	
	if (DynamicEconomy.useboundaries == false) {
		withinbounds = true;
	} else {
		withinbounds = (ceiling >= price);
	}
	
	if (item.equals("")) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
		Utility.writeToLog(stringPlay + " attempted to set the floor of the non-existent item '" + item + "'");
	} else { 
		if (withinbounds == false) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The price of " + item + " is higher than desired ceiling.");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You must decrease price to below desired ceiling, or set higher ceiling.");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2DESIRED CEILING: &f" + DynamicEconomy.currencySymbol + ceiling + " PRICE: &f" + DynamicEconomy.currencySymbol + price);
			Utility.writeToLog(stringPlay + " attempted to set ceiling of " + item + " to " + ceiling + ", but the price is higher than the desired ceiling.");
		} else {
			itemConfig.set(request,ceiling);
			saveItemFile();
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price Ceiling of &f" + item + " set to &f" + DynamicEconomy.currencySymbol + ceiling);
			Utility.writeToLog(stringPlay + " set the floor of " + item + " to " + ceiling);
			dataSigns.checkForUpdatesNonRegular(item, 0, change, 0);
		}
	}
	
}
}


public void getFloor(Player player, String[] args) {
	String stringPlay = player.getName();
	if (args.length !=1) {
		   color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/getfloor [Item]");
		   Utility.writeToLog(stringPlay + " incorrectly called /getfloor");

	} else if (DynamicEconomy.useboundaries == false) {
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries such as floor and ceiling are disabled.");
		   Utility.writeToLog(stringPlay + " called /getfloor, but boundaries are disabled.");

	 } else {
		String item = getTrueName(args[0]);
		String request = item + ".floor";
		
		
		
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to get the floor of the non-existent item '" + item + "'");
		} else { 
			Double floor = itemConfig.getDouble(request,0);
			decFormat.applyPattern("#.##");
			floor = Double.valueOf(decFormat.format(floor));
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price Floor of &f" + item + " &2is &f" + DynamicEconomy.currencySymbol + floor);
			Utility.writeToLog(stringPlay + " called /getfloor for item '" + item + "'");
		}
		
	}
}



public void getCeiling(Player player, String[] args) {
	String stringPlay = player.getName();
	if (args.length !=1) {
		   color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/getceiling [Item]");
		   Utility.writeToLog(stringPlay + " incorrectly called /getceiling");
	} else if (DynamicEconomy.useboundaries == false) {
		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries such as floor and ceiling are disabled.");
		  Utility.writeToLog(stringPlay + " called /getceiling, but boundaries are disabled.");
	 } else {
		String item = getTrueName(args[0]);
		String request = item + ".ceiling";
		
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to get the ceiling of the non-existent item '" + item + "'");
		} else { 
			Double ceiling = itemConfig.getDouble(request,0);
			decFormat.applyPattern("#.##");
			ceiling = Double.valueOf(decFormat.format(ceiling));
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Price Ceiling of &f" + item + "&2 is &f" + DynamicEconomy.currencySymbol + ceiling);
			Utility.writeToLog(stringPlay + " called /getceiling for item '" + item + "'");
		}
		
	}
}

public void getVelocity(Player player, String[] args) {
	String stringPlay = player.getName();
	if (args.length !=1) {
		   color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/getvelocity [Item]");
		   Utility.writeToLog(stringPlay + " incorrectly called /getvelocity");
	} else {
		String item = getTrueName(args[0]);
		String request = item + ".velocity";
		
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to get the velocity of the non-existent item '" + item + "'");
		} else { 
			Double velocity = itemConfig.getDouble(request,0);
			decFormat.applyPattern("#.##");
			velocity = Double.valueOf(decFormat.format(velocity));
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Velocity of &f" + item + " &2is &f" + velocity);
			Utility.writeToLog(stringPlay + " called /getvelocity for item '" + item + "'");
		}
	}
}

public void setVelocity(Player player, String[] args) {
	String stringPlay = player.getName();
	if (args.length !=2) {
		   color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/setvelocity [Item] [Velocity]");
		   Utility.writeToLog(stringPlay + " incorrectly called /setvelocity");
	} else {
		String item = getTrueName(args[0]);
		Double velocity = Double.parseDouble(args[1]);
		Double oldVelocity = itemConfig.getDouble(item + ".velocity",0);
		Double change = velocity - oldVelocity;
		
		decFormat.applyPattern("#.##");
		velocity = Double.valueOf(decFormat.format(velocity));
		
		
		String request = item + ".velocity";
		
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.itemDoesntExist);
			Utility.writeToLog(stringPlay + " attempted to set the velocity of the non-existent item '" + item + "'");
		} else { 
			itemConfig.set(request,velocity);
			saveItemFile();
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Velocity of &f" + item + " &2set to &f" + velocity);
			Utility.writeToLog(stringPlay + " set the velocity of " + item + " to " + velocity);
			dataSigns.checkForUpdatesNonRegular(item, change, 0, 0);
			
		}
	}
}

public static String[] getAllInfo(String item) {
	item = getTrueName(item);
	
	String requestPrice = item + ".price";
	String requestFloor = item + ".floor";
	String requestCeiling = item + ".ceiling";
	String requestVelocity = item + ".velocity";
	String requestStock = item + ".stock";
	String requestID = item + ".id";
	
	double price = itemConfig.getDouble(requestPrice,0);
	double floor = itemConfig.getDouble(requestFloor,0);
	double ceiling = itemConfig.getDouble(requestCeiling,0);
	double velocity = itemConfig.getDouble(requestVelocity,0);
	int stock = itemConfig.getInt(requestStock,0);
	long id = itemConfig.getLong(requestID,0);
	
	String priceStr = Double.toString(price);
	String floorStr = Double.toString(floor);
	String ceilingStr = Double.toString(ceiling);
	String velocityStr = Double.toString(velocity);
	String stockStr = Integer.toString(stock);
	String idStr = Long.toString(id);
	
	String [] info = {item,priceStr,floorStr,ceilingStr,velocityStr,stockStr,idStr};
	
	return info;
	
}


public static int getMaxDur(String itemName) {
	int maxDur = 0;
	
	if (itemName.equals("WOOD_PICKAXE")) {
		maxDur = Material.WOOD_PICKAXE.getMaxDurability();
	} else if (itemName.equals("WOOD_AXE")) {
		maxDur = Material.WOOD_AXE.getMaxDurability();
	} else if (itemName.equals("WOOD_SPADE")) {
		maxDur = Material.WOOD_SPADE.getMaxDurability();
	} else if (itemName.equals("WOOD_HOE")) {
		maxDur = Material.WOOD_HOE.getMaxDurability();
	} else if (itemName.equals("WOOD_SWORD")) {
		maxDur = Material.WOOD_SWORD.getMaxDurability();
	} 
	
	if (itemName.equals("STONE_PICKAXE")) {
		maxDur = Material.STONE_PICKAXE.getMaxDurability();
	} else if (itemName.equals("STONE_AXE")) {
		maxDur = Material.STONE_AXE.getMaxDurability();
	} else if (itemName.equals("STONE_SPADE")) {
		maxDur = Material.STONE_SPADE.getMaxDurability();
	} else if (itemName.equals("STONE_HOE")) {
		maxDur = Material.STONE_HOE.getMaxDurability();
	} else if (itemName.equals("STONE_SWORD")) {
		maxDur = Material.STONE_SWORD.getMaxDurability();
	}
	
	if (itemName.equals("IRON_PICKAXE")) {
		maxDur = Material.IRON_PICKAXE.getMaxDurability();
	} else if (itemName.equals("IRON_AXE")) {
		maxDur = Material.IRON_AXE.getMaxDurability();
	} else if (itemName.equals("IRON_SPADE")) {
		maxDur = Material.IRON_SPADE.getMaxDurability();
	} else if (itemName.equals("IRON_HOE")) {
		maxDur = Material.IRON_HOE.getMaxDurability();
	} else if (itemName.equals("IRON_SWORD")) {
		maxDur = Material.IRON_SWORD.getMaxDurability();
	}
	
	if (itemName.equals("GOLD_PICKAXE")) {
		maxDur = Material.GOLD_PICKAXE.getMaxDurability();
	} else if (itemName.equals("GOLD_AXE")) {
		maxDur = Material.GOLD_AXE.getMaxDurability();
	} else if (itemName.equals("GOLD_SPADE")) {
		maxDur = Material.GOLD_SPADE.getMaxDurability();
	} else if (itemName.equals("GOLD_HOE")) {
		maxDur = Material.GOLD_HOE.getMaxDurability();
	} else if (itemName.equals("GOLD_SWORD")) {
		maxDur = Material.GOLD_SWORD.getMaxDurability();
	}
	
	if (itemName.equals("DIAMOND_PICKAXE")) {
		maxDur = Material.DIAMOND_PICKAXE.getMaxDurability();
	} else if (itemName.equals("DIAMOND_AXE")) {
		maxDur = Material.DIAMOND_AXE.getMaxDurability();
	} else if (itemName.equals("DIAMOND_SPADE")) {
		maxDur = Material.DIAMOND_SPADE.getMaxDurability();
	} else if (itemName.equals("DIAMOND_HOE")) {
		maxDur = Material.DIAMOND_HOE.getMaxDurability();
	} else if (itemName.equals("DIAMOND_SWORD")) {
		maxDur = Material.DIAMOND_SWORD.getMaxDurability();
	}
	
	// ARMOR
	
	if (itemName.equals("IRON_HELMET")) {
		maxDur = Material.IRON_HELMET.getMaxDurability();
	} else if (itemName.equals("IRON_CHESTPLATE")) {
		maxDur = Material.IRON_CHESTPLATE.getMaxDurability();
	} else if (itemName.equals("IRON_LEGGINGS")) {
		maxDur = Material.IRON_LEGGINGS.getMaxDurability();
	} else if (itemName.equals("IRON_BOOTS")) {
		maxDur = Material.IRON_BOOTS.getMaxDurability();
	} 
	
	if (itemName.equals("GOLD_HELMET")) {
		maxDur = Material.GOLD_HELMET.getMaxDurability();
	} else if (itemName.equals("GOLD_CHESTPLATE")) {
		maxDur = Material.GOLD_CHESTPLATE.getMaxDurability();
	} else if (itemName.equals("GOLD_LEGGINGS")) {
		maxDur = Material.GOLD_LEGGINGS.getMaxDurability();
	} else if (itemName.equals("GOLD_BOOTS")) {
		maxDur = Material.GOLD_BOOTS.getMaxDurability();
	} 
	
	if (itemName.equals("DIAMOND_HELMET")) {
		maxDur = Material.DIAMOND_HELMET.getMaxDurability();
	} else if (itemName.equals("DIAMOND_CHESTPLATE")) {
		maxDur = Material.DIAMOND_CHESTPLATE.getMaxDurability();
	} else if (itemName.equals("DIAMOND_LEGGINGS")) {
		maxDur = Material.DIAMOND_LEGGINGS.getMaxDurability();
	} else if (itemName.equals("DIAMOND_BOOTS")) {
		maxDur = Material.DIAMOND_BOOTS.getMaxDurability();
	} 
	
	if (itemName.equals("LEATHER_HELMET")) {
		maxDur = Material.LEATHER_HELMET.getMaxDurability();
	} else if (itemName.equals("LEATHER_CHESTPLATE")) {
		maxDur = Material.LEATHER_CHESTPLATE.getMaxDurability();
	} else if (itemName.equals("LEATHER_LEGGINGS")) {
		maxDur = Material.LEATHER_LEGGINGS.getMaxDurability();
	} else if (itemName.equals("LEATHER_BOOTS")) {
		maxDur = Material.LEATHER_BOOTS.getMaxDurability();
	} 
	
	
	
	return maxDur;
	
}

public static void banItem(Player player, String[] args) {
	
	if (args.length != 2) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/banitem [Item] [sale|purchase]");
		   Utility.writeToLog(player.getName() + " incorrectly called /banitem");
	} else {
		String item = getTrueName(args[0]);
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item does not exist.");
			Utility.writeToLog(player.getName() + " called /banitem on the non-existant item " + args[0]);
		} else {
			if ((args[1].equalsIgnoreCase("sale")) || (args[1].equalsIgnoreCase("purchase"))) {
				try {
					DynamicEconomy.config.load(DynamicEconomy.configFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				FileConfiguration conf = DynamicEconomy.config;
				
				String bannedString = "";
				
				if (args[1].equalsIgnoreCase("sale")) {
					bannedString = conf.getString("banned-sale-items","");
				} else if (args[1].equalsIgnoreCase("purchase")) {
					bannedString = conf.getString("banned-purchase-items","");
				}
				
				
				String banToken = "";
				
				if (bannedString.length() == 0) {
					banToken = item;
				} else {
					banToken = "," + item;
				}
				
				if (args[1].equalsIgnoreCase("sale")) {
					conf.set("banned-sale-items",bannedString + banToken);
				} else if (args[1].equalsIgnoreCase("purchase")) {
					conf.set("banned-purchase-items",bannedString + banToken);
				}
				
				try {
					DynamicEconomy.config.save(DynamicEconomy.configFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				DynamicEconomy.relConfig();
				
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[0] + "&2 banned from &f" + args[1] + "&2 succesfully.");
				Utility.writeToLog(player.getName() + " banned " + args[0] + " from " + args[1]);
				
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Only use &fsale &2or &fpurchase");
				Utility.writeToLog(player.getName() + " called /banitem with invalid type '" + args[1] + "'");
			}
		}
		
	}
	
	
	
	
	
}


public static void unbanItem(Player player, String[] args) {
	
	if (args.length != 2) {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/unbanitem [Item] [sale|purchase]");
		   Utility.writeToLog(player.getName() + " incorrectly called /unbanitem");
	} else {
		String item = getTrueName(args[0]);
		if (item.equals("")) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item does not exist.");
			Utility.writeToLog(player.getName() + " called /unbanitem on the non-existant item " + args[0]);
		} else {
			if ((args[1].equalsIgnoreCase("sale")) || (args[1].equalsIgnoreCase("purchase"))) {
				try {
					DynamicEconomy.config.load(DynamicEconomy.configFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				FileConfiguration conf = DynamicEconomy.config;
				
				String bannedString = "";
				String[] bannedArray;
				
				if (args[1].equalsIgnoreCase("sale")) {
					bannedString = conf.getString("banned-sale-items","");
				} else if (args[1].equalsIgnoreCase("purchase")) {
					bannedString = conf.getString("banned-purchase-items","");
				}
				
				bannedArray = bannedString.split(",");
				boolean contains = Arrays.asList(bannedArray).contains(item);
				
				if (contains) {
					ArrayList<String> bannedArrayList = new ArrayList<String>(Arrays.asList(bannedArray));
					bannedArrayList.remove(item);
					
					String newBannedString = "";
					
					for (int x = 0; x < bannedArrayList.size(); x++) {
						if (x == 0) {
							newBannedString = newBannedString + bannedArrayList.get(x);
						} else {
							newBannedString = newBannedString + "," + bannedArrayList.get(x);
						}
					}
					
					if (args[1].equalsIgnoreCase("sale")) {
						conf.set("banned-sale-items",newBannedString);
					} else if (args[1].equalsIgnoreCase("purchase")) {
						conf.set("banned-purchase-items",newBannedString);
					}

					
					try {
						DynamicEconomy.config.save(DynamicEconomy.configFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					DynamicEconomy.relConfig();
					
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[0] + "&2 unbanned from &f" + args[1] + "&2 succesfully.");
					Utility.writeToLog(player.getName() + " unbanned " + args[0] + " from " + args[1]);
					
				} else {
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item is not banned.");
					Utility.writeToLog(player.getName() + " called /unbanitem on " + args[0] + " which is not banned.");
				}
				
				
				
				
				
				
				
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Only use &fsale &2or &fpurchase");
				Utility.writeToLog(player.getName() + " called /unbanitem with invalid type '" + args[1] + "'");
			}
		}
		
	}
	
	
	
	
	
}


public static void getDurCommand(Player player, String[] args) {
	int playerDur;
	String itemName;
	int maxDur;
	double percentDur;	
	int usesLeft;
	String stringPlay = player.getName();
	
	if (args.length == 0) {
		
int slot = player.getInventory().getHeldItemSlot();
		
		ItemStack playerItem = player.getInventory().getItem(slot);
		int itemID = playerItem.getTypeId();
	
		if (((itemID >= 256 ) && (itemID <= 258)) || ((itemID >= 267) && (itemID <= 279)) || ((itemID >= 298) && (itemID <= 317)) || ((itemID >= 283) && (itemID <= 286)) || ((itemID >= 290) && (itemID <= 294))) {
		
		playerDur = playerItem.getDurability();
		
		
		itemName = playerItem.getType().toString();
		
		maxDur = Item.getMaxDur(itemName);
		
		usesLeft = maxDur - playerDur;
		
		percentDur = (double)playerDur / (double)maxDur;
		percentDur *= 100;
		percentDur = 100 - percentDur;
		decFormat.applyPattern("#.##");
		percentDur = Double.valueOf(decFormat.format(percentDur));
		
		color.sendColouredMessage(player, "&2The durability of your item is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
		Utility.writeToLog(stringPlay + " called /getdurability for item with ID of '" + itemID + "'");
		} else {
			color.sendColouredMessage(player, Messages.itemHasNoDurability);
			Utility.writeToLog(stringPlay + " called /getdurability for item with ID of '" + itemID + "', but failed because this item has no durability.");
		}
	} else if (args.length == 1) {
		if (args[0].equalsIgnoreCase("armor")) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			int amountHelmet = armor[0].getAmount();
			int amountChestplate = armor[1].getAmount();
			int amountLeggings = armor[2].getAmount();
			int amountBoots = armor[3].getAmount();
			int amount;
		 if ((amountHelmet == 0) && (amountChestplate == 0) &&  (amountLeggings == 0) &&  (amountBoots == 0)) {	
			 color.sendColouredMessage(player, Messages.noArmorEquipped);
			 Utility.writeToLog(stringPlay + " attempted to call /getdurability armor, but has no armor equipped");
		 } else {
			 for (int x = 0; x < armor.length; x++) {
					playerDur = armor[x].getDurability();
					itemName = armor[x].getType().toString();
					maxDur = Item.getMaxDur(itemName);
					usesLeft = maxDur - playerDur;
					amount = armor[x].getAmount();
					percentDur = (double)playerDur / (double)maxDur;
					percentDur *= 100;
					percentDur = 100 - percentDur;
				if (amount == 0) {	
					if (x == 3) {
						color.sendColouredMessage(player, Messages.noHelmetEquipped);
						Utility.writeToLog(stringPlay + " called /getdurability armor without having a helmet equipped");
					} else if (x == 2) {
						color.sendColouredMessage(player, Messages.noChestplateEquipped);
						Utility.writeToLog(stringPlay + " called /getdurability armor without having a chestplate equipped");
					} else if (x == 1) {
						color.sendColouredMessage(player, Messages.noLeggingsEquipped);
						Utility.writeToLog(stringPlay + " called /getdurability armor without having leggings equipped");
					} else {
						color.sendColouredMessage(player, Messages.noBootsEquipped);
						Utility.writeToLog(stringPlay + " called /getdurability armor without having boots equipped");
					}
				} else {
					percentDur = Double.valueOf(decFormat.format(percentDur));
					color.sendColouredMessage(player, "&2The durability of &f" + itemName + "&2 is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
					Utility.writeToLog(stringPlay + " called /getdurability and found the durability of their '" + itemName + "' to be " + percentDur + "%");
				}
				}
		 }
		} else if (args[0].equalsIgnoreCase("helmet")) {
			ItemStack helmet = player.getInventory().getHelmet();
			int amount = helmet.getAmount();
		if (amount == 1) {	
			playerDur = helmet.getDurability();
			itemName = helmet.getType().toString();
			maxDur = Item.getMaxDur(itemName);
			usesLeft = maxDur - playerDur;
			percentDur = (double)playerDur / (double)maxDur;
			percentDur *= 100;
			percentDur = 100 - percentDur;
			percentDur = Double.valueOf(decFormat.format(percentDur));
			color.sendColouredMessage(player, "&2The durability of &f" + itemName + "&2 is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
			Utility.writeToLog(stringPlay + " called /getdurability and found the durability of their '" + itemName + "' to be " + percentDur + "%");
		} else {
			color.sendColouredMessage(player, Messages.noHelmetEquipped);
			Utility.writeToLog(stringPlay + " attempted to call /getdurability helmet, but did not have a helmet equipped");
		}
		} else if (args[0].equalsIgnoreCase("chestplate")) {
			ItemStack chestplate = player.getInventory().getChestplate();
			int amount = chestplate.getAmount();
		if (amount == 1) {	
			playerDur = chestplate.getDurability();
			itemName = chestplate.getType().toString();
			maxDur = Item.getMaxDur(itemName);
			usesLeft = maxDur - playerDur;
			percentDur = (double)playerDur / (double)maxDur;
			percentDur *= 100;
			percentDur = 100 - percentDur;
			percentDur = Double.valueOf(decFormat.format(percentDur));
			color.sendColouredMessage(player, "&2The durability of &f" + itemName + "&2 is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
			Utility.writeToLog(stringPlay + " called /getdurability and found the durability of their '" + itemName + "' to be " + percentDur + "%");
		} else {
			color.sendColouredMessage(player, Messages.noChestplateEquipped);
			Utility.writeToLog(stringPlay + " attempted to call /getdurability chestplate, but did not have a chestplate equipped");
		}
		} else if (args[0].equalsIgnoreCase("leggings")) {
			ItemStack leggings = player.getInventory().getLeggings();
			int amount = leggings.getAmount();
		if (amount == 1) {	
			playerDur = leggings.getDurability();
			itemName = leggings.getType().toString();
			maxDur = Item.getMaxDur(itemName);
			usesLeft = maxDur - playerDur;
			percentDur = (double)playerDur / (double)maxDur;
			percentDur *= 100;
			percentDur = 100 - percentDur;
			percentDur = Double.valueOf(decFormat.format(percentDur));
			color.sendColouredMessage(player, "&2The durability of &f" + itemName + "&2 is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
			Utility.writeToLog(stringPlay + " called /getdurability and found the durability of their '" + itemName + "' to be " + percentDur + "%");
		} else {
			color.sendColouredMessage(player, Messages.noLeggingsEquipped);
			Utility.writeToLog(stringPlay + " attempted to call /getdurability leggings, but did not have leggings equipped");
		}
		} else if (args[0].equalsIgnoreCase("boots")) {
			ItemStack boots = player.getInventory().getBoots();
			int amount = boots.getAmount();
		if (amount == 1) {	
			playerDur = boots.getDurability();
			itemName = boots.getType().toString();
			maxDur = Item.getMaxDur(itemName);
			usesLeft = maxDur - playerDur;
			percentDur = (double)playerDur / (double)maxDur;
			percentDur *= 100;
			percentDur = 100 - percentDur;
			percentDur = Double.valueOf(decFormat.format(percentDur));
			color.sendColouredMessage(player, "&2The durability of &f" + itemName + "&2 is at &f" + percentDur + "%.&2 You have &f" + usesLeft + "&2 uses left out of a possible &f" + maxDur);
			Utility.writeToLog(stringPlay + " called /getdurability and found the durability of their '" + itemName + "' to be " + percentDur + "%");
		} else {
			color.sendColouredMessage(player, Messages.noBootsEquipped);
			Utility.writeToLog(stringPlay + " attempted to call /getdurability boots, but did not have boots equipped");
		}
	} else {
		color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/getdurability (helmet/chestplate/leggings/boots/armor)");
		Utility.writeToLog(stringPlay + " incorrectly called /getdurability");
	}
	
}




	

	
}
}
	

