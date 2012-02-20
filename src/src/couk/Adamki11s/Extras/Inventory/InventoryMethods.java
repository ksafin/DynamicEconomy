package couk.Adamki11s.Extras.Inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryMethods {
	
	/**
	 * Add an item to the player's inventory.
	 * @param p The player's inventory to add the item to.
	 * @param m The Material to add.
	 * @param quantity The quantity to add.
	 */
	public abstract void addToInventory(Player p, Material m, int quantity);
	
	/**
	 * Add an item to the player's inventory.
	 * @param p The player's inventory to add the item to.
	 * @param id The id of the item to add.
	 * @param quantity The quantity to add.
	 */
	public abstract void addToInventory(Player p, int id, int quantity);
	
	/**
	 * Remove an item from the player's inventory.
	 * @param p The player's inventory to remove from.
	 * @param m The material to remove.
	 * @param quantity The quantity to remove.
	 */
	public abstract void removeFromInventory(Player p, Material m, int quantity);
	
	/**
	 * Remove an item from the player's inventory.
	 * @param p The player's inventory to remove from.
	 * @param id The id of the item to remove.
	 * @param quantity The quantity to remove.
	 */
	public abstract void removeFromInventory(Player p, int id, int quantity);
	
	/**
	 * Get the number of empty slots in a player's inventory.
	 * @param p The player's inventory to check.
	 * @return emptySlots The number of free inventory spaces the player has.
	 */
	public abstract int getEmptySlots(Player p);
	
	/**
	 * Check whether the player's inventory contains a certain item.
	 * @param p The player's inventory to search.
	 * @param m The material to check.
	 * @return A boolean value indicating whether the player's inventory contains the specified item.
	 */
	public abstract boolean doesInventoryContain(Player p, Material m);
	
	/**
	 * Check whether the player's inventory contains a certain item.
	 * @param p The player's inventory to search.
	 * @param id The item id to check.
	 * @return A boolean value indicating whether the player's inventory contains the specified item.
	 */
	public abstract boolean doesInventoryContain(Player p, int id);
	
	/**
	 * Get the amount of itemstack's with the specified item in the players inventory.
	 * @param p The player's inventory to search.
	 * @param m The material to check.
	 * @return An integer holding the amount of stacks of the specified item.
	 */
	public abstract int getStackCount(Player p, Material m);
	
	/**
	 * Get the amount of itemstack's with the specified item in the players inventory.
	 * @param p The player's inventory to search.
	 * @param id The item id to check.
	 * @return An integer holding the amount of stacks of the specified item.
	 */
	public abstract int getStackCount(Player p, int id);
	
	/**
	 * Get the number of blocks of the specified item within the player's inventory.
	 * @param p The player's inventory to search. 
	 * @param m The material to check.
	 * @return An integer holding the number of blocks of the specified item.
	 */
	public abstract int getAmountOf(Player p, Material m);
	
	/**
	 * Get the number of blocks of the specified item within the player's inventory.
	 * @param p The player's inventory to search. 
	 * @param id The item id to check.
	 * @return An integer holding the number of blocks of the specified item.
	 */
	public abstract int getAmountOf(Player p, int id);
	
	/**
	 * Organises the players inventory. Groups blocks together into stacks and reformats.
	 * @param p The player's inventory to format.
	 */
	public abstract void sortInventory(Player p);
	
	/**
	 * Remove all of the specified item from the player's inventory.
	 * @param p The player's inventory to search.
	 * @param id The item id to remove.
	 */
	public abstract void removeAllFromInventory(Player p, int id);
	
	/**
	 * Remove all of the specified item from the player's inventory.
	 * @param p The player's inventory to search.
	 * @param m The Material to remove.
	 */
	public abstract void removeAllFromInventory(Player p, Material m);
	
	/**
	 * Wipe the players inventory clean. This will delete their entire inventory.
	 * @param p The player's inventory to wipe.
	 */
	public abstract void wipeInventory(Player p);
	
	/**
	 * Store the contents of the player's inventory. (Saved in RAM, Volatile). Data will not be retained after a server restart/crash/shutdown.
	 * @param p The player's inventory to save.
	 * @return
	 */
	public abstract boolean storeInventory(Player p);
	
	/**
	 * Retrieve the contents of the player's inventory from an earlier 'storeInventory'.
	 * @param p The player's saved inventory to retrieve.
	 * @return An ItemStack[] array containing the contents of the saved inventory.
	 */
	public abstract ItemStack[] retrieveInventory(Player p);
	
	public abstract void updateInventory(Player p);
}
