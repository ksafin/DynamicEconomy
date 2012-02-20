package couk.Adamki11s.Extras.Player;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class PlayerMethods {
	
	/**
	 * Check whether the player is on the ground.
	 * @param p The player to check.
	 * @return A boolean value indicating whether the player is on the ground or not.
	 */
	public abstract boolean isOnGround(Player p);
	
	/**
	 * Check whether the player is underwater.
	 * @param p The player to check.
	 * @return A boolean value indicating whether the player is underwater or not.
	 */
	public abstract boolean isUnderWater(Player p);
	
	/**
	 * Mounts the specified player onto the specified entity.
	 * @param p The player who will mount the entity.
	 * @param e The entity which the player will mount.
	 */
	public abstract void mountEntity(Player p, Entity e);
	
	/**
	 * Mounts the specified player onto another player.
	 * @param mounter The player who will mount the target player.
	 * @param playerToMount The player who will be mounted.
	 */
	public abstract void mountPlayer(Player mounter, Player playerToMount);
	
	/**
	 * Sets the block on the player's head.
	 * @param p The player to set.
	 * @param m The material to set the player's head to.
	 */
	public abstract void setBlockOnPlayerHead(Player p, Material m);
	
	/**
	 * Sets the block on the player's head.
	 * @param p The player to set.
	 * @param id The item id to set the player's head to.
	 */
	public abstract void setBlockOnPlayerHead(Player p, int id);
	
	/**
	 * Gets the time (In seconds) that the player has lived for.
	 * @param p The player to check.
	 * @return Integer value representing seconds.
	 */
	public abstract int getSecondsLived(Player p);
	
	/**
	 * Gets the time (In Minutes) that the player has lived for.
	 * @param p The player to check.
	 * @return Integer value representing seconds.
	 */
	public abstract int getMinutesLived(Player p);
	
	/**
	 * Gets the time (In Hours) that the player has lived for.
	 * @param p The player to check.
	 * @return Integer value representing hours.
	 */
	public abstract int getHoursLived(Player p);
	
	/**
	 * Gets the players unique Id.
	 * @param p The player to get.
	 * @return The players UUID.
	 */
	public abstract UUID getUniqueUUID(Player p);
	
	/**
	 * Force the player to send a chat message.
	 * @param p The player to force.
	 * @param message The message to send.
	 */
	public abstract void forceChat(Player p, String message);
	
	/**
	 * Get the dimension the player is in.
	 * @param p The player to check.
	 * @return The players dimension.
	 */
	public abstract int getDimension(Player p);	
	
	/**
	 * Remove anything on the player's head.
	 * @param p The player.
	 */
	public abstract void removeBlockOnPlayerHead(Player p);
	
	/**
	 * Get the block the player is looking at regardless of distance.
	 * @param p The player.
	 * @return The block the player is looking at.
	 */
	public abstract Block getLookedAtBlock(Player p);
	
	/**
	 * Get the location of the block the player is looking at.
	 * @param p The player.
	 * @return The location of the block the player is looking at.
	 */
	public abstract Location getLocationLooked(Player p);

}
