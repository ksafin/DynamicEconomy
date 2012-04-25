package me.ksafin.DynamicEconomy;

import java.text.DecimalFormat;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.block.Sign;

public class dataSigns {
	
	static DecimalFormat format = new DecimalFormat("#.##");

	public dataSigns(final SignChangeEvent event) {
		String[] lines = event.getLines();
    	if (lines[0].equalsIgnoreCase("dynamicsign")) {
    		String item = lines[1];
    		String info = lines[2];
    		
    		FileConfiguration conf = DynamicEconomy.signsConfig;   
    		
    		try {
    			conf.load(DynamicEconomy.signsFile);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		Block block = event.getBlock();
    		int x = block.getX();
    		int y = block.getY();
    		int z = block.getZ();
    		
    		String signID = x + " " + y + " " + z;
    		
    		ConfigurationSection curSign = conf.createSection(signID);
    		curSign.set("WORLD", block.getWorld().getName());
    		
    		
    		
    		String[] itemInfo = Item.getAllInfo(item);
    		item = Item.getTrueName(item);
    		String data;
    		
    		if (Integer.parseInt(itemInfo[5]) != 0) {
    			if (info.equalsIgnoreCase("price")) {
    				data = "$" + format.format((DynamicEconomy.itemConfig.getDouble(item + ".price",0)));
    			} else if (info.equalsIgnoreCase("stock")) {
    				data = String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".stock",0));
    			} else if (info.equalsIgnoreCase("velocity")) {
    				data = String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".velocity",0));
    			} else if (info.equalsIgnoreCase("ceiling")) {
    				data = "$" + String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".ceiling",0));
    			} else if (info.equalsIgnoreCase("floor")) {
    				data = "$" + String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".floor",0));
    			} else {
    				data = "0";
    			}
    			
    			String tagLine = item + " " + info.toUpperCase();
    			
    			if (!data.equals("0")) {
    				event.setLine(0, Utility.getColor(DynamicEconomy.signTaglineColor) + item);
        			event.setLine(1, Utility.getColor(DynamicEconomy.signTaglineColor) + info.toUpperCase());
            		event.setLine(2, Utility.getColor(DynamicEconomy.signInfoColor) + data);
            		event.setLine(3,"");

            		curSign.set("ITEM", item);
            		curSign.set("TYPE", info);
            		
            		try {
            			conf.save(DynamicEconomy.signsFile);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
    			} else {
    				event.setLine(0, "");
        			event.setLine(1, Utility.getColor(DynamicEconomy.signInvalidColor) + "INVALID");
            		event.setLine(2, Utility.getColor(DynamicEconomy.signInvalidColor) + "ARGUMENTS");
            		event.setLine(3, "");
    			}
    			
    			
    			
    			
    		}
    		
    	}
	}
	
	public static void removeDataSign(Block block) {
		FileConfiguration conf = DynamicEconomy.signsConfig;
		
		try {
			conf.load(DynamicEconomy.signsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		String signID = x + " " + y + " " + z;
		
		
		if(conf.contains(signID)) {
			conf.set(signID, null);
			try {
				conf.save(DynamicEconomy.signsFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void checkForUpdates(String item, int changeStock, double changePrice) {
		FileConfiguration conf = DynamicEconomy.signsConfig;
		
		Set<String> set = conf.getKeys(false);
		Object[] signsObj = set.toArray();
		String[] signs = new String[signsObj.length];
		
		for (int x = 0; x < signsObj.length; x++) {
			signs[x] = signsObj[x].toString();
		}
		
		String request;
		String itemName;
		String signType;
		
		for (int x = 0; x < signs.length; x ++) {
			request = signs[x] + ".ITEM";
			itemName = conf.getString(request);
			
			if (itemName.equalsIgnoreCase(item)) {
					updateItem(item,signs[x],changeStock,changePrice);
			}
			
		}
	}
	
	public static void checkForUpdatesNonRegular(String item, double changeVelocity, double changeCeiling, double changeFloor) {
		FileConfiguration conf = DynamicEconomy.signsConfig;
		
		Set<String> set = conf.getKeys(false);
		Object[] signsObj = set.toArray();
		String[] signs = new String[signsObj.length];
		
		for (int x = 0; x < signsObj.length; x++) {
			signs[x] = signsObj[x].toString();
		}
		
		String request;
		String itemName;
		String signType;
		
		for (int x = 0; x < signs.length; x ++) {
			request = signs[x] + ".ITEM";
			itemName = conf.getString(request);
			
			if (itemName.equalsIgnoreCase(item)) {
					updateItem(item,signs[x],changeVelocity,changeCeiling,changeFloor);
				
			}
			
		}
	}
	
	public static void updateItem(String item, String signID, int changeStock, double changePrice) {
		FileConfiguration conf = DynamicEconomy.signsConfig;
		
		try {
			DynamicEconomy.itemConfig.load(DynamicEconomy.itemsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String[] splitID = signID.split(" ");
		int x = Integer.parseInt(splitID[0]);
		int y = Integer.parseInt(splitID[1]);
		int z = Integer.parseInt(splitID[2]);
		
		String node = signID + ".WORLD";
		String worldName = conf.getString(node,"world");
	
		Location loc = new Location(Bukkit.getServer().getWorld(worldName),x,y,z);
		Block block = loc.getBlock();
		Sign sign = (Sign) block.getState();
		
		String data = "";
		
		String type = conf.getString(signID + ".TYPE");
		String change = "";
		
		if ((type.equalsIgnoreCase("price")) && (changePrice != 0)) {
			data = "$" + format.format(DynamicEconomy.itemConfig.getDouble(item + ".price",0));
			if (changePrice > 0) {
				change = "+" + format.format(changePrice);
			} else {
				change = format.format(changePrice) + "";
			}
		} else if ((type.equalsIgnoreCase("stock")) && (changeStock != 0)) {
			data = String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".stock",0));
			if (changeStock > 0) {
				change = "+" + changeStock;
			} else {
				change = changeStock + "";
			}
			
		}
		
		if (!change.equals("")) {
			sign.setLine(2, Utility.getColor(DynamicEconomy.signInfoColor) + data);
			sign.setLine(3, Utility.getColor(DynamicEconomy.signInfoColor) + "(" + change + ")");
		}
		
		sign.update();
	}
	
	public static void updateItem(String item, String signID, double changeVelocity, double changeCeiling, double changeFloor) {
		FileConfiguration conf = DynamicEconomy.signsConfig;
		
		try {
			DynamicEconomy.itemConfig.load(DynamicEconomy.itemsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String[] splitID = signID.split(" ");
		int x = Integer.parseInt(splitID[0]);
		int y = Integer.parseInt(splitID[1]);
		int z = Integer.parseInt(splitID[2]);
		
		String node = signID + ".WORLD";
		String worldName = conf.getString(node,"world");
	
		Location loc = new Location(Bukkit.getServer().getWorld(worldName),x,y,z);
		Block block = loc.getBlock();
		Sign sign = (Sign) block.getState();
		
		String data = "";
		
		String type = conf.getString(signID + ".TYPE");
		
		String change = "";
		
		if ((type.equalsIgnoreCase("ceiling")) && (changeCeiling != 0)) {
			data = format.format(DynamicEconomy.itemConfig.getDouble(item + ".ceiling",0));
			if (changeCeiling > 0) {
				change = "+" + format.format(changeCeiling);
			} else {
				change = format.format(changeCeiling) + "";
			}
		} else if ((type.equalsIgnoreCase("floor")) && (changeFloor != 0)) {
			data = String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".floor",0));
			if (changeFloor > 0) {
				change = "+" + format.format(changeFloor);
			} else {
				change = format.format(changeFloor) + "";
			}
		} else if ((type.equalsIgnoreCase("velocity")) && (changeVelocity != 0)) {
			data = String.valueOf(DynamicEconomy.itemConfig.getDouble(item + ".velocity",0));
			if (changeVelocity > 0) {
				change = "+" + format.format(changeVelocity);
			} else {
				change = format.format(changeVelocity) + "";
			}
		}
		
		if (!change.equals("")) {
			sign.setLine(2, Utility.getColor(DynamicEconomy.signInfoColor) + data);
			sign.setLine(3, Utility.getColor(DynamicEconomy.signInfoColor) + "(" + change + ")");
		}
		
		sign.update();
	}
	
	
}
