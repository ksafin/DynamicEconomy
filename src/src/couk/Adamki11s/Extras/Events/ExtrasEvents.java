package couk.Adamki11s.Extras.Events;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class ExtrasEvents extends ExtrasEventMethods {

	@Override 
	public boolean didRotate(PlayerMoveEvent evt) {
		// TODO Auto-generated method stub
		Location to = evt.getTo(), from = evt.getFrom();
		float toYaw = to.getYaw(), fromYaw = from.getYaw(),
		     toPitch = to.getPitch(), fromPitch = from.getPitch();
		
		if(toYaw != fromYaw || toPitch != fromPitch){
			return true;
		}
		return false;
	}

	@Override
	public boolean didMove(PlayerMoveEvent evt) {
		if(((evt.getTo().getX() != evt.getFrom().getX()) || (evt.getTo().getY() != evt.getFrom().getY()) || (evt.getTo().getZ() != evt.getFrom().getZ()))){
				return true;
		}
		return false;
	}
	
	private double x = 0, z = 0, offsetx = 0, offsetz = 0, oldx = 0, oldz = 0;
	
	@Override
	public boolean didChangeBlock(PlayerMoveEvent evt) {
		if(didMove(evt)){
			org.bukkit.entity.Player player = evt.getPlayer();
			org.bukkit.Location location = player.getLocation();

			if (!(x == oldx)) {
				oldx = x;
			}
			if (!(z == oldz)) {
				oldz = z;
			}

			x = location.getX();
			z = location.getZ();
			// yaw = location.getDirection(); //left and right
			// pitch = location.getPitch(); //Up and down

			if (x - oldx > 0) {
				offsetx += (x - oldx);
				if (offsetx >= 1) {
					offsetx = 0;
					return true;
				}
			}
			if (x - oldx < 0) {
				offsetx += (oldx - x);
				if (offsetx >= 1) {
					offsetx = 0;
					return true;
				}
			}

			if (z - oldz > 0) {
				offsetz += (z - oldz);
				if (offsetz >= 1) {
					offsetz = 0;
					return true;
				}
			}

			if (z - oldz < 0) {
				offsetz += (oldz - z);
				if (offsetz >= 1) {
					offsetz = 0;
					return true;
				}
			}
			return false;
		}
		return false;
	}

}
