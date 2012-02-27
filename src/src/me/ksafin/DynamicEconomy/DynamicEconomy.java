package me.ksafin.DynamicEconomy;


import java.io.File;
import couk.Adamki11s.AutoUpdater.*;


import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.Extras.Extras.Extras;

/**
 *
 * @author BOSS
 */
public class DynamicEconomy extends JavaPlugin {
    
    public static FileConfiguration config;
    
    String name;
    String version;
    
    
    private final DynamicEconomyPlayerListener playerListener = new DynamicEconomyPlayerListener(this);
    private DynamicEconomyCommandExecutor commandExec;
    public static Economy economy = null;
    public static Permission permission = null;
    public static ExtrasColour color = new ExtrasColour();
    
    public static boolean usestock;
    public static boolean useboundaries;
    public static String prefix;
    public static int defaultAmount;
    public static boolean localNotify;
    public static boolean globalNotify;
    public static boolean hasUpdate;
    public static boolean logwriting;
    public static boolean updateNotifOnLogin;
    public static double salestax;
    public static double purchasetax;
    public static boolean depositTax;
    public static String taxAccount;
    public static boolean location_restrict;
    public static int minimum_y;
    public static int maximum_y;
    public static boolean altCommands;
    public static boolean useRegions;
    public static boolean useLoans;
    public static double interestRate;
    public static int paybackTime;
    public static int maxLoans;
    public static double maxLoanAmount;
    public static double minLoanAmount;
    public static boolean useLoanAccount;
    public static String loanAccountName;
    public static long loanCheckInterval;
    public static boolean enableUpdateChecker;
    public static boolean useStaticInterest;
    public static double dynamicCompressionRate;
    public static String[] bannedSaleItems;
    public static String[] bannedPurchaseItems;
    public static boolean enableOverTimePriceDecay;
    public static double overTimePriceDecayPercent;
    public static long overTimePriceDecayPeriod;
    public static long overTimePriceDecayPeriodCheck;
    
    public AUCore updater = new AUCore("http://cabin.minecraft.ms/index.html", log, "[DynamicEconomy]");
    
    File regionFile;
    
    static DynamicEconomy plugin;
    
    public static HashMap selectedCorners = new HashMap();
    
    public static File configFile;
    FileConfiguration regionConfig;
    
    public static File loansFile;
    FileConfiguration loansFileConfig;
    
    public static File itemsFile;
    FileConfiguration itemConfig;

    static Logger log = Logger.getLogger("Minecraft");
    
   
    
    /**
     * @param args the command line arguments
     */
    @Override
    public void onEnable(){
            
            PluginManager pm = this.getServer().getPluginManager();
            PluginDescriptionFile pdf = pm.getPlugin("DynamicEconomy").getDescription();
            
            plugin = this;
            
            name = pdf.getName();
            version = pdf.getVersion();
            ArrayList<String> author = pdf.getAuthors();
            
        
            log.info(name +" v" + version + " by " + author.get(0) + " enabled!");
            
            boolean economyIsSet = setupEconomy();
            boolean permissionIsSet = setupPermissions();
            
             if (economyIsSet) {
            	 log.info("[DynamicEconomy] Vault Economy hooked");
             } else {
            	 log.info("[DynamicEconomy] Vault Economy not hooked");
             }
             
             if (permissionIsSet) {
            	 log.info("[DynamicEconomy] Vault Permissions hooked");
             } else {
            	 log.info("[DynamicEconomy] Vault Permissions not hooked");
             }
             
            playerListener.setPermission(permission);
            
            double fullVer = Double.parseDouble(version.substring(0,2));
            double subVer = Double.parseDouble(version.substring(2));
            
            //double versionD = Double.parseDouble(version);
            
            Extras extras = new Extras("DynamicEconomy");
            hasUpdate = !(updater.checkVersion(fullVer,subVer,"DynamicEconomy"));
            
            playerListener.fullver = fullVer;
            playerListener.subver = subVer;
            playerListener.setUpdater(updater);
            
            itemsFile = new File(this.getDataFolder(),"Items.yml");
            Item.setItemsFile(itemsFile);
            itemConfig = YamlConfiguration.loadConfiguration(itemsFile);
            
            regionFile = new File(this.getDataFolder(),"Regions.yml");
            regionUtils.setRegionFile(regionFile);
            regionConfig = YamlConfiguration.loadConfiguration(regionFile);
            regionUtils.setRegionFileConfig(regionConfig);
            
            
            loansFile = new File(this.getDataFolder(),"Loans.yml");
            loansFileConfig = YamlConfiguration.loadConfiguration(loansFile);
            
            loan.initFiles(loansFile, loansFileConfig);
            
            configFile = new File(this.getDataFolder(),"config.yml");
            config = YamlConfiguration.loadConfiguration(configFile);
            
            regionConfig.createSection("regions");
            
            Transaction.regionConfigFile = regionConfig;
            
            File logFile = new File(this.getDataFolder(),"log.txt");
            Utility utils = new Utility(logFile,this);
            
            
            
            if (itemConfig.contains("STONE")) {
            	log.info("[DynamicEconomy] Items database loaded.");
            } else {
            	try {
                   	itemConfig.save(itemsFile);
                } catch (Exception e) {
            		log.info("[DynamicEconomy] IOException when creating Items.yml in Main");
            		log.info(itemsFile.toString());
            		e.printStackTrace();
            	}
            		Initialize init = new Initialize(config,configFile);
            		init.setItemsData(itemConfig, itemsFile);
                	init.setItems(this);
            }
            
            if (configFile.exists()) {
            	log.info("[DynamicEconomy] Core Config loaded.");
            } else {
            	try {
            		config.save(configFile);
            	} catch (Exception e) {
            		log.info("[DynamicEconomy] IOException when creating config.yml in Main");
            		e.printStackTrace();
            	}
            	Initialize.setConfigFile(configFile);
            	Initialize.setConfig(config);
            }
            
            if (regionFile.exists()) {
            	try {
            		regionConfig.load(regionFile);
            	} catch (Exception e) {
            		log.info("[DynamicEconomy] Error loading regions.yml");
            	}
            	log.info("[DynamicEconomy] Region database loaded.");
            } else {
            	try {
                   	regionConfig.save(regionFile);
                } catch (Exception e) {
            		log.info("[DynamicEconomy] IOException when creating Regions.yml in Main");
            		log.info(regionFile.toString());
            		e.printStackTrace();
            	}
            }
            
            if (loansFile.exists()) {
            	log.info("[DynamicEconomy] Loans database loaded.");
            } else {
            	try {
                   	loansFileConfig.save(loansFile);
                   	loansFileConfig.createSection("loans");
                } catch (Exception e) {
            		log.info("[DynamicEconomy] IOException when creating Loans.yml in Main");
            		log.info(loansFile.toString());
            		e.printStackTrace();
            	}
            }
            
            
            
            //this.getServer().getScheduler().scheduleSyncDelayedTask(this, new loan(this));
            
            
            
            relConfig();
            
            if (DynamicEconomy.useLoans) {
            	this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new loan(), 300L, loanCheckInterval);
            }
            
            if (DynamicEconomy.enableOverTimePriceDecay) {
            	long delay = DynamicEconomy.overTimePriceDecayPeriodCheck * 60 * 20;
            	this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Transaction(itemConfig,itemsFile), 300L, delay);
            }
            
            // End Initialize
            
            
            
            
            commandExec = new DynamicEconomyCommandExecutor(this,pdf,config,configFile);
            if (commandExec.setEconomy(economy,config)) {
            	log.info("[DynamicEconomy] CommandExec Economy set");
            }
            if (commandExec.setPermission(permission)) {
            	log.info("[DynamicEconomy] CommandExec Permissions set");
            }
            
            commandExec.setUpdater(updater);
            commandExec.fullver = fullVer;
            commandExec.subver = subVer;
            
            
            getCommand("setprice").setExecutor(commandExec);
            getCommand("setfloor").setExecutor(commandExec);
            getCommand("setceiling").setExecutor(commandExec);
            getCommand("getfloor").setExecutor(commandExec);
            getCommand("getceiling").setExecutor(commandExec);
            getCommand("getvelocity").setExecutor(commandExec);
            getCommand("setvelocity").setExecutor(commandExec);
            getCommand("dynamiceconomy").setExecutor(commandExec);
            getCommand("isstock").setExecutor(commandExec);
            getCommand("isboundary").setExecutor(commandExec);
            getCommand("addstock").setExecutor(commandExec);
            getCommand("dynamiceconomyreloadconfig").setExecutor(commandExec);
            getCommand("removestock").setExecutor(commandExec);
            getCommand("getdurability").setExecutor(commandExec);
            getCommand("dynecon").setExecutor(commandExec);
            getCommand("hasupdate").setExecutor(commandExec);
            getCommand("dyneconupdate").setExecutor(commandExec);
            getCommand("curtaxes").setExecutor(commandExec);
            getCommand("settax").setExecutor(commandExec);
            getCommand("shopregion").setExecutor(commandExec);
            getCommand("removeshopregion").setExecutor(commandExec);
            getCommand("expandreg").setExecutor(commandExec);
            getCommand("contractreg").setExecutor(commandExec);
            getCommand("shopregionwand").setExecutor(commandExec);
            getCommand("curregion").setExecutor(commandExec);
            getCommand("loan").setExecutor(commandExec);
            getCommand("curinterest").setExecutor(commandExec);
            getCommand("curloans").setExecutor(commandExec);
            
            if (altCommands) {
            	getCommand("debuy").setExecutor(commandExec);
                getCommand("desell").setExecutor(commandExec);
                getCommand("deprice").setExecutor(commandExec);
            } else {
            	getCommand("buy").setExecutor(commandExec);
                getCommand("sell").setExecutor(commandExec);
                getCommand("price").setExecutor(commandExec);
            }
            
            pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.High, this);
            
            
            
            
    }
    
    private Boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    private Boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
    
    public static void reloadConfigValues(Player player, String[] args){
    	
    	relConfig();
    	color.sendColouredMessage(player, "&2Configuration for DynamicEconomy reloaded");
    }
    
    
    
    public static void relConfig() {
    	
    	try { 	
        	config.load(configFile);
        } catch (Exception e) {
        	log.info("[DynamicEconomy] Error loading config.yml in reloadConfigValues() ");
        	log.info(e.toString());
        	e.printStackTrace();
        }
    	
    	usestock = config.getBoolean("Use-Stock",true);
        useboundaries = config.getBoolean("Use-boundaries",true);
        prefix = config.getString("prefix", "");
        defaultAmount = config.getInt("default-amount",1);
        localNotify = config.getBoolean("local-price-notify",true);
        globalNotify = config.getBoolean("global-price-notify",true);
        logwriting = config.getBoolean("log-writing",true);
        salestax = config.getDouble("salestax",0.0);
        purchasetax = config.getDouble("purchasetax",0.0);
        depositTax = config.getBoolean("deposit-tax-to-account",false);
        taxAccount = config.getString("account-name","");
        location_restrict = config.getBoolean("location-restrict",false);
        minimum_y = config.getInt("minimum-y",0);
        maximum_y = config.getInt("maximum-y",0);
        altCommands = config.getBoolean("alt-commands",false);
        useRegions = config.getBoolean("use-regions",false);
        useLoans = config.getBoolean("use-loans",true);
        useStaticInterest = config.getBoolean("use-static-interest",false);
        dynamicCompressionRate = config.getDouble("dynamic-compression-rate",0.0);
        
        if (useStaticInterest) {
        	interestRate = config.getDouble("interest-rate",0.05);
        } else {
        	interestRate = config.getDouble("dynamic-interest-rate",0.0);
        }
        
        if (interestRate == 0.0) {
        	
        	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

        	    public void run() {
        	    	loan.dynamicInterest(true);
        	    }
        	}, 100L);
        }
        
        paybackTime = config.getInt("payback-time",20);
        maxLoans = config.getInt("max-num-loans",1);
        maxLoanAmount = config.getDouble("max-loan-amount",500);
        minLoanAmount = config.getInt("min-loan-amount",10);
        useLoanAccount = config.getBoolean("use-loan-account",false);
        loanAccountName = config.getString("loan-account-name","");
        loanCheckInterval = config.getLong("loan-check-interval",300);
        enableUpdateChecker = config.getBoolean("enable-update-checker",true);
        bannedSaleItems = config.getString("banned-sale-items","").split(",");
        bannedPurchaseItems = config.getString("banned-purchase-items","").split(",");
        enableOverTimePriceDecay = config.getBoolean("enable-over-time-price-decay",true);
        overTimePriceDecayPercent = config.getDouble("over-time-price-decay-percent",.1);
        overTimePriceDecayPeriod = config.getLong("over-time-price-decay-period",1440);
        overTimePriceDecayPeriodCheck = config.getLong("over-time-price-decay-period-check",15);
        
        
    }
        
    @Override
    public void onDisable() {
    	log.info(name +" v" + version + " disabled!");
    	try {
    		Utility.out.close();
    		Utility.bf.close();
    		selectedCorners.clear();
    		regionConfig.save(regionFile);
    		loansFileConfig.save(loansFile);
    		itemConfig.save(itemsFile);
    		getServer().getScheduler().cancelTasks(this);
    	} catch (Exception e) {
    		log.info("[DynamicEconomy] Exception when disabling log writer");
    	}
    	
    }
    
}
