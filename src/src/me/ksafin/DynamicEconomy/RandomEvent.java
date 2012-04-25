package me.ksafin.DynamicEconomy;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import couk.Adamki11s.Extras.Colour.ExtrasColour;

public class RandomEvent implements Runnable {
	
	ExtrasColour color = new ExtrasColour();
	DecimalFormat format = new DecimalFormat("#.##");

	public void run() {
		double random = Math.random();
		if (random <= DynamicEconomy.randomEventChance) {
			FileConfiguration config = DynamicEconomy.randomEventConfig;
			ConfigurationSection events = config.getConfigurationSection("events");
			
			Set<String> eventsSet = events.getKeys(false);
			Object[] eventsObj = eventsSet.toArray();
			String[] eventsArr = new String[eventsObj.length];
			
			for (int i = 0; i < eventsObj.length; i++) {
				eventsArr[i] = eventsObj[i].toString();
			}
			
			int numEvents = eventsArr.length;
			
			random = Math.random() * numEvents;
			int index = (int)Math.round(random);
			index--;
			
			boolean outofbounds = false;
			String event ="";
			
			do {
				try {
					event = eventsArr[index];
				} catch (Exception e) {
					outofbounds = true;
					random = Math.random() * numEvents;
					index = (int)Math.round(random);
					index--;
				}
			} while (outofbounds);
			
			String node = "events." + event;
			ConfigurationSection eventSection = config.getConfigurationSection(node);
			
			String description = eventSection.getString("Description");
			String item = eventSection.getString("Item");
			String type = eventSection.getString("Type");
			String change = eventSection.getString("Change");
			boolean percent = eventSection.getBoolean("Percent");
			Double amount = eventSection.getDouble("Amount");
			
			item = Item.getTrueName(item);
			double price = DynamicEconomy.itemConfig.getDouble(item + ".price");
			int stock = DynamicEconomy.itemConfig.getInt(item + ".stock");
			double ceiling = DynamicEconomy.itemConfig.getDouble(item + ".ceiling");
			double floor = DynamicEconomy.itemConfig.getDouble(item + ".floor");
			
			double priceChange;
			double stockChange;
			
			if (type.equalsIgnoreCase("price")) {
				if (percent) {
					priceChange = price * amount;
				} else {
					priceChange = amount;
				}
				
				double newPrice = 0;
				
				if (change.equals("+")) {
					newPrice = price + priceChange;
				} else if (change.equals("-")) {
					newPrice = price - priceChange;
				}
				
				if (newPrice > ceiling) {
					newPrice = ceiling;
				}
				if (newPrice < floor) {
					newPrice = floor;
				}
				
			    double totalChange = newPrice - price;
			    String strPrice = format.format(newPrice);
			    String strChange = format.format(totalChange);
			    
			    if (totalChange > 0) {
					strChange = "+" + strPrice;
				}
				
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!(Utility.isQuiet(p))) {
						color.sendColouredMessage(p, DynamicEconomy.prefix +  description);
						color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Price of &f" + item + "&2 is &f" + DynamicEconomy.currencySymbol + strPrice + "&2 (" + strChange + ")");
					}
				} 
				
				DynamicEconomy.itemConfig.set(item + ".price", newPrice);
				
				dataSigns.checkForUpdates(item, 0, (newPrice - price));
			} else if (type.equalsIgnoreCase("stock")) {
				int stockArg = (int) Math.round(amount);
				int newStock = 0;
				
				if (change.equals("+")) {
					newStock = stock + stockArg;
				} else if (change.equals("-")) {
					newStock = stock - stockArg;
				}
				
				if (newStock < 0) {
					newStock = 0;
				}
				
				double totalChange = newStock - stock;
				
				String strChange = String.valueOf(totalChange);
				
				if (totalChange > 0) {
					strChange = "+" + totalChange;
				}
					
				
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!(Utility.isQuiet(p))) {
						color.sendColouredMessage(p, DynamicEconomy.prefix +  description);
						color.sendColouredMessage(p, DynamicEconomy.prefix +  "&2New Stock of &f" + item + "&2 is &f" + newStock + "&2 (" + strChange + ")");
					}
				} 
				
				DynamicEconomy.itemConfig.set(item + ".stock", newStock);
				
				dataSigns.checkForUpdates(item, (newStock - stock), 0);
			}
			
			
			
			try {
				DynamicEconomy.itemConfig.save(DynamicEconomy.itemsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
	//	try {
		//	this.wait(DynamicEconomy.randomEventInterval * 60 * 20);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}
	
}
