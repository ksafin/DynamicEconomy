package couk.Adamki11s.Extras.Colour;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public abstract class ColourMethods {
	
	/** 
	 * Send a coloured message to the player easily.
	 * @param p Player whom the message will be send to.
	 * @param Your message
	 */
	public abstract void sendColouredMessage(Player p, String message);
	
	/**
	 * Send a multi-coloured message to the player easily.
	 * @param p Player whom the message will be send to.
	 * @param Your message
	 */
	public abstract void sendMultiColouredMessage(Player p, String message);
	
	/**
	 * Broadcast a coloured message easily.
	 * @param p Player whom the message will be send to.
	 * @param Your message
	 */
	public abstract void broadcastColouredMessage(Server s, String message);
	
	/**
	 * Broadcast a multi-coloured message easily.
	 * @param p Player whom the message will be send to.
	 * @param Your message
	 */
	public abstract void broadcastMultiColouredMessage(Server s, String message);

}
