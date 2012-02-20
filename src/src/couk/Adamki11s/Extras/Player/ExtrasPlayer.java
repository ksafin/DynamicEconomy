package couk.Adamki11s.Extras.Player;

import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExtrasPlayer extends PlayerMethods {

	@Override
	public boolean isOnGround(Player p) {
		if(((CraftPlayer)p).getHandle().onGround){
			return true;
		} else {
			return false;
		}
	}
	
	public void mountEntity(Player p, Entity e){
        e.setPassenger((Entity)p);
	}

	@Override
	public boolean isUnderWater(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();		
		l.setY(l.getY() + 1);
		if(w.getBlockAt(l).getType() == Material.WATER || w.getBlockAt(l).getType() == Material.STATIONARY_WATER){
			return true;
		} else {
			return false;
		}
	}
	
	public void setBlockOnPlayerHead(Player p, Material m){
		p.getInventory().setHelmet(new ItemStack(m, 1));
	}

	@Override
	public void mountPlayer(Player mounter, Player target) {
		((Entity)target).setPassenger((Entity)mounter);
	}

	@Override
	public void setBlockOnPlayerHead(Player p, int id) {
		p.getInventory().setHelmet(new ItemStack(id, 1));
	}

	@Override
	public int getSecondsLived(Player p) {
		return Math.round((((CraftPlayer)p).getHandle().ticksLived) / 20);
	}

	@Override
	public int getMinutesLived(Player p) {
		return Math.round(((((CraftPlayer)p).getHandle().ticksLived) / 20)/60);
	}

	@Override
	public int getHoursLived(Player p) {
		return Math.round((((((CraftPlayer)p).getHandle().ticksLived) / 20)/60)/60);
	}

	@Override
	public UUID getUniqueUUID(Player p) {
		return ((Entity)p).getUniqueId();
	}

	@Override
	public void forceChat(Player p, String message) {
		p.chat(message);
	}

	@Override
	public int getDimension(Player p) {
		return ((CraftPlayer)p).getHandle().dimension;
	}

	@Override
	public void removeBlockOnPlayerHead(Player p) {
		p.getInventory().setHelmet(null);
	}

	@Override
	public Block getLookedAtBlock(Player p) {
		List<Block> blocks = p.getLineOfSight(null, 500);
		Block lookedAt = blocks.get(blocks.size() - 1);
		return lookedAt;
	}
	
	@Override
	public Location getLocationLooked(Player p) {
		List<Block> blocks = p.getLineOfSight(null, 500);
		Block lookedAt = blocks.get(blocks.size() - 1);
		return lookedAt.getLocation();
	}



}
