package me.ksafin.DynamicEconomy;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Extras.Colour.ExtrasColour;

public class regionUtils {

	private static File regionFile;
	private static FileConfiguration regionFileConfig;
	static Logger log = Logger.getLogger("Minecraft");
	private static final ExtrasColour color = new ExtrasColour();
	
	static NumberFormat f = NumberFormat.getNumberInstance(Locale.US);
    
	public static DecimalFormat decFormat = (DecimalFormat)f;
	
	public static void setRegionFile(File regFile) {
		regionFile = regFile;
	}
	
	public static void setRegionFileConfig(FileConfiguration regConfig) {
		regionFileConfig = regConfig;
	}
	
	public static boolean createRegion(Player player, String[] args) {
		if ((args.length == 1) && (args[0] instanceof String)) { 
			String name = player.getName();
		
			Object selectionsObject = DynamicEconomy.selectedCorners.get(name);
		
			if (selectionsObject == null) {
				color.sendColouredMessage(player, Messages.noRegionSelected);
				return false;
			}
		
			String[] selections = (String[]) selectionsObject;
			
			
			if ((selections[0] == null) || (selections[1] == null)) {
				color.sendColouredMessage(player, Messages.noRegionSelected);
				return false;
			}
			
			if ((selections[0].isEmpty()) || (selections[1].isEmpty())) {
				color.sendColouredMessage(player, Messages.noRegionSelected);
				return false;
			}
		
			
			
			int[] coord1 = Utility.decodeCoordinates(selections[0]);
			int[] coord2 = Utility.decodeCoordinates(selections[1]);
			
			
			
			String regionName = args[0];
			
			int xMax = 0;
			int yMax = 0;
			int zMax = 0;
			
			int xMin = 0;
			int yMin = 0;
			int zMin = 0;
			
			if (coord1[0] > coord2[0]) {
				xMax = coord1[0];
				xMin = coord2[0];
			} else {
				xMax = coord2[0];
				xMin = coord1[0];
			}
			
			if (coord1[1] > coord2[1]) {
				yMax = coord1[1];
				yMin = coord2[1];
			} else {
				yMax = coord2[1];
				yMin = coord1[1];
			}
			
			if (coord1[2] > coord2[2]) {
				zMax = coord1[2];
				zMin = coord2[2];
			} else {
				zMax = coord2[2];
				zMin = coord1[2];
			}
			
			regionName = regionName.toUpperCase();
			
			if (regionFileConfig.contains("regions." + regionName)) {
				color.sendColouredMessage(player, "&2Region already exists!");
				return false;
			}
			
			
			ConfigurationSection cornerOne = regionFileConfig.createSection("regions." + regionName + ".CornerOne");
			ConfigurationSection cornerTwo = regionFileConfig.createSection("regions." + regionName + ".CornerTwo");
			ConfigurationSection maxs = regionFileConfig.createSection("regions." + regionName + ".maxs");
			
			cornerOne.set("X", coord1[0]);
			cornerOne.set("Y", coord1[1]);
			cornerOne.set("Z", coord1[2]);
			
			cornerTwo.set("X", coord2[0]);
			cornerTwo.set("Y", coord2[1]);
			cornerTwo.set("Z", coord2[2]);
			
			maxs.set("xMax", xMax);
			maxs.set("yMax", yMax);
			maxs.set("zMax", zMax);
			
			maxs.set("xMin", xMin);
			maxs.set("yMin", yMin);
			maxs.set("zMin", zMin);
			

			ConfigurationSection flags = regionFileConfig.createSection("regions." + regionName + ".flags");
			
			ArrayList<String> allowed = new ArrayList<String>();
			allowed.add("*");
			
			ArrayList<String> allowed2 = new ArrayList<String>();
			allowed2.add("*");
			
			
			flags.set("purchasetax", 0.0);
			flags.set("salestax", 0.0);
			flags.set("banned-sale-items","");
			flags.set("banned-purchase-items","");
			flags.set("allowed-purchase-groups",allowed);
			flags.set("allowed-sale-groups",allowed2);
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + regionName + "&2 created!");
			Utility.writeToLog(player.getName() + " created region '" + regionName + "'");
			DynamicEconomy.selectedCorners.remove(name);
			
			try {
				regionFileConfig.save(regionFile);
				regionFileConfig.load(regionFile);
			} catch (Exception e) {
				log.info("[DynamicEconomy]IOException saving Region.yml");
			}
			
			return true;
		
		} else {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/shopregion [Name]");
			Utility.writeToLog(player.getName() + " incorrectly called /shopregion");
			return false;
		}
	}
	
	public static boolean withinRegion(int x, int y, int z) {
		Set<String> sectionsSet = regionFileConfig.getConfigurationSection("regions").getKeys(false);
		
		Object[] sectionsObj = (sectionsSet.toArray());
		String[] sections = new String[sectionsObj.length];
		
		for (int i = 0; i < sections.length; i++) {
			sections[i] = sectionsObj[i].toString();
		}
		
		if (sections.length == 0) {
			return false;
		}
		
		int regionXMin = 0;
		int regionYMin = 0;
		int regionZMin = 0;
		int regionXMax = 0;
		int regionYMax = 0;
		int regionZMax = 0;
		
		for (int i = 0; i < sections.length ; i++) {
			String section = sections[i];
			
			regionXMin = regionFileConfig.getInt("regions." + section + ".maxs.xMin");
			regionYMin = regionFileConfig.getInt("regions." + section + ".maxs.yMin");
			regionZMin = regionFileConfig.getInt("regions." + section + ".maxs.zMin");
			
			regionXMax = regionFileConfig.getInt("regions." + section + ".maxs.xMax");
			regionYMax = regionFileConfig.getInt("regions." + section + ".maxs.yMax");
			regionZMax = regionFileConfig.getInt("regions." + section + ".maxs.zMax");
			
			if (((x > regionXMin) && (x < regionXMax)) && ((y > regionYMin) && (y < regionYMax)) && ((z > regionZMin) && (z < regionZMax))) {
				return true;
			}
			
		}
		return false;
		
	}
	
	public static String getRegion(int x, int y, int z) {
		Set<String> sectionsSet = regionFileConfig.getConfigurationSection("regions").getKeys(false);
		
		Object[] sectionsObj = (sectionsSet.toArray());
		String[] sections = new String[sectionsObj.length];
		
		for (int i = 0; i < sections.length; i++) {
			sections[i] = sectionsObj[i].toString();
		}
		
		if (sections.length == 0) {
			return "";
		}
		
		int regionXMin = 0;
		int regionYMin = 0;
		int regionZMin = 0;
		int regionXMax = 0;
		int regionYMax = 0;
		int regionZMax = 0;
		
		for (int i = 0; i < sections.length ; i++) {
			String section = sections[i];
			
			regionXMin = regionFileConfig.getInt("regions." + section + ".maxs.xMin");
			regionYMin = regionFileConfig.getInt("regions." + section + ".maxs.yMin");
			regionZMin = regionFileConfig.getInt("regions." + section + ".maxs.zMin");
			
			regionXMax = regionFileConfig.getInt("regions." + section + ".maxs.xMax");
			regionYMax = regionFileConfig.getInt("regions." + section + ".maxs.yMax");
			regionZMax = regionFileConfig.getInt("regions." + section + ".maxs.zMax");
			
			if (((x > regionXMin) && (x < regionXMax)) && ((y > regionYMin) && (y < regionYMax)) && ((z > regionZMin) && (z < regionZMax))) {
				return section;
			}
			
		}
		return "";
		
	}
	
	public static boolean expandRegion(Player player, String[] args) {
		String stringPlay = player.getName();
		if ((args.length != 2) && (args.length != 1)){
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/expandreg [north|south|east|west|up|down|vert] (amount)");
			 Utility.writeToLog(stringPlay + " incorrectly called /expandreg");
			return false;
		} else {
			String dir = "";
			int amt = 0;
			try {
				dir = args[0];
				
				if (!(dir.equalsIgnoreCase("vert"))) {
					amt = Integer.parseInt(args[1]);
				}
				
			} catch (Exception e) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/expandreg [north|south|east|west|up|down|vert] (amount)");
				return false;
			}
			
			Object selections = DynamicEconomy.selectedCorners.get(stringPlay);
			
			String[] selectionsArr = (String[]) selections;
			
			
			if ((selectionsArr[0] == null) || (selectionsArr[1] == null)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
		
			
			
			int[] coord1 = Utility.decodeCoordinates(selectionsArr[0]);
			int[] coord2 = Utility.decodeCoordinates(selectionsArr[1]);
			
			amt = Math.abs(amt);
			
			String xMax = "";
			String yMax = "";
			String zMax = "";
			
			String xMin = "";
			String yMin = "";
			String zMin = "";
			
			if (coord1[0] > coord2[0]) {
				xMax = "1";
				xMin = "2";
			} else {
				xMax = "2";
				xMin = "1";
			}
			
			if (coord1[1] > coord2[1]) {
				yMax = "1";
				yMin = "2";
			} else {
				yMax = "2";
				yMin = "1";
			}
			
			if (coord1[2] > coord2[2]) {
				zMax = "1";
				zMin = "2";
			} else {
				zMax = "2";
				zMin = "1";
			}
			
			if (dir.equalsIgnoreCase("up")) {
				if (yMax.equals("1")) {
					coord1[1] = coord1[1] + amt;
					if (coord1[1] > Bukkit.getServer().getWorlds().get(0).getMaxHeight()) {
						coord1[1] = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
					}
				} else {
					coord2[1] = coord2[1] + amt;
					if (coord2[1] > Bukkit.getServer().getWorlds().get(0).getMaxHeight()) {
						coord2[1] = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
					}
				}
			} else if (dir.equalsIgnoreCase("down")) {
				if (yMin.equals("1")) {
					coord1[1] = coord1[1] - amt;
					if (coord1[1] < 0) {
						coord1[1] = 0;
					}
				} else {
					coord2[1] = coord2[1] - amt;
					if (coord2[1] < 0) {
						coord2[1] = 0;
					}
				}
			} else if (dir.equalsIgnoreCase("west")) {
				if (zMax.equals("1")) {
					coord1[2] = coord1[2] + amt;
				} else {
					coord2[2] = coord2[2] + amt;
				}
			} else if (dir.equalsIgnoreCase("east")) {
				if (zMin.equals("1")) {
					coord1[2] = coord1[2] - amt;
				} else {
					coord2[2] = coord2[2] - amt;
				}
			} else if (dir.equalsIgnoreCase("north")) {
				if (xMin.equals("1")) {
					coord1[0] = coord1[0] - amt;
				} else {
					coord2[0] = coord2[0] - amt;
				}
			} else if (dir.equalsIgnoreCase("south")) {
				if (xMax.equals("1")) {
					coord1[0] = coord1[0] + amt;
				} else {
					coord2[0] = coord2[0] + amt;
				}
			} else if (dir.equalsIgnoreCase("vert")) {
				if (yMax.equals("1")) {
					coord1[1] = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
					coord2[1] = 0;
				} else {
					coord2[1] = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
					coord1[1] = 0;
				}
				
			}
			
			String coords1 = Utility.encodeCoordinates(coord1);
			String coords2 = Utility.encodeCoordinates(coord2);
			
			String[] coordsArray = {coords1,coords2};
			
			DynamicEconomy.selectedCorners.put(stringPlay, coordsArray);
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.regionExpanded);
			return true;
			
		}
	}
	
	public static boolean contractRegion(Player player, String[] args) {
		String stringPlay = player.getName();
		if ((args.length != 2) && (args.length != 1)){
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/expandreg [north|south|east|west|up|down|vert] (amount)");
			 Utility.writeToLog(stringPlay + " incorrectly called /expandreg");
			return false;
		} else {
			String dir = "";
			int amt = 0;
			try {
				dir = args[0];
				
				if (!(dir.equalsIgnoreCase("vert"))) {
					amt = Integer.parseInt(args[1]);
				}
				
			} catch (Exception e) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/expandreg [north|south|east|west|up|down|vert] (amount)");
				return false;
			}
			
			Object selections = DynamicEconomy.selectedCorners.get(stringPlay);
			
			String[] selectionsArr = (String[]) selections;
			
			
			if ((selectionsArr[0] == null) || (selectionsArr[1] == null)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
		
			
			
			int[] coord1 = Utility.decodeCoordinates(selectionsArr[0]);
			int[] coord2 = Utility.decodeCoordinates(selectionsArr[1]);
			
			amt = Math.abs(amt);
			
			String xMax = "";
			String yMax = "";
			String zMax = "";
			
			String xMin = "";
			String yMin = "";
			String zMin = "";
			
			if (coord1[0] > coord2[0]) {
				xMax = "1";
				xMin = "2";
			} else {
				xMax = "2";
				xMin = "1";
			}
			
			if (coord1[1] > coord2[1]) {
				yMax = "1";
				yMin = "2";
			} else {
				yMax = "2";
				yMin = "1";
			}
			
			if (coord1[2] > coord2[2]) {
				zMax = "1";
				zMin = "2";
			} else {
				zMax = "2";
				zMin = "1";
			}
			
			if (dir.equalsIgnoreCase("up")) {
				if (yMin.equals("1")) {
					coord1[1] = coord1[1] + amt;
					if (coord1[1] > 128) {
						coord1[1] = 128;
					}
				} else {
					coord2[1] = coord2[1] + amt;
					if (coord2[1] > 128) {
						coord2[1] = 128;
					}
				}
			} else if (dir.equalsIgnoreCase("down")) {
				if (yMax.equals("1")) {
					coord1[1] = coord1[1] - amt;
					if (coord1[1] < 0) {
						coord1[1] = 0;
					}
				} else {
					coord2[1] = coord2[1] - amt;
					if (coord2[1] < 0) {
						coord2[1] = 0;
					}
				}
			} else if (dir.equalsIgnoreCase("west")) {
				if (zMin.equals("1")) {
					coord1[2] = coord1[2] + amt;
				} else {
					coord2[2] = coord2[2] + amt;
				}
			} else if (dir.equalsIgnoreCase("east")) {
				if (zMax.equals("1")) {
					coord1[2] = coord1[2] - amt;
				} else {
					coord2[2] = coord2[2] - amt;
				}
			} else if (dir.equalsIgnoreCase("north")) {
				if (xMax.equals("1")) {
					coord1[0] = coord1[0] - amt;
				} else {
					coord2[0] = coord2[0] - amt;
				}
			} else if (dir.equalsIgnoreCase("south")) {
				if (xMin.equals("1")) {
					coord1[0] = coord1[0] + amt;
				} else {
					coord2[0] = coord2[0] + amt;
				}
			}
			
			String coords1 = Utility.encodeCoordinates(coord1);
			String coords2 = Utility.encodeCoordinates(coord2);
			
			String[] coordsArray = {coords1,coords2};
			
			DynamicEconomy.selectedCorners.put(stringPlay, coordsArray);
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.regionContracted);
			return true;
			
		}
	}
	
	public static boolean wand(Player player, String[] args) {
		ItemStack wand = new ItemStack(org.bukkit.Material.WOOD_SPADE);
		Inventory inv = player.getInventory();
		inv.addItem(wand);
		return true;
	}
	
	public static boolean toggleWand(Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length != 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/togglewand");
			Utility.writeToLog(stringPlay + " incorrectly called /togglewand");
			return false;
		} else {
			DynamicEconomy.isWandOn = !DynamicEconomy.isWandOn;
			if (DynamicEconomy.isWandOn) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wand enabled.");
				Utility.writeToLog(stringPlay + " enabled region selection wand.");
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wand disabled.");
				Utility.writeToLog(stringPlay + " disabled region selection wand.");
			}
			return true;
		}
	}
	
	public static boolean getCorners (Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length != 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/curselectedregion");
			 Utility.writeToLog(stringPlay + " incorrectly called /curselectedregion");
			return false;
		} else {
			Object selections = DynamicEconomy.selectedCorners.get(stringPlay);
			
			String[] selectionsArr = (String[]) selections;
			
			if (selectionsArr == null) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
			
			if ((selectionsArr[0] == null) || (selectionsArr[1] == null)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + Messages.noRegionSelected);
				return false;
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Current Region Selections:");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Block 1: &f" + selectionsArr[0]);
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Block 2: &f" + selectionsArr[1]);
			return true;
		}
				
	}
	
	public static void curRegion(Player player, String[] args) {
		if (args.length != 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/curregion");
			Utility.writeToLog(player.getName() + " incorrectly called /curregion");
		} else {
			if (DynamicEconomy.useRegions == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Regions are disabled!");
				Utility.writeToLog(player.getName() + " called /curregion, but regions are disabled.");
				return;
			}
			
			Location loc = player.getLocation();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			
			String reg = getRegion(x, y, z);
			
			if (reg.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are not in any region!");
				Utility.writeToLog(player.getName() + " called /curregion but was not in a region");
				return;
			}
			
			String node = "regions." + reg;
			
			int x1 = regionFileConfig.getInt(node + ".maxs.xMax");
			int y1 = regionFileConfig.getInt(node + ".maxs.yMax");
			int z1 = regionFileConfig.getInt(node + ".maxs.zMax");
			
			int x2 = regionFileConfig.getInt(node + ".maxs.xMin");
			int y2 = regionFileConfig.getInt(node + ".maxs.yMin");
			int z2 = regionFileConfig.getInt(node + ".maxs.zMin");
			
			node = node + ".flags";
			
			decFormat.applyPattern("#.##");
			String purchasetax = decFormat.format(regionFileConfig.getDouble(node + ".purchasetax") * 100);
			String salestax = decFormat.format(regionFileConfig.getDouble(node + ".salestax") * 100);
			
			String bannedSales = regionFileConfig.getString(node + ".banned-sale-items");
			String bannedPurchases = regionFileConfig.getString(node + ".banned-purchase-items");
			
			List<String> purchaseGroups = null;
			List<String> salesGroups = null;
			
			if (DynamicEconomy.groupControl) {
				purchaseGroups = regionFileConfig.getStringList(node + ".allowed-purchase-groups");
				salesGroups = regionFileConfig.getStringList(node + ".allowed-sale-groups");
			}
			
			color.sendColouredMessage(player, "&2-----------------&fRegion" + reg + "&2------------------");
			color.sendColouredMessage(player, "&2X Range: &f" + x2 + "&2 to &f" + x1);
			color.sendColouredMessage(player, "&2Y Range: &f" + y2 + "&2 to &f" + y1);
			color.sendColouredMessage(player, "&2Z Range: &f" + z2 + "&2 to &f" + z1);
			color.sendColouredMessage(player, "&2Sales Tax: &f" + salestax + "%&2 | Purchase Tax: &f" + purchasetax + "%");
			color.sendColouredMessage(player, "&2Banned Sale Items: &f" + bannedSales);
			color.sendColouredMessage(player, "&2Banned Purchase Items: &f" + bannedPurchases);
			
			if (DynamicEconomy.groupControl) {
				color.sendColouredMessage(player, "&2Allowed Purchase Groups: &f" + Utility.listToString(purchaseGroups));
				color.sendColouredMessage(player, "&2Allowed Sale Groups: &f" + Utility.listToString(salesGroups));
			}
			
			color.sendColouredMessage(player, "&2----------------------------------------------------");
			
		}
	}
	
	public static void removeItemGroup(Player player, String[] args) {
		if (args.length != 3) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removeregiongroup [Region] [Sale|Purchase] [ItemGroupName]");
			 Utility.writeToLog(player.getName() + " incorrectly called /removeregiongroup");
			 return;
		} else {
			args[2] = args[2].toUpperCase();
			args[0] = args[0].toUpperCase();
			
			String node = "regions." + args[0];
			
			if(regionFileConfig.contains(node) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + args[0] + "&2 does not exist.");
				Utility.writeToLog(player.getName() + " called /removeregiongroup with non-existant region " + args[0]);
				return;
			}
			
			if((DynamicEconomy.groupsConfig.contains(args[2]) == false) && (args[2].equals("*") == false)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[2] + "&2 does not exist.");
				Utility.writeToLog(player.getName() + " called /removeregiongroup with non-existant group " + args[2]);
				return;
			}
			
			if (args[1].equalsIgnoreCase("purchase")) {
				node = node + ".flags.allowed-purchase-groups";
			} else if (args[1].equalsIgnoreCase("sale")) {
				node = node + ".flags.allowed-sale-groups";
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Use either 'Sale' or 'Purchase'");
				 Utility.writeToLog(player.getName() + " called /removeregiongroup with non-existant type " + args[1]);
			}
			
			List<String> groups = regionFileConfig.getStringList(node);
			
			if (groups.contains(args[2]) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[0] + " &2 is not allowed as it is.");
				return;
			}
			
			groups.remove(args[2]);
			regionFileConfig.set(node,groups);
			
			try {
				regionFileConfig.save(regionFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving Regions.yml in removeItemGroup()");
			}
			
			DynamicEconomy.relConfig();
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[2] + " &2 removed from region &f" + args[0]);
			Utility.writeToLog(player.getName() + " removed Item Group " + args[2] + " from region " + args[0]);
		}
	}
	
	public static void addItemGroup(Player player, String[] args) {
		if (args.length != 3) {
			 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/addregiongroup [Region] [Sale|Purchase] [ItemGroupName]");
			 Utility.writeToLog(player.getName() + " incorrectly called /addregiongroup");
			 return;
		} else {
			args[2] = args[2].toUpperCase();
			args[0] = args[0].toUpperCase();
			
			String node = "regions." + args[0];
			
			if(regionFileConfig.contains(node) == false) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + args[0] + "&2 does not exist.");
				Utility.writeToLog(player.getName() + " called /addregiongroup with non-existant region " + args[0]);
				return;
			}
			
			if((DynamicEconomy.groupsConfig.contains(args[2]) == false) && (args[2].equals("*") == false)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[2] + "&2 does not exist.");
				Utility.writeToLog(player.getName() + " called /addregiongroup with non-existant group " + args[2]);
				return;
			}
			
			if (args[1].equalsIgnoreCase("purchase")) {
				node = node + ".flags.allowed-purchase-groups";
			} else if (args[1].equalsIgnoreCase("sale")) {
				node = node + ".flags.allowed-sale-groups";
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Use either 'Sale' or 'Purchase'");
				 Utility.writeToLog(player.getName() + " called /addregiongroup with non-existant type " + args[1]);
			}
			
			List<String> groups = regionFileConfig.getStringList(node);
			
			if(groups.contains(args[2])) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[2] + "&2 is already allowed for " + args[1]);
				return;
			}
			
			groups.add(args[2]);
			
			regionFileConfig.set(node,groups);
			
			try {
				regionFileConfig.save(regionFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("[DynamicEconomy] Error saving Regions.yml in addItemGroup()");
			}
			
			DynamicEconomy.relConfig();
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Item Group &f" + args[2] + " &2 added to region &f" + args[0]);
			Utility.writeToLog(player.getName() + " added Item Group " + args[2] + " to region " + args[0]);
		}
	}
	
	public static void banRegionItem(Player player, String[] args) {
		
		if (args.length != 3) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/banitem [Region] [sale|purchase] [Item]");
			   Utility.writeToLog(player.getName() + " incorrectly called /banregionitem");
		} else {
			String item = Item.getTrueName(args[2]);
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item does not exist.");
				Utility.writeToLog(player.getName() + " called /banregionitem on the non-existant item " + args[0]);
			} else {
				
					args[0] = args[0].toUpperCase();
					args[1] = args[1].toUpperCase();
				
					try {
						regionFileConfig.load(regionFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String node = "regions." + args[0];
					
					if(regionFileConfig.contains(node) == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + args[0] + "&2 does not exist.");
						Utility.writeToLog(player.getName() + " called /banregionitem with non-existant region " + args[0]);
						return;
					}
					
					if (args[1].equalsIgnoreCase("purchase")) {
						node = node + ".flags.banned-purchase-items";
					} else if (args[1].equalsIgnoreCase("sale")) {
						node = node + ".flags.banned-sale-items";
					} else {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Use either 'Sale' or 'Purchase'");
						 Utility.writeToLog(player.getName() + " called /banregionitem with non-existant type " + args[1]);
					}
					
					String bannedString = "";
					
					bannedString = regionFileConfig.getString(node);
					
					if (bannedString.contains(item)) {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + item + "&2 is already banned in this region.");
					    Utility.writeToLog(player.getName() + " tried to ban " + item + " in region " + args[0] + " but it is already banned.");
					    return;
					}
					
					if (bannedString.length() == 0) {
						bannedString = item;
					} else {
						bannedString += "," + item;
					}
					
					regionFileConfig.set(node, bannedString);
					
					try {
						regionFileConfig.save(regionFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					DynamicEconomy.relConfig();
					
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + item + "&2 banned from &f" + args[1] + "&2 in region &f" + args[0] + "&2 succesfully.");
					Utility.writeToLog(player.getName() + " banned " + item + " from " + args[1] + " in region " + args[0]);
			}
			
		}
		
		
		
		
		
	}
	
public static void unbanRegionItem(Player player, String[] args) {
		
		if (args.length != 3) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/unbanitem [Region] [sale|purchase] [Item]");
			   Utility.writeToLog(player.getName() + " incorrectly called /unbanregionitem");
		} else {
			String item = Item.getTrueName(args[2]);
			if (item.equals("")) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2This item does not exist.");
				Utility.writeToLog(player.getName() + " called /unbanregionitem on the non-existant item " + args[0]);
			} else {
				
					args[0] = args[0].toUpperCase();
					args[1] = args[1].toUpperCase();
				
					try {
						regionFileConfig.load(regionFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String node = "regions." + args[0];
					
					if(regionFileConfig.contains(node) == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + args[0] + "&2 does not exist.");
						Utility.writeToLog(player.getName() + " called /unbanregionitem with non-existant region " + args[0]);
						return;
					}
					
					if (args[1].equalsIgnoreCase("purchase")) {
						node = node + ".flags.banned-purchase-items";
					} else if (args[1].equalsIgnoreCase("sale")) {
						node = node + ".flags.banned-sale-items";
					} else {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + args[1] + "&2 is not a valid type. Use either 'Sale' or 'Purchase'");
						 Utility.writeToLog(player.getName() + " called /unbanregionitem with non-existant type " + args[1]);
					}
					
					String bannedString = "";
					
					bannedString = regionFileConfig.getString(node);
					
					if (bannedString.contains(item) == false) {
						color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + item + "&2 isn't banned in this region as it is.");
					    Utility.writeToLog(player.getName() + " tried to unban " + item + " in region " + args[0] + " but it isn't banned.");
					    return;
					}
					
					String[] splitBanned = bannedString.split(",");
					ArrayList<String> splitList = new ArrayList<String>(Arrays.asList(splitBanned));
					splitList.remove(item);
					
					String newBanned = "";
					
					for (int x = 0; x < splitList.size(); x++) {
						newBanned += splitList.get(x) + ",";
					}
					
					if (newBanned.length() != 0) {
						newBanned = newBanned.substring(0,newBanned.length() - 1);
					}
					
					regionFileConfig.set(node, newBanned);
					
					try {
						regionFileConfig.save(regionFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					DynamicEconomy.relConfig();
					
					color.sendColouredMessage(player, DynamicEconomy.prefix + "&f" + item + "&2 unbanned from &f" + args[1] + "&2 in region &f" + args[0] + "&2 succesfully.");
					Utility.writeToLog(player.getName() + " unbanned " + item + " from " + args[1] + " in region " + args[0]);
			}
			
		}
		
		
		
		
		
	}
	
	
	
	public static boolean deleteShopRegion(Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length != 1) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/removeshopregion [name]");
			 Utility.writeToLog(stringPlay + " incorrectly called /removeshopregion");
			return false;
		} else {
			String region = args[0];
			region = region.toUpperCase();
			String node = "regions." + region;
			if (regionFileConfig.contains(node)) {
				regionFileConfig.set(node, null);
				try {
					regionFileConfig.save(regionFile);
				} catch (Exception e) {
					log.info("Erro Saving Regions.yml");
					Utility.writeToLog(stringPlay + "Error Saving regions.yml");
				}
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + region + "&2 deleted.");
				Utility.writeToLog(stringPlay + " removed region '" + region + "'");
				return true;
			} else {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region &f" + region + "&2 not found.");
				Utility.writeToLog(stringPlay + " attempted to remove the non-existant region '" + region + "'");
				return false;
			}
		}
	}
	
	
	
}
