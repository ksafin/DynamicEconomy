package couk.Adamki11s.Extras.Colour;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import couk.Adamki11s.Extras.Extras.Extras;

public class ExtrasColour extends ColourMethods {

	@Override  
	public void sendColouredMessage(Player p, String message) {
		// TODO Auto-generated method stub
		try{
			p.sendMessage(replaceMessage(message));
		} catch (Exception ex){
			System.out.println("[Extras] Error sending coloured message! Caused by plugin : " + Extras.pluginName);
			ex.printStackTrace();
		}
	}

	@Override
	public void sendMultiColouredMessage(Player p, String message) {
		// TODO Auto-generated method stub
		try{
			p.sendMessage(colourMessage(message));
		} catch (Exception ex){
			System.out.println("[Extras] Error broadcasting multi-coloured message! Caused by plugin : " + Extras.pluginName);
			ex.printStackTrace();
		}
	}

	@Override
	public void broadcastColouredMessage(Server s, String message) {
		// TODO Auto-generated method stub
		try{
			s.broadcastMessage(replaceMessage(message));
		} catch (Exception ex){
			System.out.println("[Extras] Error broadcasting coloured message! Caused by plugin : " + Extras.pluginName);
			ex.printStackTrace();
		}
	}

	@Override
	public void broadcastMultiColouredMessage(Server s, String message) {
		// TODO Auto-generated method stub
		try{
			s.broadcastMessage(colourMessage(message));
		} catch (Exception ex){
			System.out.println("[Extras] Error broadcasting multi-coloured message! Caused by plugin : " + Extras.pluginName);
			ex.printStackTrace();
		}
	}
	
	private String colourMessage(String msg){
		String construct = "";
		Random rnd = new Random();
		int num = 0;
		
		for(int length = 1; length <= msg.length(); length++){
			num = rnd.nextInt(15);
			construct += getColour(num) + msg.charAt(length - 1);
		}
		
		return construct;
		
	}
	
	private String getColour(int n){
		switch(n){
			case 0: return ChatColor.BLACK.toString();
			case 1: return ChatColor.DARK_BLUE.toString();
			case 2: return ChatColor.DARK_GREEN.toString();
			case 3: return ChatColor.DARK_AQUA.toString();
			case 4: return ChatColor.DARK_RED.toString();
			case 5: return ChatColor.DARK_PURPLE.toString();
			case 6: return ChatColor.GOLD.toString();
			case 7: return ChatColor.GRAY.toString();
			case 8: return ChatColor.DARK_GRAY.toString();
			case 9: return ChatColor.BLUE.toString();
			case 10: return ChatColor.GREEN.toString();
			case 11: return ChatColor.AQUA.toString();
			case 12: return ChatColor.RED.toString();
			case 13: return ChatColor.LIGHT_PURPLE.toString();
			case 14: return ChatColor.YELLOW.toString();
			case 15: return ChatColor.WHITE.toString();
			default: return ChatColor.WHITE.toString();
		}	
	}
	
	private String replaceMessage(String msg){
		msg = msg.replaceAll("&0", ChatColor.BLACK.toString());
		msg = msg.replaceAll("&1", ChatColor.DARK_BLUE.toString());
		msg = msg.replaceAll("&2", ChatColor.DARK_GREEN.toString());
		msg = msg.replaceAll("&3", ChatColor.DARK_AQUA.toString());
		msg = msg.replaceAll("&4", ChatColor.DARK_RED.toString());
		msg = msg.replaceAll("&5", ChatColor.DARK_PURPLE.toString());
		msg = msg.replaceAll("&6", ChatColor.GOLD.toString());
		msg = msg.replaceAll("&7", ChatColor.GRAY.toString());
		msg = msg.replaceAll("&8", ChatColor.DARK_GRAY.toString());
		msg = msg.replaceAll("&9", ChatColor.BLUE.toString());
		msg = msg.replaceAll("&a", ChatColor.GREEN.toString());
		msg = msg.replaceAll("&b", ChatColor.AQUA.toString());
		msg = msg.replaceAll("&c", ChatColor.RED.toString());
		msg = msg.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString());
		msg = msg.replaceAll("&e", ChatColor.YELLOW.toString());
		msg = msg.replaceAll("&f", ChatColor.WHITE.toString());
		msg = msg.replaceAll("&A", ChatColor.GREEN.toString());
		msg = msg.replaceAll("&B", ChatColor.AQUA.toString());
		msg = msg.replaceAll("&C", ChatColor.RED.toString());
		msg = msg.replaceAll("&D", ChatColor.LIGHT_PURPLE.toString());
		msg = msg.replaceAll("&E", ChatColor.YELLOW.toString());
		msg = msg.replaceAll("&F", ChatColor.WHITE.toString());
		msg = msg.replaceAll("&black", ChatColor.BLACK.toString());
		msg = msg.replaceAll("&blue", ChatColor.BLUE.toString());
		msg = msg.replaceAll("&darkblue", ChatColor.DARK_BLUE.toString());
		msg = msg.replaceAll("&darkaqua", ChatColor.DARK_AQUA.toString());
		msg = msg.replaceAll("&darkred", ChatColor.DARK_RED.toString());
		msg = msg.replaceAll("&darkpurple", ChatColor.DARK_PURPLE.toString());
		msg = msg.replaceAll("&gray", ChatColor.GRAY.toString());
		msg = msg.replaceAll("&darkgray", ChatColor.DARK_GRAY.toString());
		msg = msg.replaceAll("&grey", ChatColor.GRAY.toString());
		msg = msg.replaceAll("&darkgrey", ChatColor.DARK_GRAY.toString());
		msg = msg.replaceAll("&lightpurple", ChatColor.LIGHT_PURPLE.toString());
		msg = msg.replaceAll("&white", ChatColor.WHITE.toString());
		msg = msg.replaceAll("&red", ChatColor.RED.toString());
		msg = msg.replaceAll("&yellow", ChatColor.YELLOW.toString());
		msg = msg.replaceAll("&green", ChatColor.GREEN.toString());
		msg = msg.replaceAll("&aqua", ChatColor.AQUA.toString());
		msg = msg.replaceAll("&pink", ChatColor.LIGHT_PURPLE.toString());
		msg = msg.replaceAll("&purple", ChatColor.DARK_PURPLE.toString());
		msg = msg.replaceAll("&gold", ChatColor.GOLD.toString());
		return msg;
	}

}
