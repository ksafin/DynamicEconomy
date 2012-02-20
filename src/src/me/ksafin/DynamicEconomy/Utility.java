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

public class Utility {
	
	public File log;
	public static Logger logger = Logger.getLogger("Minecraft");
	public static FileWriter out;
	public static BufferedWriter bf;
	private static Calendar calendar = Calendar.getInstance();
	private DynamicEconomy plugin;
	
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
	
	
}
