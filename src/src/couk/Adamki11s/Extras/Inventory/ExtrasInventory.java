package couk.Adamki11s.Extras.Inventory;

import java.util.HashMap;

import net.minecraft.server.Container;

import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExtrasInventory extends InventoryMethods {
	
	private HashMap<Player, ItemStack[]> playerInventory = new HashMap<Player, ItemStack[]>();
 
	@Override
	public void addToInventory(Player p, Material m, int quantity) {
		p.getInventory().addItem(new ItemStack(m.getId(), quantity));
	}

	@Override
	public void addToInventory(Player p, int id, int quantity) {
		p.getInventory().addItem(new ItemStack(id, quantity));
	}

	@Override
	public void removeFromInventory(Player p, Material m, int quantity) {
		int amount = getAmountOf(p, m.getId());
		p.getInventory().remove(m.getId());
		addToInventory(p, m.getId(), amount - quantity);
	}

	@Override
	public void removeFromInventory(Player p, int id, int quantity) {
		int amount = getAmountOf(p, id);
		p.getInventory().remove(id);
		addToInventory(p, id, amount - quantity);
	}

	@Override
	public int getEmptySlots(Player p) {
		ItemStack[] invent = p.getInventory().getContents();
		int freeSlots = 0;
		for(ItemStack i : invent){
			if(i == null){
				freeSlots++;
			}
		}
		return freeSlots;
	}

	@Override
	public boolean doesInventoryContain(Player p, Material m) {
		ItemStack[] invent = p.getInventory().getContents();
		for(ItemStack i : invent){
			if(i != null){
				if(i.getType() == m){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean doesInventoryContain(Player p, int id) {
		ItemStack[] invent = p.getInventory().getContents();
		for(ItemStack i : invent){
			if(i != null){
				if(i.getTypeId() == id){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getStackCount(Player p, Material m) {
		ItemStack[] invent = p.getInventory().getContents();
		int stackCount = 0;
		for(ItemStack i : invent){
			if(i != null){
				if(i.getType() == m){
					stackCount++;
				}
			}
		}
		return stackCount;
	}

	@Override
	public int getStackCount(Player p, int id) {
		ItemStack[] invent = p.getInventory().getContents();
		int stackCount = 0;
		for(ItemStack i : invent){
			if(i != null){
				if(i.getTypeId() == id){
					stackCount++;
				}
			}
		}
		return stackCount;
	}

	@Override
	public int getAmountOf(Player p, Material m) {
		ItemStack[] invent = p.getInventory().getContents();
		int amount = 0;
		for(ItemStack i : invent){
			if(i != null){
				if(i.getType() == m){
					amount += i.getAmount();
				}
			}
		}
		return amount;
	}

	@Override
	public int getAmountOf(Player p, int id) {
		ItemStack[] invent = p.getInventory().getContents();
		int amount = 0;
		for(ItemStack i : invent){
			if(i != null){
				if(i.getTypeId() == id){
					amount += i.getAmount();
				}
			}
		}
		return amount;
	}
	
	@Override
	public void sortInventory(Player p) {
		ItemStack[] invent = p.getInventory().getContents();
		for(ItemStack i : invent){
			if(i != null){
				removeFromInventory(p, i.getType(), 0);
			}
		} 
	}

	@Override
	public void removeAllFromInventory(Player p, int id) {
		if(doesInventoryContain(p, id)){
			p.getInventory().remove(id);
		}
	}

	@Override
	public void removeAllFromInventory(Player p, Material m) {
		if(doesInventoryContain(p, m)){
			p.getInventory().remove(m);
		}	
	}

	@Override
	public void wipeInventory(Player p) {
		// TODO Auto-generated method stub
		for(ItemStack i : p.getInventory().getContents()){
			if(i != null){
				p.getInventory().remove(i.getTypeId());
			}
		}
	}

	@Override
	public boolean storeInventory(Player p) {
		if(p != null){
			playerInventory.put(p, p.getInventory().getContents());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack[] retrieveInventory(Player p) {
		if(playerInventory.containsKey(p)){
			return playerInventory.get(p);
		} else {
			return null;
		}
	}

	@Override
	public void updateInventory(Player p) {
		((CraftPlayer)p).getHandle().syncInventory();
	}

}
