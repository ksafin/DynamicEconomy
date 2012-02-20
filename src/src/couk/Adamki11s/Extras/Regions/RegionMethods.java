package couk.Adamki11s.Extras.Regions;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class RegionMethods {
	
	/**
	 * Check whether the specified player is within the two points specified.
	 * @param p The player to check.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @return A boolean value indicating whether the player is within the specified region or not.
	 */
	public abstract boolean isInsideCuboid(Player p, Location point1, Location point2);
	
	/**
	 * Check whether the specified player is within the radius specified.
	 * @param p The player to check.
	 * @param centre The epicentre.
	 * @param radius The radius size in blocks.
	 * @return A boolean value indicating whether the player is within the specified region or not.
	 */
	public abstract boolean isInsideRadius(Player p, Location centre, int radius);
	
	/**
	 * Returns a list of the players within the two points specified.
	 * @param s The server.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @return A List containing the players inside the specified region.
	 */
	public abstract List<Player> getPlayersInCuboid(Server s, Location point1, Location point2);
	
	/**
	 * Returns a list of the players within the radius specified.
	 * @param s The server.
	 * @param centre The epicentre.
	 * @param radius The radius size in blocks.
	 * @return A List containing the players inside the specified region.
	 */
	public abstract List<Player> getPlayersInRadius(Server s, Location centre, int radius);
	
	/**
	 * Returns a list of entities within the specified points.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @return A List containing the entities within the specified region.
	 */
	public abstract List<Entity> getEntitiesInCuboid(Location point1, Location point2);
	
	/**
	 * Returns a list of entities within radius specified.
	 * @param centre The epicentre.
	 * @param radius The radius size in blocks.
	 * @return A List containing the entities within the specified radius.
	 */
	public abstract List<Entity> getEntitiesInRadius(Location centre, int radius);
	
	/**
	 * Gets the block count within the specified region(Includes air blocks).
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @return Returns an integer containing the number of blocks(Including air blocks) within the specified region.
	 */
	public abstract int getBlockCountInCuboid(Location point1, Location point2);
	
	/**
	 * Gets the block count within the specified radius(Includes air blocks).
	 * @param centre The epicentre.
	 * @param radius The radius size in blocks.
	 * @return Returns an integer containing the number of blocks(Including air blocks) within the specified region.
	 */
	public abstract int getBlockCountInRadius(Location centre, int radius);
	
	/**
	 * Returns an arraylist containing all of the blocks within a region.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @return An arraylist containing each block in the specified region.
	 */
	public abstract ArrayList<Block> getBlocksInRegion(Location point1, Location point2);

}
