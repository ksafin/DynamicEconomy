package me.ksafin.DynamicEconomy;


import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.Material;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.AutoUpdater.*;



public class DynamicEconomyPlayerListener extends PlayerListener {
	
	public static Permission permission = null;
	private final ExtrasColour color = new ExtrasColour();
	public AUCore updater = null;
	public double fullver;
	public double subver;
	
    public DynamicEconomy plugin;
    
    public DynamicEconomyPlayerListener(DynamicEconomy instance) {
    	//Bukkit.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.High, instance);
    	plugin = instance;
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
    	if (DynamicEconomy.enableUpdateChecker) {
    	final Player player = event.getPlayer();
    	final String stringPlay = player.getName();
    	boolean latest = updater.checkVersion(fullver,subver,"DynamicEconomy");
    	Utility.writeToLog(stringPlay + " logged in.");
    	if (latest == false) {
    		 if (permission.has(player,"DynamicEconomy.update")) {
    			 plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    				 public void run() {
    				 color.sendColouredMessage(player, "&2New Version of DynamicEconomy Available!");
        			 Utility.writeToLog(stringPlay + " notified of new DynamicEconomy version");
    				 }
    			 }, 60L);
    			 
    		 }
    	}
    }
    }
    
    public void onPlayerInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack inHand = player.getItemInHand();
        org.bukkit.Material item = inHand.getType();
        if (item.equals(org.bukkit.Material.WOOD_SPADE)) {
        	if (DynamicEconomyCommandExecutor.permission.has(player, "dynamiceconomy.selectregion")) {
        		Block block = event.getClickedBlock();
    			String playerName = player.getName();
    			int x = block.getX();
    			int y = block.getY();
    			int z = block.getZ();
    			String xyz = x + " " + y + " " + z;
    			String[] coords = new String[2];
        	if (action == Action.RIGHT_CLICK_BLOCK) {
        			coords[1] = xyz;
        			if (DynamicEconomy.selectedCorners.containsKey(playerName)) {
        				Object coordinates = DynamicEconomy.selectedCorners.get(playerName);
        				String[] stringCoords = (String[]) coordinates;
        				stringCoords[1] = xyz;
        				DynamicEconomy.selectedCorners.put(playerName, stringCoords);
        			} else {
        				DynamicEconomy.selectedCorners.put(playerName, coords);
        			}
        			color.sendColouredMessage(player, "&2Selected block #2: " + x + " , " + y + " , " + z);
       			    Utility.writeToLog(playerName + " Selected block #2: " + x + " , " + y + " , " + z);
        	} else if (action == Action.LEFT_CLICK_BLOCK) {
    			coords[0] = xyz;
    			if (DynamicEconomy.selectedCorners.containsKey(playerName)) {
    				Object coordinates = DynamicEconomy.selectedCorners.get(playerName);
    				String[] stringCoords = (String[]) coordinates;
    				stringCoords[0] = xyz;
    				DynamicEconomy.selectedCorners.put(playerName, stringCoords);
    			} else {
    				DynamicEconomy.selectedCorners.put(playerName, coords);
    			}
    			color.sendColouredMessage(player, "&2Selected block #1: " + x + " , " + y + " , " + z);
   			    Utility.writeToLog(playerName + " Selected block #1: " + x + " , " + y + " , " + z);
        	}
        	}
        }
    }
       
    
    public boolean setPermission(Permission perm) {
    	permission = perm;
    	return true;
    }
    
    public boolean setUpdater(AUCore up) {
    	updater = up;
    	return true;
    }
    
}
