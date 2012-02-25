package me.ksafin.DynamicEconomy;

import couk.Adamki11s.Extras.Colour.*;
import couk.Adamki11s.AutoUpdater.*;
import net.milkbowl.vault.permission.*;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.command.PluginCommandYamlParser;

import couk.Adamki11s.Extras.Colour.ExtrasColour;

/**
 *
 * @author BOSS
 */
public class DynamicEconomyCommandExecutor implements org.bukkit.command.CommandExecutor {

    private File itemsFile;
    private FileConfiguration itemConfig;
    private ExtrasColour color = new ExtrasColour();
    private Item item;
    
    private DynamicEconomy plugin;
    private PluginDescriptionFile pluginDescription;
    
    private Economy economy;
    public static Permission permission = null;
    private Transaction trans;
    
    private FileConfiguration config;
    
    public AUCore updater = null;
    public double fullver;
    public double subver;
    
    public File confFile;
    
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Player player = null;
        
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        
        
       // PRICE COMMAND 
        
        
	if ((cmd.getName().equalsIgnoreCase("price")) || (cmd.getName().equalsIgnoreCase("deprice"))) { 
                if (permission.has(player, "dynamiceconomy.price")) {
                    item.getPrice(player,args);
                } else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /price but did not have permission.");
                }
                return true;
	}       
        // SETPRICE COMMAND
          if (cmd.getName().equalsIgnoreCase("setprice")) {
        	if (permission.has(player, "dynamiceconomy.setprice")) {
        		item.setPrice(player,args);
        	} else {
                color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                Utility.writeToLog(player.getName() + " called /setprice but did not have permission.");
            }
        	return true;
        }
          
        // SETFLOOR COMMAND
          if (cmd.getName().equalsIgnoreCase("setfloor")) {
          	if (permission.has(player, "dynamiceconomy.setfloor")) {
          		item.setFloor(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /setfloor but did not have permission.");
              }
          	return true;
          }
          
          //SETCEILING COMMAND
          
          if (cmd.getName().equalsIgnoreCase("setceiling")) {
            	if (permission.has(player, "dynamiceconomy.setceiling")) {
            		item.setCeiling(player,args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /setceiling but did not have permission.");
                }
            	return true;
            }
          
          // GETFLOOR COMMAND
          
          if (cmd.getName().equalsIgnoreCase("getfloor")) {
          	if (permission.has(player, "dynamiceconomy.getfloor")) {
          		item.getFloor(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /getfloor but did not have permission.");
              }
          	return true;
          }
          
          // GETCEILING COMMAND
          
          if (cmd.getName().equalsIgnoreCase("getceiling")) {
          	if (permission.has(player, "dynamiceconomy.getceiling")) {
          		item.getCeiling(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /getceiling but did not have permission.");
              }
          	return true;
          }
          
          // GETVELOCITY COMMAND
          
          if (cmd.getName().equalsIgnoreCase("getvelocity")) {
            	if (permission.has(player, "dynamiceconomy.getvelocity")) {
            		item.getVelocity(player,args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /getvelocity but did not have permission.");
                }
            	return true;
            }
          
          // SETVELOCITY COMMAND
          
          if (cmd.getName().equalsIgnoreCase("setvelocity")) {
          	if (permission.has(player, "dynamiceconomy.setvelocity")) {
          		item.setVelocity(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /setvelocity but did not have permission.");
              }
          	return true;
          }
          
          // DYNAMICECONOMY COMMAND
          
          if (cmd.getName().equalsIgnoreCase("dynamiceconomy")) {
            	if (permission.has(player, "dynamiceconomy.dynamiceconomy")) {
            		this.commandList(player,args);
            		 Utility.writeToLog(player.getName() + " called /dynamiceconomy for help");
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /dynamiceconomy but did not have permission.");
                }
            	return true;
            }
          
          if (cmd.getName().equalsIgnoreCase("dynecon")) {
          	if (permission.has(player, "dynamiceconomy.dynamiceconomy")) {
          		this.commandList(player,args);
          		Utility.writeToLog(player.getName() + " called /dynamiceconomy for help");
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /dynecon but did not have permission.");
              }
          	return true;
          }
          
          // BUY COMMAND
          
          if ((cmd.getName().equalsIgnoreCase("buy")) || (cmd.getName().equalsIgnoreCase("debuy"))) {
          	if (permission.has(player, "dynamiceconomy.buy")) {
          		Location loc = player.getLocation();
          		int y = loc.getBlockY();
          		if (DynamicEconomy.location_restrict) {
          			if ((y <= DynamicEconomy.maximum_y) & (y >= DynamicEconomy.minimum_y)) {
          				trans.buy(player,args);
          			} else if (y <= DynamicEconomy.minimum_y) {
          				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are too deep underground to access the economy!.");
          				Utility.writeToLog(player.getName() + " called /buy but was too deep underground.");
          			} else if (y >= DynamicEconomy.maximum_y) {
          				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are too high up to access the economy!.");
         				Utility.writeToLog(player.getName() + " called /sell but was too high up.");
          			}
          		} else {
          			trans.buy(player,args);
          		}
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /buy but did not have permission.");
              }
          	return true;
          }
          
          // SELL COMMAND
          
          if ((cmd.getName().equalsIgnoreCase("sell")) || (cmd.getName().equalsIgnoreCase("desell"))){
            	if (permission.has(player, "dynamiceconomy.sell")) {
            		Location loc = player.getLocation();
              		int y = loc.getBlockY();
              		if (DynamicEconomy.location_restrict) {
              			if ((y <= DynamicEconomy.maximum_y) & (y >= DynamicEconomy.minimum_y)) {
              				trans.sell(player,args);
              			} else if (y <= DynamicEconomy.minimum_y) {
              				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are too deep underground to access the economy!.");
              				Utility.writeToLog(player.getName() + " called /sell but was too deep underground.");
              			} else if (y >= DynamicEconomy.maximum_y) {
              				color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You are too high up to access the economy!.");
              				Utility.writeToLog(player.getName() + " called /sell but was too high up.");
              			}          		
            	}  else {
            		trans.sell(player,args);
            	}
            	
            } else {
            	
                color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                Utility.writeToLog(player.getName() + " called /sell but did not have permission.");
            }
        	return true;
          }
          
          // ISSTOCK COMMAND
          
          if (cmd.getName().equalsIgnoreCase("isstock")) {
          	if (permission.has(player, "dynamiceconomy.isstock")) {
          		this.getStockBoolean(player,args);
          		Utility.writeToLog(player.getName() + " called /isstock");
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /isstock but did not have permission.");
              }
          	return true;
          }
          
          // ISBOUNDARY COMMAND
          
          if (cmd.getName().equalsIgnoreCase("isboundary")) {
            	if (permission.has(player, "dynamiceconomy.isboundary")) {
            		this.getBoundaryBoolean(player,args);
            		Utility.writeToLog(player.getName() + " called /isboundary");
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /isboundary but did not have permission.");
                }
            	return true;
            }
          
          // ADDSTOCK COMMAND
          
          if (cmd.getName().equalsIgnoreCase("addstock")) {
          	if (permission.has(player, "dynamiceconomy.addstock")) {
          		trans.addStock(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /addstock but did not have permission.");
              }
          	return true;
          }
          
          // DYNAMICECONOMYRELOADCONFIG COMMAND
          
          if (cmd.getName().equalsIgnoreCase("dynamiceconomyreloadconfig")) {
            	if (permission.has(player, "dynamiceconomy.dynamiceconomyreloadconfig")) {
            		DynamicEconomy.reloadConfigValues(player,args);
            		Utility.writeToLog(player.getName() + " reloaded the DynamicEconomy config.yml");
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /dynamiceconomyreloadconfig but did not have permission.");
                }
            	return true;
            }
          
          // REMOVESTOCK COMMAND
          
          if (cmd.getName().equalsIgnoreCase("removestock")) {
          	if (permission.has(player, "dynamiceconomy.removestock")) {
          		trans.removeStock(player,args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /removestock but did not have permission.");
              }
          	return true;
          }
          
          // GETDURABILITY COMMAND
          
          if (cmd.getName().equalsIgnoreCase("getdurability")) {
            	if (permission.has(player, "dynamiceconomy.getdurability")) {
            		Item.getDurCommand(player,args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /getdurability but did not have permission.");
                }
            	return true;
            }
          
          // HASUPDATE COMMAND
          
          if (cmd.getName().equalsIgnoreCase("hasupdate")) {
          	if (permission.has(player, "dynamiceconomy.update")) {
          		if (DynamicEconomy.enableUpdateChecker) {
          			boolean isLatest = updater.checkVersion(fullver,subver,"DynamicEconomy");
          			Utility.writeToLog(player.getName() + " called /hasupdate");
          			if (!isLatest) {
          			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2New Version of DynamicEconomy Available!");
          			} else {
          			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You have the latest version of DynamicEconomy!");
          			}
          		}
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /hasupdate but did not have permission.");
              }
          	return true;
          }
          
          // GETUPDATE COMMAND
          
          if (cmd.getName().equalsIgnoreCase("dyneconupdate")) {
            	if (permission.has(player, "dynamiceconomy.update")) {
            		if (DynamicEconomy.enableUpdateChecker) {
            			Utility.writeToLog(player.getName() + " called /dyneconupdate and downloaded the latest version of DynamicEconomy");
            			updater.forceDownload("http://cabin.minecraft.ms/downloads/DynamicEconomy.jar", "DynamicEconomy",player);
            		}
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /dyneconupdate but did not have permission.");
                }
            	return true;
            }
          
          // GET TAXES COMMAND
          
          if (cmd.getName().equalsIgnoreCase("curtaxes")) {
            	if (permission.has(player, "dynamiceconomy.curtaxes")) {
            		trans.curTaxes(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /curtaxes but did not have permission.");
                }
            	return true;
            }
          
          // SET TAX COMMAND
          
          if (cmd.getName().equalsIgnoreCase("settax")) {
          	if (permission.has(player, "dynamiceconomy.settax")) {
          		trans.setTaxes(player, args);
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /settax but did not have permission.");
              }
          	return true;
          }
          
          // DECLARE REGIONSHOP COMMAND
          
          if (cmd.getName().equalsIgnoreCase("shopregion")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.createRegion(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /shopregion but didn't have permission");
                }
            	return true;
            }
          
          // REMOVE SHOP REGION COMMAND
          
          if (cmd.getName().equalsIgnoreCase("removeshopregion")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.deleteShopRegion(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /shopregion but didn't have permission");
                }
            	return true;
            }
          
          // EXPAND SHOP REGION COMMAND
          
          if (cmd.getName().equalsIgnoreCase("expandreg")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.expandRegion(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /shopregion but didn't have permission");
                }
            	return true;
            }
          
          // CONTRACT SHOP REGION COMMAND
          
          if (cmd.getName().equalsIgnoreCase("contractreg")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.contractRegion(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /shopregion but didn't have permission");
                }
            	return true;
            }
          
          // SHOP REGION WAND COMMAND
          
          if (cmd.getName().equalsIgnoreCase("shopregionwand")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.wand(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /shopregionwand but didn't have permission");
                }
            	return true;
            }
          
          // SHOP CURREGION COMMAND
          
          if (cmd.getName().equalsIgnoreCase("curregion")) {
            	if (permission.has(player, "dynamiceconomy.shopregion")) {
            		regionUtils.getCorners(player, args);
            	} else {
                    color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                    Utility.writeToLog(player.getName() + " called /curregion but didn't have permission");
                }
            	return true;
            }
          
          // LOANS COMMAND
          
          if (cmd.getName().equalsIgnoreCase("loan")) {
          	if (permission.has(player, "dynamiceconomy.loan")) {
          		if (DynamicEconomy.useLoans) {
          		   loan.lend(player, args);
          		} else {
          			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The Bank is not available for loans at this time.");
                    Utility.writeToLog(player.getName() + " called /loan but loans are disabled.");
          		}
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /loan but didn't have permission");
              }
          	return true;
          }
          
          // GET INTEREST RATE COMMAND
          
          if (cmd.getName().equalsIgnoreCase("curinterest")) {
          	if (permission.has(player, "dynamiceconomy.curinterest")) {
          		if (DynamicEconomy.useLoans) {
          		    loan.getInterest(player, args);
          		} else {
          			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The Bank is not available for loans at this time.");
                    Utility.writeToLog(player.getName() + " called /curinterest but loans are disabled.");
          		}
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /curinterest but didn't have permission");
              }
          	return true;
          }
          
          // GET LOANS COMMAND
          
          if (cmd.getName().equalsIgnoreCase("curloans")) {
          	if (permission.has(player, "dynamiceconomy.loan")) {
          		if (DynamicEconomy.useLoans) {
          		   loan.getLoans(player,args);
          		} else {
          			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2The Bank is not available for loans at this time.");
                    Utility.writeToLog(player.getName() + " called /curloans but loans are disabled.");
          		}
          	} else {
                  color.sendColouredMessage(player, DynamicEconomy.prefix + "&2You do not have permission to use this command.");
                  Utility.writeToLog(player.getName() + " called /curloans but didn't have permission");
              }
          	return true;
          }
          
	
	
	
	return false; 
}
    
    public DynamicEconomyCommandExecutor(DynamicEconomy plugin, PluginDescriptionFile desc, FileConfiguration conf, File cf){
        this.plugin = plugin;
        this.itemsFile = new File(plugin.getDataFolder(),"Items.yml");
        itemConfig = YamlConfiguration.loadConfiguration(itemsFile);
        item = new Item(itemConfig);
        pluginDescription = desc;
        config = conf;
        confFile = cf;
    }
    
    public void commandList(Player player, String[] args) {
    	ArrayList<Command> cmdList = (ArrayList<Command>) PluginCommandYamlParser.parse(plugin);
    	int length = cmdList.size();
    	int numPages = (int)((length/5.0) + 1);
    	int page;
    	
    	if (args.length == 0) { 
    		page = 1;
    	} else {
    		page = Integer.parseInt(args[0]);
    	}
    	
    	int startCommand = (page * 5) - 5;
    	int endCommand = (page * 5);
    	
    	if (endCommand > length) {
    		endCommand = length;
    	}
    	
    	
    		color.sendColouredMessage(player, "&2---------&fDynamicEconomy Commands&2---------");
    		for (int x = startCommand; x < endCommand; x++) {
    			Command cmd = cmdList.get(x);
    			String command = cmd.getUsage();
    			String desc = cmd.getDescription();
    			String message = "&2" + command + " : &f" + desc;
    			color.sendColouredMessage(player, message);
    		}
    		color.sendColouredMessage(player, "&2----------------&fPage &f" + page + "/" + numPages + "&2----------------");
    	 
    }
    
    public void getStockBoolean(Player player, String[] args) {
    	if (args.length > 0) {
    		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/isstock");
    	} else {
    		Boolean isStock = config.getBoolean("Use-Stock",true);
    		if (isStock) {
    			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Stock is turned on.");
    		} else {
    			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Stock is turned off.");
    		}
    	}
    }
    
    public void getBoundaryBoolean(Player player, String[] args) {
    	if (args.length > 0) {
    		 color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Wrong Command Usage. &f/isboundary");
    	} else {
    		Boolean isStock = config.getBoolean("Use-boundaries",true);
    		if (isStock) {
    			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries are turned on.");
    		} else {
    			color.sendColouredMessage(player, DynamicEconomy.prefix + "&2Boundaries are turned off.");
    		}
    	}
    }
    
    public boolean setEconomy(Economy eco, FileConfiguration config) {
    	economy = eco;
    	setTrans(config);
    	return true;
    }
    
    public boolean setUpdater(AUCore up) {
    	updater = up;
    	return true;
    }
    
    public boolean setPermission(Permission perm) {
    	permission = perm;
    	return true;
    }
    
    public void setTrans(FileConfiguration config){
    	trans = new Transaction(economy,itemConfig,config,itemsFile,confFile);
    }
    

}
    
    
    








