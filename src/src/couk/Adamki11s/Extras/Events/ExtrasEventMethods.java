package couk.Adamki11s.Extras.Events;

import org.bukkit.event.player.PlayerMoveEvent;

public abstract class ExtrasEventMethods {
	
	/**
	 * Check whether the player rotated.
	 * @param evt The PlayerMoveEvent.
	 * @return A boolean value indicating whether the player has rotated or not.
	 */
	public abstract boolean didRotate(PlayerMoveEvent evt);
	
	/**
	 * Check whether the player moved.
	 * @param evt The PlayerMoveEvent.
	 * @return A boolean value indicating whether the player has moved or not.
	 */
	public abstract boolean didMove(PlayerMoveEvent evt);
	
	/**
	 * Check whether the player has moved to a different block.
	 * @param evt The PlayerMoveEvent.
	 * @return A boolean value indicating whether the player has moved from one block to another.
	 */
	public abstract boolean didChangeBlock(PlayerMoveEvent evt);

}
