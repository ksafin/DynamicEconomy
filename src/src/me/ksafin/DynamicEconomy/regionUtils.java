package me.ksafin.DynamicEconomy;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
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
				color.sendColouredMessage(player, "&2No region selected!");
				return false;
			}
		
			String[] selections = (String[]) selectionsObject;
			
			
			if ((selections[0] == null) || (selections[1] == null)) {
				color.sendColouredMessage(player, "&2No region selected!");
				return false;
			}
			
			if ((selections[0].isEmpty()) || (selections[1].isEmpty())) {
				color.sendColouredMessage(player, "&2No region selected!");
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
			
			
			String block1X = "regions." + regionName.toUpperCase() + ".CornerOne.X";
			String block1Y = "regions." + regionName.toUpperCase() + ".CornerOne.Y";
			String block1Z = "regions." + regionName.toUpperCase() + ".CornerOne.Z";
			
			String block2X = "regions." + regionName.toUpperCase() + ".CornerTwo.X";
			String block2Y = "regions." + regionName.toUpperCase() + ".CornerTwo.Y";
			String block2Z = "regions." + regionName.toUpperCase() + ".CornerTwo.Z";
			
			String xMaxStr = "regions." + regionName.toUpperCase() + ".xMax";
			String yMaxStr = "regions." + regionName.toUpperCase() + ".yMax";
			String zMaxStr = "regions." + regionName.toUpperCase() + ".zMax";
			
			String xMinStr = "regions." + regionName.toUpperCase() + ".xMin";
			String yMinStr = "regions." + regionName.toUpperCase() + ".yMin";
			String zMinStr = "regions." + regionName.toUpperCase() + ".zMin";
			
			if (regionFileConfig.contains(regionName)) {
				color.sendColouredMessage(player, "&2Region already exists!");
				return false;
			}
			
			regionFileConfig.set(block1X, coord1[0]);
			regionFileConfig.set(block1Y, coord1[1]);
			regionFileConfig.set(block1Z, coord1[2]);
			
			regionFileConfig.set(block2X, coord2[0]);
			regionFileConfig.set(block2Y, coord2[1]);
			regionFileConfig.set(block2Z, coord2[2]);
			
			regionFileConfig.set(xMaxStr, xMax);
			regionFileConfig.set(yMaxStr, yMax);
			regionFileConfig.set(zMaxStr, zMax);
			
			regionFileConfig.set(xMinStr, xMin);
			regionFileConfig.set(yMinStr, yMin);
			regionFileConfig.set(zMinStr, zMin);
			
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
		
		//Utility.writeToLog("# of regions: " + sections.length);
		//Utility.writeToLog("region: " + sections[0]);
		
		for (int i = 0; i < sections.length ; i++) {
			String section = sections[i];
			
			regionXMin = regionFileConfig.getInt("regions." + section + ".xMin");
			regionYMin = regionFileConfig.getInt("regions." + section + ".yMin");
			regionZMin = regionFileConfig.getInt("regions." + section + ".zMin");
			
			regionXMax = regionFileConfig.getInt("regions." + section + ".xMax");
			regionYMax = regionFileConfig.getInt("regions." + section + ".yMax");
			regionZMax = regionFileConfig.getInt("regions." + section + ".zMax");
			
			/*if (x > regionXMin) {
				Utility.writeToLog("User X above XMin");
			}
			if (x < regionXMax) {
				Utility.writeToLog("User X below XMax");
			}
			if (y > regionYMin) {
				Utility.writeToLog("User Y above YMin");
			}
			if (y < regionYMax) {
				Utility.writeToLog("User Y below YMax");
			}
			if (z > regionZMin) {
				Utility.writeToLog("User Z above ZMin");
			}
			if (z < regionZMin) {
				Utility.writeToLog("User Z below ZMax");
			}*/
			
			if (((x > regionXMin) && (x < regionXMax)) && ((y > regionYMin) && (y < regionYMax)) && ((z > regionZMin) && (z < regionZMax))) {
				return true;
			}
			
		}
		return false;
		
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
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
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
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region expanded!");
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
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
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
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Region expanded!");
			return true;
			
		}
	}
	
	public static boolean wand(Player player, String[] args) {
		ItemStack wand = new ItemStack(org.bukkit.Material.WOOD_SPADE);
		Inventory inv = player.getInventory();
		inv.addItem(wand);
		return true;
	}
	
	public static boolean getCorners (Player player, String[] args) {
		String stringPlay = player.getName();
		if (args.length != 0) {
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/curregion");
			 Utility.writeToLog(stringPlay + " incorrectly called /curregion");
			return false;
		} else {
			Object selections = DynamicEconomy.selectedCorners.get(stringPlay);
			
			String[] selectionsArr = (String[]) selections;
			
			
			if ((selectionsArr[0] == null) || (selectionsArr[1] == null)) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
				return false;
			}
			
			if ((selectionsArr[0].isEmpty()) || (selectionsArr[1].isEmpty())) {
				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2No region selected!");
				return false;
			}
			
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Current Region Selections:");
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Block 1: &f" + selectionsArr[0]);
			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Block 2: &f" + selectionsArr[1]);
			return true;
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
