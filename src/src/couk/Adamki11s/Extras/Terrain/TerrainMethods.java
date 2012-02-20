package couk.Adamki11s.Extras.Terrain;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public abstract class TerrainMethods {
	
	/**
	 * Generates a cube from the given location.
	 * @param length The length of each side of the cube.
	 * @param startPoint The location from which the cuboid will be constructed.
	 * @param m The material to construct the cube out of.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateCube(int length, Location startPoint, Material m, Byte data);

	/**
	 * Generates a cube from the given location.
	 * @param length The length of each side of the cube.
	 * @param startPoint The location from which the cube will be constructed.
	 * @param id The item id to construct the cube out of.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateCube(int length, Location startPoint, int id, Byte data);
	
	/**
	 * Deletes a specific block from a cube selection.
	 * @param length The length of each size of the cube selection.
	 * @param startPoint The location from which the cube will be constructed.
	 * @param m The material to delete from the selection.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void deleteSpecificCube(int length, Location startPoint, Material m, Byte data);

	/**
	 * Deletes a specific block from a cube selection.
	 * @param length The length of each size of the cube selection.
	 * @param startPoint The location from which the cube will be constructed.
	 * @param id The item id to delete from the selection.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void deleteSpecificCube(int length, Location startPoint, int id, Byte data);
	
	/**
	 * Delete every block from a cube selection.
	 * @param length The length of each size of the cube selection.
	 * @param startPoint The location from which the cube will be constructed.
	 */
	public abstract void deleteAllCube(int length, Location startPoint);
	
	/**
	 * Generate a custom shape out of the specified material between the two specified points.
	 * @param point1 Point 1.
	 * @param point2 Point 1.
	 * @param m The material to construct the shape out of.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateFromPoints(Location point1, Location point2, Material m, Byte data);

	/**
	 * Generate a custom shape out of the specified material between the two specified points.
	 * @param point1 Point 1.
	 * @param point2 Point 1.
	 * @param id The item id to construct the shape out of.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateFromPoints(Location point1, Location point2, int id, Byte data);
	
	/**
	 * Generate a block at each location in the arraylist. (Allows for greater control/customisation.
	 * @param ArrayList The arraylist containing the locations of the blocks you want modified.
	 * @param m The material to construct the blocks from.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateFromArrayList(ArrayList<Location> ArrayList, Material m, Byte data);

	/**
	 * Generate a block at each location in the arraylist. (Allows for greater control/customisation.
	 * @param ArrayList The arraylist containing the locations of the blocks you want modified.
	 * @param id The item id to construct the blocks from.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void generateFromArrayList(ArrayList<Location> ArrayList, int id, Byte data);
	
	/**
	 * Deletes a specific block from a custom selection.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @param m The material to delete from the custom selection.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void deleteSpecificFromPoints(Location point1, Location point2, Material m, Byte data);

	/**
	 * Deletes a specific block from a custom selection.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 * @param id The item id to delete from the custom selection.
	 * @param data The block data. Pass null or '(Byte)0' to exclude.
	 */
	public abstract void deleteSpecificFromPoints(Location point1, Location point2, int id, Byte data);
	
	/**
	 * Delete every block in a custom selection.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 */
	public abstract void deleteAllFromPoints(Location point1, Location point2);
	
	/**
	 * Replace blocks in a custom selection.
	 * @param toReplace The material to replace.
	 * @param toReplaceData The data of the material to replace. Pass null or '(Byte)0' to exclude.
	 * @param replaceWith The material which will replace the specified one within the custom selection.
	 * @param replaceWithData The data of the material to replace. Pass null or '(Byte)0' to exclude.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 */
	public abstract void replaceBlocks(Material toReplace, Byte toReplaceData, Material replaceWith, Byte replaceWithData, Location point1, Location point2);
	
	/**
	 * Replace blocks in a custom selection.
	 * @param toReplace The item id to replace.
	 * @param toReplaceData The data of the item id to replace. Pass null or '(Byte)0' to exclude.
	 * @param replaceWith The item id which will replace the specified one within the custom selection.
	 * @param replaceWithData The data of the item id to replace. Pass null or '(Byte)0' to exclude.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 */
	public abstract void replaceBlocks(int toReplace, Byte toReplaceData, int replaceWith, Byte replaceWithData, Location point1, Location point2);
	
	/**
	 * Covers blocks in the custom selection with snow.
	 * @param point1 Point 1.
	 * @param point2 Point 2.
	 */
	public abstract void simulateSnow(Location point1, Location point2);
	
	/**
	 * Covers every block in the arraylist with snow.
	 * @param blockSet An arraylist containing all the blocks to be covered in snow.
	 */
	public abstract void simulateSnowFromArrayList(ArrayList<Block> blockSet);
}
