package couk.Adamki11s.Extras.Terrain;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ExtrasTerrain extends TerrainMethods {

	@Override
	public void generateCube(int length, Location startPoint, Material m, Byte data) {
		// TODO Auto-generated method stub
		double xx = startPoint.getX(), yy = startPoint.getY(), zz = startPoint.getZ();
		World w = startPoint.getWorld();

		
		for(double x = xx - length; x <= xx; x++){
			for(double y = yy - length; y <= yy; y++){
				for(double z = zz - length; z <= zz; z++){
					Location construct = new Location(w, x, y, z);
					w.getBlockAt(construct).setType(m);
					if(data != null){
						w.getBlockAt(construct).setData(data);
					}
				}
			}
		}
	}

	@Override
	public void generateCube(int length, Location startPoint, int id, Byte data) {
		// TODO Auto-generated method stub
		double xx = startPoint.getX(), yy = startPoint.getY(), zz = startPoint.getZ();
		World w = startPoint.getWorld();	
		for(double x = xx - length; x <= xx; x++){
			for(double y = yy - length; y <= yy; y++){
				for(double z = zz - length; z <= zz; z++){
					Location construct = new Location(w, x, y, z);
					w.getBlockAt(construct).setTypeId(id);
					if(data != null){
						w.getBlockAt(construct).setData(data);
					}
				}
			}
		}
	}

	@Override
	public void deleteSpecificCube(int length, Location startPoint, Material m,
			Byte data) {
		double xx = startPoint.getX(), yy = startPoint.getY(), zz = startPoint.getZ();
		World w = startPoint.getWorld();

		
		for(double x = xx - length; x <= xx; x++){
			for(double y = yy - length; y <= yy; y++){
				for(double z = zz - length; z <= zz; z++){
					Location construct = new Location(w, x, y, z);
					if(w.getBlockAt(construct).getType() == m){
						if(data != null){
							if(w.getBlockAt(construct).getData() == data){
								w.getBlockAt(construct).setType(Material.AIR);
							}
						} else {
							w.getBlockAt(construct).setType(Material.AIR);
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteSpecificCube(int length, Location startPoint, int id,
			Byte data) {
		double xx = startPoint.getX(), yy = startPoint.getY(), zz = startPoint.getZ();
		World w = startPoint.getWorld();

		
		for(double x = xx - length; x <= xx; x++){
			for(double y = yy - length; y <= yy; y++){
				for(double z = zz - length; z <= zz; z++){
					Location construct = new Location(w, x, y, z);
					if(w.getBlockAt(construct).getTypeId() == id){
						if(data != null){
							if(w.getBlockAt(construct).getData() == data){
								w.getBlockAt(construct).setType(Material.AIR);
							}
						} else {
							w.getBlockAt(construct).setType(Material.AIR);
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteAllCube(int length, Location startPoint) {
		// TODO Auto-generated method stub
		double xx = startPoint.getX(), yy = startPoint.getY(), zz = startPoint.getZ();
		World w = startPoint.getWorld();
		
		for(double x = xx - length; x <= xx; x++){
			for(double y = yy - length; y <= yy; y++){
				for(double z = zz - length; z <= zz; z++){
					Location construct = new Location(w, x, y, z);	
				    w.getBlockAt(construct).setType(Material.AIR);
				}
			}
		}
	}

	@Override
	public void generateFromPoints(Location point1, Location point2,
			Material m, Byte data) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(data != null){
						w.getBlockAt(construct).setType(m);
						w.getBlockAt(construct).setData(data);
					} else {
						w.getBlockAt(construct).setType(m);
					}
				}
			}
		}
	}

	@Override
	public void generateFromPoints(Location point1, Location point2,
			int id, Byte data) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(data != null){
						w.getBlockAt(construct).setTypeId(id);
						w.getBlockAt(construct).setData(data);
					} else {
						w.getBlockAt(construct).setTypeId(id);
					}
				}
			}
		}
	}

	@Override
	public void generateFromArrayList(ArrayList<Location> ArrayList, Material m,
			Byte data) {
		for(Location l : ArrayList){
			if(data != null){
				l.getWorld().getBlockAt(l).setType(m);
				l.getWorld().getBlockAt(l).setData(data);
			} else {
				l.getWorld().getBlockAt(l).setType(m);
			}
		}
		
	}

	@Override
	public void generateFromArrayList(ArrayList<Location> ArrayList, int id, Byte data) {
		for(Location l : ArrayList){
			if(data != null){
				l.getWorld().getBlockAt(l).setTypeId(id);
				l.getWorld().getBlockAt(l).setData(data);
			} else {
				l.getWorld().getBlockAt(l).setTypeId(id);
			}
		}
	}

	@Override
	public void deleteSpecificFromPoints(Location point1, Location point2,
			Material m, Byte data) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(data != null){
						if(w.getBlockAt(construct).getType() == m && w.getBlockAt(construct).getData() == data){
							w.getBlockAt(construct).setTypeId(0);
						}
					} else {
						if(w.getBlockAt(construct).getType() == m){
							w.getBlockAt(construct).setTypeId(0);
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteSpecificFromPoints(Location point1, Location point2,
			int id, Byte data) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(data != null){
						if(w.getBlockAt(construct).getTypeId() == id && w.getBlockAt(construct).getData() == data){
							w.getBlockAt(construct).setTypeId(0);
						}
					} else {
						if(w.getBlockAt(construct).getTypeId() == id){
							w.getBlockAt(construct).setTypeId(0);
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteAllFromPoints(Location point1, Location point2) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					w.getBlockAt(construct).setTypeId(0);
				}
			}
		}
	}

	@Override
	public void replaceBlocks(Material toReplace, Byte toReplaceData,
			Material replaceWith, Byte replaceWithData, Location point1,
			Location point2) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(toReplaceData != null){
						if(w.getBlockAt(construct).getType() == toReplace && w.getBlockAt(construct).getData() == toReplaceData){
							if(replaceWithData != null){
								w.getBlockAt(construct).setType(replaceWith);
								w.getBlockAt(construct).setData(replaceWithData);
							} else {
								w.getBlockAt(construct).setType(replaceWith);
							}
							
						}
					} else {
						if(w.getBlockAt(construct).getType() == toReplace){
							if(replaceWithData != null){
								w.getBlockAt(construct).setType(replaceWith);
								w.getBlockAt(construct).setData(replaceWithData);
							} else {
								w.getBlockAt(construct).setType(replaceWith);
							}
						}
					}
				}
			}
		}
	}
	
	public void replaceBlocks(int toReplace, Byte toReplaceData,
			int replaceWith, Byte replaceWithData, Location point1,
			Location point2) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					if(toReplaceData != null){
						if(w.getBlockAt(construct).getTypeId() == toReplace && w.getBlockAt(construct).getData() == toReplaceData){
							if(replaceWithData != null){
								w.getBlockAt(construct).setTypeId(replaceWith);
								w.getBlockAt(construct).setData(replaceWithData);
							} else {
								w.getBlockAt(construct).setTypeId(replaceWith);
							}
							
						}
					} else {
						if(w.getBlockAt(construct).getTypeId() == toReplace){
							if(replaceWithData != null){
								w.getBlockAt(construct).setTypeId(replaceWith);
								w.getBlockAt(construct).setData(replaceWithData);
							} else {
								w.getBlockAt(construct).setTypeId(replaceWith);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void simulateSnow(Location point1, Location point2) {
		double x1 = point1.getX(), x2 = point2.getX(),
	    y1 = point1.getY(), y2 = point2.getY(),
	    z1 = point1.getZ(), z2 = point2.getZ(), tmp = 0;
		
		World w = point1.getWorld();
	
		if(x2 > x1){ tmp = x2; x2 = x1; x1 = tmp; }
		if(y2 > y1){ tmp = y2; y2 = y1; y1 = tmp; }
		if(z2 > z1){ tmp = z2; z2 = z1; z1 = tmp; }
		
		for(double xx = x1 - (x1 - x2); xx <= x1; xx++){
			for(double yy = y1 - (y1 - y2); yy <= y1; yy++){
				for(double zz = z1 - (z1 - z2); zz <= z1; zz++){
					Location construct = new Location(w, xx, yy, zz);
					Block b = w.getBlockAt(construct);
					if(b.getTypeId() != 0){
						Block offset = b.getRelative(0, 1, 0);
						if(offset.getType() != Material.SNOW){
							offset.setType(Material.SNOW);
						}
					}
				}
			}
		}
	}

	@Override
	public void simulateSnowFromArrayList(ArrayList<Block> blockSet) {
		for(Block b : blockSet){
			Block bRel = b.getRelative(0, 1, 0);
			bRel.setType(Material.SNOW);
		}
	}

	

}
