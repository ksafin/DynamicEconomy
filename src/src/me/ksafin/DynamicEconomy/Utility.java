package me.ksafin.DynamicEconomy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import couk.Adamki11s.Extras.Colour.ExtrasColour;

public class Utility {
	
	public File log;
	public static Logger logger = Logger.getLogger("Minecraft");
	public static FileWriter out;
	public static BufferedWriter bf;
	private static Calendar calendar = Calendar.getInstance();
	private DynamicEconomy plugin;
	public static ExtrasColour color = new ExtrasColour();
	
	public Utility(File logFile, DynamicEconomy plug) {
		log = logFile;
		plugin = plug;
		checkLog();
		try {
		out = new FileWriter(log,true);
		bf = new BufferedWriter(out);
		} catch (Exception e) {
			logger.info("[DynamicEconomy] Error creating FileWriter for log.txt");
			e.printStackTrace();
		}
	}
	
	public static int[] decodeCoordinates(String stringCoords) {
		String[] split = stringCoords.split(" ");
		int[] intCoords = new int[3];
		for (int x = 0; x < 3; x++) {
			intCoords[x] = Integer.parseInt(split[x]);
		}
		return intCoords;
	}
	
	public static String encodeCoordinates(int[] coordsArray) {
		String coords = "";
		for (int x =0; x < 3; x++) {
			if (x < 2) {
			   coords += coordsArray[x] + " ";
			} else {
			   coords += coordsArray[x];
			}
		}
		return coords;
	}

	public void checkLog() {
		
		File file = new File(plugin.getDataFolder().getPath());
		if(!file.exists()) file.mkdir();
		
		File logF = new File(file.getPath() + File.separator + "log.txt");
		
		try
        {
            if(!logF.exists())
            {
                FileOutputStream fos = new FileOutputStream(logF);
                fos.flush();
                fos.close();
            }
        }
        catch(IOException ioe)
        {
            logger.info("[DynamicEconomy] Exception creating log.txt");
        }
		
	/*	if (log.exists() == false) {
			try {
			//logger.info(log.getAbsolutePath());
			//log.createNewFile();
			FileOutputStream fos = new FileOutputStream(log);
	        fos.flush();
	        fos.close();
			} catch (Exception e) {
				logger.info("[DynamicEconomy] Error creating log file.");
				e.printStackTrace();
			}
		} else {
			logger.info("[DynamicEconomy] Log file detected.");
		}*/
	}
	
	public static void writeToLog(String message) {
		int year = calendar.YEAR;
		int month = calendar.MONTH;
		month++;
		int day = calendar.DAY_OF_MONTH;
		
		String date = year + "-" + month + "-" + day;
		
		Date time = calendar.getTime();
		int hours = time.getHours();
		int minutes = time.getMinutes();
		int seconds = time.getSeconds();
		
		String timeS = hours + ":" + minutes + ":" + seconds;
		
		Date dateS = new Date();
		
		String timestamp = date + " " + timeS;
		Timestamp ts = new Timestamp(dateS.getTime());
		
		if (DynamicEconomy.logwriting) {
		try {
			bf.write("\n + [" + ts + "] " + message);
			bf.flush();
		} catch (Exception e) {
			logger.info("[DynamicEconomy] Exception writing to log.txt");
			e.printStackTrace();
		}
		} 
		
	}
	
	public static void wrongArgsMessage(Player player) {
		color.sendColouredMessage(player, "&2You have put entered &finvalid &2 arguments for this command. &fTry Again.");
	}
	
	public static boolean isQuiet(Player player) {
		String name = player.getName();
		
		FileConfiguration conf = DynamicEconomy.usersConfig;
		
		String node = name + ".QUIET";
		
		Boolean isQuiet = conf.getBoolean(node,true);
		
		return isQuiet;
	}
	
	public static void makeQuiet(Player player) {
		if (isQuiet(player)) {
			DynamicEconomy.usersConfig.set((player.getName() + ".QUIET"),false);
		} else {
			DynamicEconomy.usersConfig.set((player.getName() + ".QUIET"),true);
		}
		
		color.sendColouredMessage(player, "&2Your Quiet mode has been set to: &f" + isQuiet(player));
		
		try {
			DynamicEconomy.usersConfig.save(DynamicEconomy.usersFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getColor(String color) {
		if (color.equals("&0")) {
			return ChatColor.BLACK.toString();
		} else if (color.equals("&1")) {
			return ChatColor.DARK_BLUE.toString();
		} else if (color.equals("&2")) {
			return ChatColor.DARK_GREEN.toString();
		} else if (color.equals("&3")) {
			return ChatColor.DARK_AQUA.toString();
		} else if (color.equals("&4")) {
			return ChatColor.DARK_RED.toString();
		} else if (color.equals("&5")) {
			return ChatColor.DARK_PURPLE.toString();
		} else if (color.equals("&6")) {
			return ChatColor.GOLD.toString();
		} else if (color.equals("&7")) {
			return ChatColor.GRAY.toString();
		} else if (color.equals("&8")) {
			return ChatColor.DARK_GRAY.toString();
		} else if (color.equals("&9")) {
			return ChatColor.BLUE.toString();
		} else if (color.equals("&a")) {
			return ChatColor.GREEN.toString();
		} else if (color.equals("&b")) {
			return ChatColor.AQUA.toString();
		} else if (color.equals("&c")) {
			return ChatColor.RED.toString();
		} else if (color.equals("&d")) {
			return ChatColor.LIGHT_PURPLE.toString();
		} else if (color.equals("&e")) {
			return ChatColor.YELLOW.toString();
		} else if (color.equals("&f")) {
			return ChatColor.WHITE.toString();
		} else if (color.equals("&A")) {
			return ChatColor.GREEN.toString();
		} else if (color.equals("&B")) {
			return ChatColor.AQUA.toString();
		} else if (color.equals("&C")) {
			return ChatColor.RED.toString();
		} else if (color.equals("&D")) {
			return ChatColor.LIGHT_PURPLE.toString();
		} else if (color.equals("&E")) {
			return ChatColor.YELLOW.toString();
		} else if (color.equals("&F")) {
			return ChatColor.WHITE.toString();
		} else if (color.equals("&black")) {
			return ChatColor.BLACK.toString();
		} else if (color.equals("&blue")) {
			return ChatColor.BLUE.toString();
		} else if (color.equals("&darkblue")) {
			return ChatColor.DARK_BLUE.toString();
		} else if (color.equals("&darkaqua")) {
			return ChatColor.DARK_AQUA.toString();
		} else if (color.equals("&darkred")) {
			return ChatColor.DARK_RED.toString();
		} else if (color.equals("&darkpurple")) {
			return ChatColor.DARK_PURPLE.toString();
		} else if (color.equals("&gray")) {
			return ChatColor.GRAY.toString();
		} else if (color.equals("&darkgray")) {
			return ChatColor.DARK_GRAY.toString();
		} else if (color.equals("&grey")) {
			return ChatColor.GRAY.toString();
		} else if (color.equals("&darkgrey")) {
			return ChatColor.DARK_GRAY.toString();
		} else if (color.equals("&lightpurple")) {
			return ChatColor.LIGHT_PURPLE.toString();
		} else if (color.equals("&white")) {
			return ChatColor.WHITE.toString();
		} else if (color.equals("&red")) {
			return ChatColor.RED.toString();
		} else if (color.equals("&yellow")) {
			return ChatColor.YELLOW.toString();
		} else if (color.equals("&green")) {
			return ChatColor.GREEN.toString();
		} else if (color.equals("&aqua")) {
			return ChatColor.AQUA.toString();
		} else if (color.equals("&pink")) {
			return ChatColor.LIGHT_PURPLE.toString();
		} else if (color.equals("&purple")) {
			return ChatColor.DARK_PURPLE.toString();
		} else if (color.equals("&gold")) {
			return ChatColor.GOLD.toString();
		} else {
			return "";
		}
		
		
	}
	
	
}
