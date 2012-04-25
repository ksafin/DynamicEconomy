package me.ksafin.DynamicEconomy;


import java.util.Set;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.AutoUpdater.*;



public class DynamicEconomyPlayerListener implements Listener {
	
	public static Permission permission = null;
	private final ExtrasColour color = new ExtrasColour();
	public AUCore updater = null;
	public double fullver;
	public double subver;
	
	private static Logger log = Logger.getLogger("Minecraft");
	
    public DynamicEconomy plugin;
    
    public DynamicEconomyPlayerListener(DynamicEconomy instance) {
    	//Bukkit.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.High, instance);
    	plugin = instance;
    }
    
    @EventHandler
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
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack inHand = player.getItemInHand();
        org.bukkit.Material item = inHand.getType();
        if (item.equals(org.bukkit.Material.WOOD_SPADE)) {
        	if (DynamicEconomyCommandExecutor.permission.has(player, "dynamiceconomy.selectregion")) {
        		Block block = event.getClickedBlock();
        		if (block != null) {
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
    }
       
    @EventHandler
    public void onSignChange (final SignChangeEvent event) {
    	new dataSigns(event);
    }
    
    @EventHandler
    public void onBlockBreak (final BlockBreakEvent event) {
    	Block block = event.getBlock();
    	int id = block.getTypeId();
    	if ((id == 68) || (id == 63) || (id == 323)) {
    		dataSigns.removeDataSign(block);
    	}
    }
    
    public int getExpBylevel(int level) {
    	if (level == 0) {
    		return -3;
    	}
    	int subBin = ((level-1) * 7);
    	String bin = Integer.toBinaryString(subBin);
    	bin = bin.substring(0,bin.length() - 1);
    	int dec;
    	
    	if(!(bin.equals(""))) {
    		dec = Integer.parseInt(bin,2);
    	} else {
    		dec = 0;
    	}
    	
    	return (7+dec);
    	
    }
    
    public int getTotalExp(Player player) {
    	int total = player.getTotalExperience();
    	float remaining = 1 - player.getExp();
    	
    	int diffLevels = getExpBylevel(player.getLevel() + 1) - getExpBylevel(player.getLevel());
    	int remainingExp = Math.round(remaining * diffLevels);
    	
    	double ln = getExpBylevel(player.getLevel());
    	double ln_1 = getExpBylevel(player.getLevel() - 1);
    	
    	long sum = Math.round(((ln - remainingExp) / ln) * ln_1);
    	
    	int experience = (int)(total - ln + sum);
    	
    	return experience;
    }
    
    @EventHandler
    public void onEnchantItem(final EnchantItemEvent event) {
    	Player player = event.getEnchanter();
    	
    	int numLevels = event.getExpLevelCost();
    	
    	int newExp;
    	
    	for (int x = 0; x < numLevels; x++) {
    		newExp = getTotalExp(player);
    		player.setTotalExperience(0);
    		player.setLevel(0);
    		player.setExp(0);
    		player.giveExp(newExp);
    	}
    	
    	
		
		log.info("Player Total Exp In Event: " + player.getTotalExperience());
		log.info("Player Level In Event: " + player.getLevel());
		log.info("Player Exp In Event: " + player.getExp());
    	
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
