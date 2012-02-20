package couk.Adamki11s.Extras.Random;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class RandomMethods {

	/**
	 * Returns a random integer within the specified bounds.
	 * @param upperBound The max value.
	 * @param lowerBound The minimum value.
	 * @return Returns a random integer within the specified bounds.
	 */
	public abstract int getRandomInt(int upperBound, int lowerBound);
	
	/**
	 * Returns a random double within the specified bounds.
	 * @param upperBound The max value.
	 * @param lowerBound The minimum value.
	 * @return Returns a random double within the specified bounds.
	 */
	public abstract double getRandomDouble(double upperBound, double lowerBound);
	
	/**
	 * Returns a random float within the specified bounds.
	 * @param upperBound The max value.
	 * @param lowerBound The minimum value.
	 * @return Returns a random float within the specified bounds.
	 */
	public abstract float getRandomFloat(float upperBound, float lowerBound);
	
	/**
	 * Returns a random CreatureType.
	 * @return Returns a random CreatureType.
	 */
	public abstract CreatureType getRandomCreature();
	
	/**
	 * Returns a random living entity from the specified world.
	 * @param w World.
	 * @return Returns a random living entity from the specified world. Could be a player, monster or animal.
	 */
	public abstract Entity getRandomLivingEntityFromWorld(World w);
	
	/**
	 * Returns a random player on the server.
	 * @param s Server.
	 * @return Returns a random player on the server.
	 */
	public abstract Player getRandomPlayer(Server s);
	
	/**
	 * Returns a random block ID as an integer.
	 * @return Returns a random block ID as an integer.
	 */
	public abstract int getRandomBlockId();
}
