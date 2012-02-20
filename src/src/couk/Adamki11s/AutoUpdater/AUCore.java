/**
 * LICENSING
 * 
 * This software is copyright by Adamki11s <adam@adamki11s.co.uk> and is
 * distributed under a dual license:
 * 
 * Non-Commercial Use:
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Commercial Use:
 *    Please contact adam@adamki11s.co.uk
 */

package couk.Adamki11s.AutoUpdater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import couk.Adamki11s.Extras.Colour.ExtrasColour;


public class AUCore{

	private double versionNO, subVersionNO;
	private String reason, source, prefix, urgency;
	private Logger log;
	private ExtrasColour color = new ExtrasColour();
	
	private URL uri;
	
	/**
	 * Constructor for variables needed for the AutoUpdaterCore.
	 * @param website
	 * @param l
	 * @param pref
	 * @param plugin
	 * @param serv
	 */
	public AUCore(String website, Logger l, String pref){
		
		log = l;
		prefix = pref;
		
		try{
			uri = new URL(website);
		} catch(MalformedURLException ex){
			ex.printStackTrace();
			log.info("[AU]" + prefix + " Malformed URL Exception. Make sure the URL is in the form 'http://www.website.domain'");
		}	
		
	}
	
	
	/**
	 * Check the current version against te latest one.
	 * @param currentVersion - Double
	 * @param currentSubVersion - Double
	 * @param pluginname - String
	 * @return
	 */
	public boolean checkVersion(double currentVersion, double currentSubVersion, String pluginname){
		source = FetchSource.fetchSource(uri, log, prefix);
		formatSource(source);
		
		String subVers;
		if(currentSubVersion == 0){
			subVers = "";
		} else {
			subVers = Double.toString(currentSubVersion);
		}
		
		if(versionNO != currentVersion){
			log.info("[AU]" + prefix + " You are not running the latest version of " + pluginname + "!");
			log.info("[AU]" + prefix + " Running version : " + currentVersion + "_" + subVers + ". Latest version : " + versionNO + "_" + subVersionNO + ".");
			log.info("[AU]" + prefix + " Update urgency for version " + versionNO + "_" + subVersionNO + " : " + urgency + ".");
			log.info("[AU]" + prefix + " Update reason : " + reason);
			return false;
		} else if(subVersionNO != currentSubVersion) {
			log.info("[AU]" + prefix + " You are not running the latest sub version of " + pluginname + "!");
			log.info("[AU]" + prefix + " Running version : " + currentVersion + "_" + subVers + ". Latest version : " + versionNO + "_" + subVersionNO + ".");
			log.info("[AU]" + prefix + " Update urgency for version " + versionNO + "_" + subVersionNO + " : " + urgency + ".");
			log.info("[AU]" + prefix + " Update reason : " + reason);
			return false;
		} else if(versionNO == currentVersion && subVersionNO == currentSubVersion){
			//log.info("[AU]" + prefix + " You are running the latest version of " + pluginname + ".");
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Force a download of the newest version
	 * 
	 * @param downloadLink - String
	 * @param pluginName - String
	 */
	public void forceDownload(String downloadLink, String pluginName, Player player){
		//new File("plugins/AutoUpdater").mkdir();
		if(new File("plugins/" + pluginName + ".jar").exists()){
			new File("plugins/" + pluginName + ".jar").delete();
		}
		log.info("[AU]" + prefix + " Downloading newest version of " + pluginName + "..."); 
		try{
			BufferedInputStream in = new java.io.BufferedInputStream(new 
					 
			URL(downloadLink).openStream());
			FileOutputStream fos = new java.io.FileOutputStream("plugins/" + pluginName + ".jar");
			BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
			byte[] data = new byte[1024];
			int x=0;
			
			while((x=in.read(data,0,1024))>=0){
				bout.write(data,0,x);
			}
			
			bout.close();
			in.close();
		} catch (Exception ex){
			ex.printStackTrace();
			log.info("[AU]" + prefix + " Error whilst downloading update!"); 
			color.sendColouredMessage(player, "&2Error updating!");
		}
		log.info("[AU]" + prefix + " Download completed successfully!"); 
		color.sendColouredMessage(player, "&2DynamicEconomy succesfully updated!");
		log.info("[AU]" + prefix + " Newest version of " + pluginName + " has been downloaded to : '/plugins/" + pluginName + ".jar"); 
		
	}
	
	private void formatSource(String source){
		String parts[] = source.split("\\@");
		
		try{
			versionNO = Double.parseDouble(parts[1]);
			subVersionNO = Double.parseDouble(parts[2]);
		} catch (NumberFormatException ex){
			ex.printStackTrace();
			log.info("[AU]" + prefix + " Error while parsing version number!");
		}
		
		urgency = parts[3];
			
		reason = parts[4];
	}
	
	
}
