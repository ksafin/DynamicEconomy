package me.ksafin.DynamicEconomy;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
	
	public static String noPermission;
	public static String invalidCommandArgs;
	public static String wrongWorld;
	public static String belowMinY;
	public static String aboveMaxY;
	public static String loansDisabled;
	public static String stockOn;
	public static String stockOff;
	public static String boundariesOn;
	public static String boundariesOff;
	public static String itemDoesntExist;
	public static String itemHasNoDurability;
	public static String noArmorEquipped;
	public static String noHelmetEquipped;
	public static String noChestplateEquipped;
	public static String noLeggingsEquipped;
	public static String noBootsEquipped;
	public static String bannedItem;
	public static String cannotBuyAll;
	public static String negativeBuyAmount;
	public static String negativeSellAmount;
	public static String notEnoughMoney;
	public static String notEnoughStock;
	public static String purchaseSuccess;
	public static String notWithinRegion;
	public static String saleSuccess;
	public static String stockAdded;
	public static String stockRemoved;
	public static String noRegionSelected;
	public static String regionExpanded;
	public static String regionContracted;
	public static String maxLoans;
	public static String loanAccountNotFound;
	

	
	public static void getMessages() {
		FileConfiguration config = DynamicEconomy.messagesConfig;
		
		noPermission = config.getString("no-permission", "&2You do not have permission to use this command.");
		invalidCommandArgs = config.getString("invalid-command-args","&2You entered the command arguments in the wrong order, or your amount was invalid. Try again. ");
		wrongWorld = config.getString("wrong-world","&2This world does not have access to this command.");
		belowMinY = config.getString("below-min-y","&2You are too deep underground to access the economy!");
		aboveMaxY = config.getString("above-max-y","&2You are too high up to access the economy!");
		loansDisabled = config.getString("loans-disabled","&2The Bank is not available for loans at this time.");
		stockOn = config.getString("stock-on","&2Stock is turned on.");
		stockOff = config.getString("stock-off","&2Stock is turned off.");
		boundariesOn = config.getString("boundaries-on","&2Boundaries are turned on.");
		boundariesOff = config.getString("boundaries-off","&2Boundaries are turned off.");
		itemDoesntExist = config.getString("item-doesnt-exist","&2This item does not exist. ");
		itemHasNoDurability = config.getString("item-has-no-durability","&2This item has no durability.");
		noArmorEquipped = config.getString("no-armor-equipped","&2You do not have any armor equipped");
		noHelmetEquipped = config.getString("no-helmet-equipped","&2You do not have a &fhelmet &2equipped");
		noChestplateEquipped = config.getString("no-chestplate-equipped","&2You do not have a &fchestplate &2equipped");
		noLeggingsEquipped = config.getString("no-leggings-equipped","&2You do not have &fleggings &2equipped");
		noBootsEquipped = config.getString("no-boots-equipped","&2You do not have &fboots &2equipped");
		bannedItem = config.getString("banned-item","&2This item is banned, and not allowed to be purchased in the economy.");
		cannotBuyAll = config.getString("cannot-buy-all","&2Stock Disabled, cannot use keyword 'all' ");
		negativeBuyAmount = config.getString("negative-buy-amount","&2This amount is invalid.");
		negativeSellAmount = config.getString("negative-sell-amount","&2This amount is invalid.");
		notEnoughMoney = config.getString("not-enough-money","&2You do not have enough money to purchase this.");
		notEnoughStock = config.getString("not-enough-stock","&2There is not enough stock of this item.");
		purchaseSuccess = config.getString("purchase-success","&2Purchase Success!");
	    notWithinRegion = config.getString("not-within-region","&2You are not within an economy region!");
	    saleSuccess = config.getString("sale-success","&2Sale Success!");
	    stockAdded = config.getString("stock-added","&2Stock succesfully added.");
	    stockRemoved = config.getString("stock-removed","&2Stock succesfully removed.");
	    noRegionSelected = config.getString("no-region-selected","&2No region selected!");
	    regionExpanded = config.getString("region-expanded","&2Region expanded!");
	    regionContracted = config.getString("region-contracted","&2Region contracted!");
	    maxLoans = config.getString("max-loans","&2You have the maximum number of loans allowed.");
	    loanAccountNotFound = config.getString("loan-account-not-found","&2The Bank is not available, contact your server admin");
	}
	
	
	
}
