package couk.Adamki11s.Extras.Regions;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ExtrasRegions extends RegionMethods {

	@Override
	public boolean isInsideCuboid(Player p, Location point1, Location point2) {
		// TODO Auto-generated method stub
		double x1 = point1.getX(), x2 = point2.getX(),
		       y1 = point1.getY(), y2 = point2.getY(),
		       z1 = point1.getZ(), z2 = point2.getZ(),
		       px = p.getLocation().getX(),
		       py = p.getLocation().getY(),
		       pz = p.getLocation().getZ();
		
		if( (((py <= y1) && 
			(py >= y2)) || 
			((py >= y1) && 
			(py <= y2))) && 
			(((pz <= z1) && 
			(pz >= z2)) || 
			((pz >= z1) && 
			(pz <= z2)))  &&  
			(((px <= x1) && 
			(px >= x2)) || 
			((px >= x1) && 
			(px <= x2))) && 
			(((px <= x1) && 
			(px >= x2)) || 
			((px >= x1) && 
			(px <= x2)))   ){
			return true;
		}			
		
		return false;
	}

	@Override
	public boolean isInsideRadius(Player p, Location centre, int radius) {
		// TODO Auto-generated method stub
		Location point1 = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
		Location point2 = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
		
		double x1 = point1.getX(), x2 = point2.getX(),
	       y1 = point1.getY(), y2 = point2.getY(),
	       z1 = point1.getZ(), z2 = point2.getZ(),
	       px = p.getLocation().getX(),
	       py = p.getLocation().getY(),
	       pz = p.getLocation().getZ();
		
		if( (((py <= y1) && 
				(py >= y2)) || 
				((py >= y1) && 
				(py <= y2))) && 
				(((pz <= z1) && 
				(pz >= z2)) || 
				((pz >= z1) && 
				(pz <= z2)))  &&  
				(((px <= x1) && 
				(px >= x2)) || 
				((px >= x1) && 
				(px <= x2))) && 
				(((px <= x1) && 
				(px >= x2)) || 
				((px >= x1) && 
				(px <= x2)))   ){
				return true;
			}		
		
		return false;
	}

	@Override
	public List<Player> getPlayersInCuboid(Server s, Location point1,
			Location point2) {
		
		List<Player> playerSet = new ArrayList<Player>();
		
		for(Player p : s.getOnlinePlayers()){
		
				double x1 = point1.getX(), x2 = point2.getX(),
			       y1 = point1.getY(), y2 = point2.getY(),
			       z1 = point1.getZ(), z2 = point2.getZ(),
			       px = p.getLocation().getX(),
			       py = p.getLocation().getY(),
			       pz = p.getLocation().getZ();
			
				if( (((py <= y1) && 
					(py >= y2)) || 
					((py >= y1) && 
					(py <= y2))) && 
					(((pz <= z1) && 
					(pz >= z2)) || 
					((pz >= z1) && 
					(pz <= z2)))  &&  
					(((px <= x1) && 
					(px >= x2)) || 
					((px >= x1) && 
					(px <= x2))) && 
					(((px <= x1) && 
					(px >= x2)) || 
					((px >= x1) && 
					(px <= x2)))   ){
					playerSet.add(p);
				}	
		}
		
		return playerSet;
	}

	@Override
	public List<Player> getPlayersInRadius(Server s, Location centre, int radius) {
		// TODO Auto-generated method stub
		
		Location point1 = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
		Location point2 = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
		
		List<Player> playerSet = new ArrayList<Player>();
		
		for(Player p : s.getOnlinePlayers()){
		
				double x1 = point1.getX(), x2 = point2.getX(),
			       y1 = point1.getY(), y2 = point2.getY(),
			       z1 = point1.getZ(), z2 = point2.getZ(),
			       px = p.getLocation().getX(),
			       py = p.getLocation().getY(),
			       pz = p.getLocation().getZ();
			
				if( (((py <= y1) && 
					(py >= y2)) || 
					((py >= y1) && 
					(py <= y2))) && 
					(((pz <= z1) && 
					(pz >= z2)) || 
					((pz >= z1) && 
					(pz <= z2)))  &&  
					(((px <= x1) && 
					(px >= x2)) || 
					((px >= x1) && 
					(px <= x2))) && 
					(((px <= x1) && 
					(px >= x2)) || 
					((px >= x1) && 
					(px <= x2)))   ){
					playerSet.add(p);
				}	
		}
		
		return playerSet;
	}

	@Override
	public List<Entity> getEntitiesInCuboid(Location point1,
			Location point2) {
		
		List<Entity> entitySet = new ArrayList<Entity>();
		World w = point1.getWorld();
		
		for(Entity e : w.getEntities()){
		
				double x1 = point1.getX(), x2 = point2.getX(),
			       y1 = point1.getY(), y2 = point2.getY(),
			       z1 = point1.getZ(), z2 = point2.getZ(),
			       ex = e.getLocation().getX(),
			       ey = e.getLocation().getY(),
			       ez = e.getLocation().getZ();
			
				if( (((ey <= y1) && 
					(ey >= y2)) || 
					((ey >= y1) && 
					(ey <= y2))) && 
					(((ez <= z1) && 
					(ez >= z2)) || 
					((ez >= z1) && 
					(ez <= z2)))  &&  
					(((ex <= x1) && 
					(ex >= x2)) || 
					((ex >= x1) && 
					(ex <= x2))) && 
					(((ex <= x1) && 
					(ex >= x2)) || 
					((ex >= x1) && 
					(ex <= x2)))   ){
					entitySet.add(e);
				}	
		}
		
		return entitySet;
	}

	@Override
	public List<Entity> getEntitiesInRadius(Location centre, int radius) {
		// TODO Auto-generated method stub
		List<Entity> entitySet = new ArrayList<Entity>();
		Location point1 = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
		Location point2 = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
		World w = point1.getWorld();
		
		for(Entity e : w.getEntities()){
		
				double x1 = point1.getX(), x2 = point2.getX(),
			       y1 = point1.getY(), y2 = point2.getY(),
			       z1 = point1.getZ(), z2 = point2.getZ(),
			       ex = e.getLocation().getX(),
			       ey = e.getLocation().getY(),
			       ez = e.getLocation().getZ();
			
				if( (((ey <= y1) && 
					(ey >= y2)) || 
					((ey >= y1) && 
					(ey <= y2))) && 
					(((ez <= z1) && 
					(ez >= z2)) || 
					((ez >= z1) && 
					(ez <= z2)))  &&  
					(((ex <= x1) && 
					(ex >= x2)) || 
					((ex >= x1) && 
					(ex <= x2))) && 
					(((ex <= x1) && 
					(ex >= x2)) || 
					((ex >= x1) && 
					(ex <= x2)))   ){
					entitySet.add(e);
				}	
		}
		
		return entitySet;
	}

	@Override
	public int getBlockCountInCuboid(Location point1, Location point2) {
		// TODO Auto-generated method stub
		int x1 = point1.getBlockX(), x2 = point2.getBlockX(),
	       y1 = point1.getBlockY(), y2 = point2.getBlockY(),
	       z1 = point1.getBlockZ(), z2 = point2.getBlockZ();

		int xdist, zdist, ydist;
		
		if(x1 > x2){ xdist = Math.round(x1 - x2); } else { xdist = Math.round(x2 - x1); }
		if(y1 > y2){ ydist = Math.round(y1 - y2); } else { ydist = Math.round(y2 - y1); }
		if(z1 > z2){ zdist = Math.round(z1 - z2); } else { zdist = Math.round(z2 - z1); }
		
		int blockCount = xdist * ydist * zdist;
		
		return blockCount;
	}

	@Override
	public int getBlockCountInRadius(Location centre, int radius) {
		// TODO Auto-generated method stub
		Location point1 = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
		Location point2 = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
		
		int x1 = point1.getBlockX(), x2 = point2.getBlockX(),
	       y1 = point1.getBlockY(), y2 = point2.getBlockY(),
	       z1 = point1.getBlockZ(), z2 = point2.getBlockZ();
		
		int xdist, zdist, ydist;
		
		if(x1 > x2){ xdist = Math.round(x1 - x2); } else { xdist = Math.round(x2 - x1); }
		if(y1 > y2){ ydist = Math.round(y1 - y2); } else { ydist = Math.round(y2 - y1); }
		if(z1 > z2){ zdist = Math.round(z1 - z2); } else { zdist = Math.round(z2 - z1); }
		
		int blockCount = xdist * ydist * zdist;
		
		return blockCount;
	}

	@Override
	public ArrayList<Block> getBlocksInRegion(Location point1, Location point2) {
		ArrayList<Block> al = new ArrayList<Block>();
		double x1 = point1.getX(), x2 = point2.getX(),
		    y1 = point1.getY(), y2 = point2.getY(),
		    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		World w = point1.getWorld();
		
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double x = x1 - x2; x <= x2; x++){
			for(double y = y1 - y2; y <= y2; y++){
				for(double z = z1 - z2; z <= z2; z++){
					Location construct = new Location(w, x2 + x, y2 + y, z2 + z);
					al.add(w.getBlockAt(construct));//This should add every block in the 3x3 cube, putting 9 blocks in the ArrayList.
				}
			}
		}
		return al;
	}

	

}
