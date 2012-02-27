package me.ksafin.DynamicEconomy;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Initialize {
	
	static Logger log = Logger.getLogger("Minecraft");
	private static FileConfiguration config;
	private static File configFile;
	
	private File itemsFile;
	private FileConfiguration conf;
	
	public static void setConfig(FileConfiguration conf) {
		
		config = conf;
		
		config.set("Use-Stock", true);
		config.set("Use-boundaries",true);
		config.set("prefix","");
		config.set("default-amount",1);
		config.set("local-price-notify",true);
		config.set("global-price-notify",true);
		config.set("log-writing",true);
		config.set("salestax",0.0);
		config.set("purchasetax",0.0);
	    config.set("deposit-tax-to-account",false);
	    config.set("account-name","");
	    config.set("location-restrict",false);
	    config.set("minimum-y",0);
	    config.set("maximum-y",128);
	    config.set("alt-commands",false);
	    config.set("enable-update-checker",true);
	    config.set("use-regions",false);
	    config.set("use-loans",true);
	    config.set("use-static-interest",false);
	    config.set("interest-rate",0.05);
	    config.set("dynamic-interest-rate",0.0);
	    config.set("dynamic-compression-rate",0.0);
	    config.set("payback-time",20);
	    config.set("max-num-loans",1);
	    config.set("max-loan-amount",500);
	    config.set("min-loan-amount",10);
	    config.set("use-loan-account",false);
	    config.set("loan-account-name","");
	    config.set("loan-check-interval",300);
	    config.set("banned-sale-items","");
	    config.set("banned-purchase-items","");
	    config.set("enable-over-time-price-decay",true);
	    config.set("over-time-price-decay-percent",.1);
	    config.set("over-time-price-decay-period",1440);
	    config.set("over-time-price-decay-period-check",60);
	    
		
		try {
			config.save(configFile);
		} catch (Exception e) {
			log.info("[DynamicEconomy] Error saving config");
			log.info(e.toString());
			e.printStackTrace();
		}
	}
	
	public void setItems(DynamicEconomy plugin) {
		//File itemsFile = new File(plugin.getDataFolder(), "Items.yml");
		//FileConfiguration conf = YamlConfiguration.loadConfiguration(itemsFile);
		
		
		conf.set("STONE.velocity", .01);
		conf.set("STONE.id", 1);
		conf.set("STONE.price", .5);
		conf.set("STONE.floor", .01);
		conf.set("STONE.ceiling", 1);
		conf.set("STONE.description", "A Smooth Stone");
		conf.set("STONE.stock",50);
		conf.set("STONE.spread", .01);
		conf.set("STONE.time",0);

		conf.set("DIRT.velocity", .001);
		conf.set("DIRT.id", 3);
		conf.set("DIRT.price", .02);
		conf.set("DIRT.floor", .001);
		conf.set("DIRT.ceiling", .3);
		conf.set("DIRT.description", "Dirt dug from the crust");
		conf.set("DIRT.stock",50);
		conf.set("DIRT.spread", .001);
		conf.set("DIRT.time",0);

		conf.set("COBBLESTONE.velocity", .01);
		conf.set("COBBLESTONE.id", 4);
		conf.set("COBBLESTONE.price", .1);
		conf.set("COBBLESTONE.floor", .01);
		conf.set("COBBLESTONE.ceiling", .5);
		conf.set("COBBLESTONE.description", "Un-refined rock");
		conf.set("COBBLESTONE.stock",50);
		conf.set("COBBLESTONE.spread", .01);
		conf.set("COBBLESTONE.time",0);

		conf.set("PLANK.velocity", .01);
		conf.set("PLANK.id", 5);
		conf.set("PLANK.price", .2);
		conf.set("PLANK.floor", .01);
		conf.set("PLANK.ceiling", .4);
		conf.set("PLANK.description", "Processed wood");
		conf.set("PLANK.stock",50);
		conf.set("PLANK.spread", .01);
		conf.set("PLANK.time",0);

		conf.set("SAPLING.velocity", .01);
		conf.set("SAPLING.id", 6);
		conf.set("SAPLING.price", .2);
		conf.set("SAPLING.floor", .01);
		conf.set("SAPLING.ceiling", .45);
		conf.set("SAPLING.description", "A small plant");
		conf.set("SAPLING.stock",50);
		conf.set("SAPLING.spread", .01);
		conf.set("SAPLING.time",0);

		conf.set("SAND.velocity", .001);
		conf.set("SAND.id", 12);
		conf.set("SAND.price", .04);
		conf.set("SAND.floor", .001);
		conf.set("SAND.ceiling", .3);
		conf.set("SAND.description", "Found on beaches!");
		conf.set("SAND.stock",50);
		conf.set("SAND.spread", .001);
		conf.set("SAND.time",0);

		conf.set("GRAVEL.velocity", .001);
		conf.set("GRAVEL.id", 13);
		conf.set("GRAVEL.price", .04);
		conf.set("GRAVEL.floor", .001);
		conf.set("GRAVEL.ceiling", .3);
		conf.set("GRAVEL.description", "Useless cave slush");
		conf.set("GRAVEL.stock",50);
		conf.set("GRAVEL.spread", .001);
		conf.set("GRAVEL.time",0);
		
		conf.set("GOLDORE.velocity", .19);
		conf.set("GOLDORE.id", 14);
		conf.set("GOLDORE.price", 300);
		conf.set("GOLDORE.floor", 250);
		conf.set("GOLDORE.ceiling", 400);
		conf.set("GOLDORE.description", "Prettiest metal ever!");
		conf.set("GOLDORE.stock",50);
		conf.set("GOLDORE.spread", .19);
		conf.set("GOLDORE.time",0);
		
		conf.set("IRONORE.velocity", .12);
		conf.set("IRONORE.id", 15);
		conf.set("IRONORE.price", 50);
		conf.set("IRONORE.floor", 30);
		conf.set("IRONORE.ceiling", 80);
		conf.set("IRONORE.description", "Tough as steel!");
		conf.set("IRONORE.stock",50);
		conf.set("IRONORE.spread", .12);
		conf.set("IRONORE.time",0);
		
		conf.set("COALORE.velocity", .09);
		conf.set("COALORE.id", 16);
		conf.set("COALORE.price", 10);
		conf.set("COALORE.floor", 1);
		conf.set("COALORE.ceiling", 30);
		conf.set("COALORE.description", "Good for burning!");
		conf.set("COALORE.stock",50);
		conf.set("COALORE.spread", .09);
		conf.set("COALORE.time",0);

		conf.set("WOOD.velocity", .05);
		conf.set("WOOD.id", 17);
		conf.set("WOOD.price", .8);
		conf.set("WOOD.floor", .1);
		conf.set("WOOD.ceiling", 1);
		conf.set("WOOD.description", "Part of a tree!");
		conf.set("WOOD.stock",50);
		conf.set("WOOD.spread", .05);
		conf.set("WOOD.time",0);
		
		conf.set("BIRCHWOOD.velocity", .05);
		conf.set("BIRCHWOOD.id", 17001);
		conf.set("BIRCHWOOD.price", .8);
		conf.set("BIRCHWOOD.floor", .1);
		conf.set("BIRCHWOOD.ceiling", 1);
		conf.set("BIRCHWOOD.description", "Part of a tree!");
		conf.set("BIRCHWOOD.stock",50);
		conf.set("BIRCHWOOD.spread", .05);
		conf.set("BIRCHWOOD.time",0);
		
		conf.set("PINEWOOD.velocity", .05);
		conf.set("PINEWOOD.id", 17002);
		conf.set("PINEWOOD.price", .8);
		conf.set("PINEWOOD.floor", .1);
		conf.set("PINEWOOD.ceiling", 1);
		conf.set("PINEWOOD.description", "Part of a tree!");
		conf.set("PINEWOOD.stock",50);
		conf.set("PINEWOOD.spread", .05);
		conf.set("PINEWOOD.time",0);
		
		conf.set("JUNGLEWOOD.velocity", .05);
		conf.set("JUNGLEWOOD.id", 17003);
		conf.set("JUNGLEWOOD.price", .8);
		conf.set("JUNGLEWOOD.floor", .1);
		conf.set("JUNGLEWOOD.ceiling", 1);
		conf.set("JUNGLEWOOD.description", "Part of a tree!");
		conf.set("JUNGLEWOOD.stock",50);
		conf.set("JUNGLEWOOD.spread", .05);
		conf.set("JUNGLEWOOD.time",0);

		conf.set("GLASS.velocity", .02);
		conf.set("GLASS.id", 20);
		conf.set("GLASS.price", .35);
		conf.set("GLASS.floor", .2);
		conf.set("GLASS.ceiling", .5);
		conf.set("GLASS.description", "Clear as day.");
		conf.set("GLASS.stock",50);
		conf.set("GLASS.spread", .02);
		conf.set("GLASS.time",0);
		
		conf.set("LAPISLAZULIORE.velocity", .9);
		conf.set("LAPISLAZULIORE.id", 21);
		conf.set("LAPISLAZULIORE.price", 25);
		conf.set("LAPISLAZULIORE.floor", 10);
		conf.set("LAPISLAZULIORE.ceiling", 50);
		conf.set("LAPISLAZULIORE.description", "Prettiest ore in the world");
		conf.set("LAPISLAZULIORE.stock",50);
		conf.set("LAPISLAZULIORE.spread", .8);
		conf.set("LAPISLAZULIORE.time",0);
		
		conf.set("DISPENSER.velocity", .15);
		conf.set("DISPENSER.id", 23);
		conf.set("DISPENSER.price", 2.5);
		conf.set("DISPENSER.floor", 1);
		conf.set("DISPENSER.ceiling", 5);
		conf.set("DISPENSER.description", "Dispenses stuff!");
		conf.set("DISPENSER.stock",50);
		conf.set("DISPENSER.spread", .15);
		conf.set("DISPENSER.time",0);

		conf.set("SANDSTONE.velocity", .01);
		conf.set("SANDSTONE.id", 24);
		conf.set("SANDSTONE.price", .15);
		conf.set("SANDSTONE.floor", .1);
		conf.set("SANDSTONE.ceiling", .5);
		conf.set("SANDSTONE.description", "Looks like sand, feels like stone!");
		conf.set("SANDSTONE.stock",50);
		conf.set("SANDSTONE.spread", .01);
		conf.set("SANDSTONE.time",0);

		conf.set("NOTEBLOCK.velocity", .08);
		conf.set("NOTEBLOCK.id", 25);
		conf.set("NOTEBLOCK.price", 1.5);
		conf.set("NOTEBLOCK.floor", .75);
		conf.set("NOTEBLOCK.ceiling", 5);
		conf.set("NOTEBLOCK.description", "Plays musical notes");
		conf.set("NOTEBLOCK.stock",50);
		conf.set("NOTEBLOCK.spread", .08);
		conf.set("NOTEBLOCK.time",0);

		conf.set("BED.velocity", .1);
		conf.set("BED.id", 355);
		conf.set("BED.price", 2.5);
		conf.set("BED.floor", .5);
		conf.set("BED.ceiling", 5);
		conf.set("BED.description", "Useful for sleeping!");
		conf.set("BED.stock",50);
		conf.set("BED.spread", .1);
		conf.set("BED.time",0);

		conf.set("POWEREDRAIL.velocity", 1.2);
		conf.set("POWEREDRAIL.id", 27);
		conf.set("POWEREDRAIL.price",  301);
		conf.set("POWEREDRAIL.floor", 275);
		conf.set("POWEREDRAIL.ceiling", 325);
		conf.set("POWEREDRAIL.description", "A rail that can power minecarts");
		conf.set("POWEREDRAIL.stock",50);
		conf.set("POWEREDRAIL.spread", 1.2);
		conf.set("POWEREDRAIL.time",0);

		conf.set("DETECTORRAIL.velocity", .5);
		conf.set("DETECTORRAIL.id", 28);
		conf.set("DETECTORRAIL.price", 54);
		conf.set("DETECTORRAIL.floor", 45);
		conf.set("DETECTORRAIL.ceiling", 74);
		conf.set("DETECTORRAIL.description", "Provides power!");
		conf.set("DETECTORRAIL.stock",50);
		conf.set("DETECTORRAIL.spread", .5);
		conf.set("DETECTORRAIL.time",0);

		conf.set("STICKYPISTON.velocity", .8);
		conf.set("STICKYPISTON.id", 29);
		conf.set("STICKYPISTON.price", 120);
		conf.set("STICKYPISTON.floor", 100);
		conf.set("STICKYPISTON.ceiling", 145);
		conf.set("STICKYPISTON.description", "Grabs and moves blocks!");
		conf.set("STICKYPISTON.stock",50);
		conf.set("STICKYPISTON.spread", .8);
		conf.set("STICKYPISTON.time",0);

		conf.set("PISTON.velocity", .75);
		conf.set("PISTON.id", 33);
		conf.set("PISTON.price", 53.2);
		conf.set("PISTON.floor", 50);
		conf.set("PISTON.ceiling", 60);
		conf.set("PISTON.description", "Moves stuff!");
		conf.set("PISTON.stock",50);
		conf.set("PISTON.spread", .75);
		conf.set("PISTON.time",0);

		conf.set("WOOL.velocity", .05);
		conf.set("WOOL.id", 35);
		conf.set("WOOL.price", 1);
		conf.set("WOOL.floor", .1);
		conf.set("WOOL.ceiling", 2);
		conf.set("WOOL.description", "Found on sheep & dye-able!");
		conf.set("WOOL.stock",50);
		conf.set("WOOL.spread", .05);
		conf.set("WOOL.time",0);
		
		// COLORED WOOLS
		
		conf.set("ORANGEWOOL.velocity", .05);
		conf.set("ORANGEWOOL.id", 35001);
		conf.set("ORANGEWOOL.price", 1);
		conf.set("ORANGEWOOL.floor", .1);
		conf.set("ORANGEWOOL.ceiling", 2);
		conf.set("ORANGEWOOL.description", "Found on sheep & dye-able!");
		conf.set("ORANGEWOOL.stock",50);
		conf.set("ORANGEWOOL.spread", .05);
		conf.set("ORANGEWOOL.time",0);
		
		conf.set("MAGENTAWOOL.velocity", .05);
		conf.set("MAGENTAWOOL.id", 35002);
		conf.set("MAGENTAWOOL.price", 1);
		conf.set("MAGENTAWOOL.floor", .1);
		conf.set("MAGENTAWOOL.ceiling", 2);
		conf.set("MAGENTAWOOL.description", "Found on sheep & dye-able!");
		conf.set("MAGENTAWOOL.stock",50);
		conf.set("MAGENTAWOOL.spread", .05);
		conf.set("MAGENTAWOOL.time",0);
		
		conf.set("LIGHTBLUEWOOL.velocity", .05);
		conf.set("LIGHTBLUEWOOL.id", 35003);
		conf.set("LIGHTBLUEWOOL.price", 1);
		conf.set("LIGHTBLUEWOOL.floor", .1);
		conf.set("LIGHTBLUEWOOL.ceiling", 2);
		conf.set("LIGHTBLUEWOOL.description", "Found on sheep & dye-able!");
		conf.set("LIGHTBLUEWOOL.stock",50);
		conf.set("LIGHTBLUEWOOL.spread", .05);
		conf.set("LIGHTBLUEWOOL.time",0);
		
		conf.set("YELLOWWOOL.velocity", .05);
		conf.set("YELLOWWOOL.id", 35004);
		conf.set("YELLOWWOOL.price", 1);
		conf.set("YELLOWWOOL.floor", .1);
		conf.set("YELLOWWOOL.ceiling", 2);
		conf.set("YELLOWWOOL.description", "Found on sheep & dye-able!");
		conf.set("YELLOWWOOL.stock",50);
		conf.set("YELLOWWOOL.spread", .05);
		conf.set("YELLOWWOOL.time",0);
		
		conf.set("LIMEWOOL.velocity", .05);
		conf.set("LIMEWOOL.id", 35005);
		conf.set("LIMEWOOL.price", 1);
		conf.set("LIMEWOOL.floor", .1);
		conf.set("LIMEWOOL.ceiling", 2);
		conf.set("LIMEWOOL.description", "Found on sheep & dye-able!");
		conf.set("LIMEWOOL.stock",50);
		conf.set("LIMEWOOL.spread", .05);
		conf.set("LIMEWOOL.time",0);
		
		conf.set("PINKWOOL.velocity", .05);
		conf.set("PINKWOOL.id", 35006);
		conf.set("PINKWOOL.price", 1);
		conf.set("PINKWOOL.floor", .1);
		conf.set("PINKWOOL.ceiling", 2);
		conf.set("PINKWOOL.description", "Found on sheep & dye-able!");
		conf.set("PINKWOOL.stock",50);
		conf.set("PINKWOOL.spread", .05);
		conf.set("PINKWOOL.time",0);
		
		conf.set("GRAYWOOL.velocity", .05);
		conf.set("GRAYWOOL.id", 35007);
		conf.set("GRAYWOOL.price", 1);
		conf.set("GRAYWOOL.floor", .1);
		conf.set("GRAYWOOL.ceiling", 2);
		conf.set("GRAYWOOL.description", "Found on sheep & dye-able!");
		conf.set("GRAYWOOL.stock",50);
		conf.set("GRAYWOOL.spread", .05);
		conf.set("GRAYWOOL.time",0);
		
		conf.set("LIGHTGRAYWOOL.velocity", .05);
		conf.set("LIGHTGRAYWOOL.id", 35008);
		conf.set("LIGHTGRAYWOOL.price", 1);
		conf.set("LIGHTGRAYWOOL.floor", .1);
		conf.set("LIGHTGRAYWOOL.ceiling", 2);
		conf.set("LIGHTGRAYWOOL.description", "Found on sheep & dye-able!");
		conf.set("LIGHTGRAYWOOL.stock",50);
		conf.set("LIGHTGRAYWOOL.spread", .05);
		conf.set("LIGHTGRAYWOOL.time",0);
		
		conf.set("CYANWOOL.velocity", .05);
		conf.set("CYANWOOL.id", 35009);
		conf.set("CYANWOOL.price", 1);
		conf.set("CYANWOOL.floor", .1);
		conf.set("CYANWOOL.ceiling", 2);
		conf.set("CYANWOOL.description", "Found on sheep & dye-able!");
		conf.set("CYANWOOL.stock",50);
		conf.set("CYANWOOL.spread", .05);
		conf.set("CYANWOOL.time",0);
		
		conf.set("PURPLEWOOL.velocity", .05);
		conf.set("PURPLEWOOL.id", 350010);
		conf.set("PURPLEWOOL.price", 1);
		conf.set("PURPLEWOOL.floor", .1);
		conf.set("PURPLEWOOL.ceiling", 2);
		conf.set("PURPLEWOOL.description", "Found on sheep & dye-able!");
		conf.set("PURPLEWOOL.stock",50);
		conf.set("PURPLEWOOL.spread", .05);
		conf.set("PURPLEWOOL.time",0);
		
		conf.set("BLUEWOOL.velocity", .05);
		conf.set("BLUEWOOL.id", 350011);
		conf.set("BLUEWOOL.price", 1);
		conf.set("BLUEWOOL.floor", .1);
		conf.set("BLUEWOOL.ceiling", 2);
		conf.set("BLUEWOOL.description", "Found on sheep & dye-able!");
		conf.set("BLUEWOOL.stock",50);
		conf.set("BLUEWOOL.spread", .05);
		conf.set("BLUEWOOL.time",0);
		
		conf.set("BROWNWOOL.velocity", .05);
		conf.set("BROWNWOOL.id", 350012);
		conf.set("BROWNWOOL.price", 1);
		conf.set("BROWNWOOL.floor", .1);
		conf.set("BROWNWOOL.ceiling", 2);
		conf.set("BROWNWOOL.description", "Found on sheep & dye-able!");
		conf.set("BROWNWOOL.stock",50);
		conf.set("BROWNWOOL.spread", .05);
		conf.set("BROWNWOOL.time",0);
		
		conf.set("GREENWOOL.velocity", .05);
		conf.set("GREENWOOL.id", 350013);
		conf.set("GREENWOOL.price", 1);
		conf.set("GREENWOOL.floor", .1);
		conf.set("GREENWOOL.ceiling", 2);
		conf.set("GREENWOOL.description", "Found on sheep & dye-able!");
		conf.set("GREENWOOL.stock",50);
		conf.set("GREENWOOL.spread", .05);
		conf.set("GREENWOOL.time",0);
		
		conf.set("REDWOOL.velocity", .05);
		conf.set("REDWOOL.id", 350014);
		conf.set("REDWOOL.price", 1);
		conf.set("REDWOOL.floor", .1);
		conf.set("REDWOOL.ceiling", 2);
		conf.set("REDWOOL.description", "Found on sheep & dye-able!");
		conf.set("REDWOOL.stock",50);
		conf.set("REDWOOL.spread", .05);
		conf.set("REDWOOL.time",0);
		
		conf.set("BLACKWOOL.velocity", .05);
		conf.set("BLACKWOOL.id", 350015);
		conf.set("BLACKWOOL.price", 1);
		conf.set("BLACKWOOL.floor", .1);
		conf.set("BLACKWOOL.ceiling", 2);
		conf.set("BLACKWOOL.description", "Found on sheep & dye-able!");
		conf.set("BLACKWOOL.stock",50);
		conf.set("BLACKWOOL.spread", .05);
		conf.set("BLACKWOOL.time",0);
		
		// END COLORED WOOLS
		
		conf.set("DANDELION.velocity", .8);
		conf.set("DANDELION.id", 37);
		conf.set("DANDELION.price", 1.3);
		conf.set("DANDELION.floor", .3);
		conf.set("DANDELION.ceiling", 2);
		conf.set("DANDELION.description", "A pretty yellow flower!");
		conf.set("DANDELION.stock",50);
		conf.set("DANDELION.spread", .8);
		conf.set("DANDELION.time",0);

		conf.set("ROSE.velocity", .8);
		conf.set("ROSE.id", 38);
		conf.set("ROSE.price", 1.3);
		conf.set("ROSE.floor", .3);
		conf.set("ROSE.ceiling", 2);
		conf.set("ROSE.description", "A pretty red flower!");
		conf.set("ROSE.stock",50);
		conf.set("ROSE.spread", .8);
		conf.set("ROSE.time",0);

		conf.set("BROWNMUSHROOM.velocity", .6);
		conf.set("BROWNMUSHROOM.id", 39);
		conf.set("BROWNMUSHROOM.price", .9);
		conf.set("BROWNMUSHROOM.floor", .05);
		conf.set("BROWNMUSHROOM.ceiling", 2);
		conf.set("BROWNMUSHROOM.description", "Tasty.. or not?");
		conf.set("BROWNMUSHROOM.stock",50);
		conf.set("BROWNMUSHROOM.spread", .6);
		conf.set("BROWNMUSHROOM.time",0);

		conf.set("REDMUSHROOM.velocity", .6);
		conf.set("REDMUSHROOM.id", 40);
		conf.set("REDMUSHROOM.price", .9);
		conf.set("REDMUSHROOM.floor", .05);
		conf.set("REDMUSHROOM.ceiling", 2);
		conf.set("REDMUSHROOM.description", "Tasty.. or not?");
		conf.set("REDMUSHROOM.stock",50);
		conf.set("REDMUSHROOM.spread", .8);
		conf.set("REDMUSHROOM.time",0);

		conf.set("GOLDBLOCK.velocity", 1);
		conf.set("GOLDBLOCK.id", 41);
		conf.set("GOLDBLOCK.price", 2700);
		conf.set("GOLDBLOCK.floor", 2400);
		conf.set("GOLDBLOCK.ceiling", 3000);
		conf.set("GOLDBLOCK.description", "A block of solid gold!");
		conf.set("GOLDBLOCK.stock",50);
		conf.set("GOLDBLOCK.spread", 1);
		conf.set("GOLDBLOCK.time",0);

		conf.set("IRONBLOCK.velocity", 1);
		conf.set("IRONBLOCK.id", 42);
		conf.set("IRONBLOCK.price", 450);
		conf.set("IRONBLOCK.floor", 400);
		conf.set("IRONBLOCK.ceiling", 500);
		conf.set("IRONBLOCK.description", "A block of solid Iron!!");
		conf.set("IRONBLOCK.stock",50);
		conf.set("IRONBLOCK.spread", 1);
		conf.set("IRONBLOCK.time",0);

		conf.set("DOUBLESLABS.velocity", .03);
		conf.set("DOUBLESLABS.id", 43);
		conf.set("DOUBLESLABS.price", 2);
		conf.set("DOUBLESLABS.floor", 1);
		conf.set("DOUBLESLABS.ceiling", 3);
		conf.set("DOUBLESLABS.description", "Very sophisticated!");
		conf.set("DOUBLESLABS.stock",50);
		conf.set("DOUBLESLABS.spread", .03);
		conf.set("DOUBLESLABS.time",0);

		// BEGIN SLABS
		
		conf.set("SLABS.velocity", .03);
		conf.set("SLABS.id", 44);
		conf.set("SLABS.price", 1);
		conf.set("SLABS.floor", .01);
		conf.set("SLABS.ceiling", 2);
		conf.set("SLABS.description", "Half as sophisticated!");
		conf.set("SLABS.stock",50);
		conf.set("SLABS.spread", .03);
		conf.set("SLABS.time",0);
		
		conf.set("SANDSTONESLABS.velocity", .03);
		conf.set("SANDSTONESLABS.id", 44001);
		conf.set("SANDSTONESLABS.price", 1);
		conf.set("SANDSTONESLABS.floor", .01);
		conf.set("SANDSTONESLABS.ceiling", 2);
		conf.set("SANDSTONESLABS.description", "Half as sophisticated!");
		conf.set("SANDSTONESLABS.stock",50);
		conf.set("SANDSTONESLABS.spread", .03);
		conf.set("SANDSTONESLABS.time",0);
		
		conf.set("PLANKSLABS.velocity", .03);
		conf.set("PLANKSLABS.id", 44002);
		conf.set("PLANKSLABS.price", 1);
		conf.set("PLANKSLABS.floor", .01);
		conf.set("PLANKSLABS.ceiling", 2);
		conf.set("PLANKSLABS.description", "Half as sophisticated!");
		conf.set("PLANKSLABS.stock",50);
		conf.set("PLANKSLABS.spread", .03);
		conf.set("PLANKSLABS.time",0);
		
		conf.set("COBBLESLABS.velocity", .03);
		conf.set("COBBLESLABS.id", 44003);
		conf.set("COBBLESLABS.price", 1);
		conf.set("COBBLESLABS.floor", .01);
		conf.set("COBBLESLABS.ceiling", 2);
		conf.set("COBBLESLABS.description", "Half as sophisticated!");
		conf.set("COBBLESLABS.stock",50);
		conf.set("COBBLESLABS.spread", .03);
		conf.set("COBBLESLABS.time",0);
		
		conf.set("BRICKSLABS.velocity", .03);
		conf.set("BRICKSLABS.id", 44004);
		conf.set("BRICKSLABS.price", 1);
		conf.set("BRICKSLABS.floor", .01);
		conf.set("BRICKSLABS.ceiling", 2);
		conf.set("BRICKSLABS.description", "Half as sophisticated!");
		conf.set("BRICKSLABS.stock",50);
		conf.set("BRICKSLABS.spread", .03);
		conf.set("BRICKSLABS.time",0);
		
		conf.set("STONEBRICKSLABS.velocity", .03);
		conf.set("STONEBRICKSLABS.id", 44005);
		conf.set("STONEBRICKSLABS.price", 1);
		conf.set("STONEBRICKSLABS.floor", .01);
		conf.set("STONEBRICKSLABS.ceiling", 2);
		conf.set("STONEBRICKSLABS.description", "Half as sophisticated!");
		conf.set("STONEBRICKSLABS.stock",50);
		conf.set("STONEBRICKSLABS.spread", .03);
		conf.set("STONEBRICKSLABS.time",0);
		
		// END SLABS

		conf.set("BRICKBLOCK.velocity", .8);
		conf.set("BRICKBLOCK.id", 45);
		conf.set("BRICKBLOCK.price", 20);
		conf.set("BRICKBLOCK.floor", 15);
		conf.set("BRICKBLOCK.ceiling", 25);
		conf.set("BRICKBLOCK.description", "Reminds me of Brooklyn!");
		conf.set("BRICKBLOCK.stock",50);
		conf.set("BRICKBLOCK.spread", .8);
		conf.set("BRICKBLOCK.time",0);

		conf.set("TNT.velocity", .7);
		conf.set("TNT.id", 46);
		conf.set("TNT.price", 60);
		conf.set("TNT.floor", 49);
		conf.set("TNT.ceiling", 73);
		conf.set("TNT.description", "Blows everything up!");
		conf.set("TNT.stock",50);
		conf.set("TNT.spread", .7);
		conf.set("TNT.time",0);

		conf.set("BOOKSHELF.velocity", .4);
		conf.set("BOOKSHELF.id", 47);
		conf.set("BOOKSHELF.price", 15.6);
		conf.set("BOOKSHELF.floor", 7);
		conf.set("BOOKSHELF.ceiling", 24);
		conf.set("BOOKSHELF.description", "Found in the homes of the sophisticated and educated!");
		conf.set("BOOKSHELF.stock",50);
		conf.set("BOOKSHELF.spread", .4);
		conf.set("BOOKSHELF.time",0);

		conf.set("MOSSYCOBBLE.velocity", 1.3);
		conf.set("MOSSYCOBBLE.id", 48);
		conf.set("MOSSYCOBBLE.price", 175);
		conf.set("MOSSYCOBBLE.floor", 150);
		conf.set("MOSSYCOBBLE.ceiling", 250);
		conf.set("MOSSYCOBBLE.description", "Found in Dungeons!");
		conf.set("MOSSYCOBBLE.stock",50);
		conf.set("MOSSYCOBBLE.spread", 1.3);
		conf.set("MOSSYCOBBLE.time",0);

		conf.set("OBSIDIAN.velocity", 1.1);
		conf.set("OBSIDIAN.id", 49);
		conf.set("OBSIDIAN.price", 100);
		conf.set("OBSIDIAN.floor", 80);
		conf.set("OBSIDIAN.ceiling", 160);
		conf.set("OBSIDIAN.description", "Hardened Lava!");
		conf.set("OBSIDIAN.stock",50);
		conf.set("OBSIDIAN.spread", 1.1);
		conf.set("OBSIDIAN.time",0);

		conf.set("TORCH.velocity", .01);
		conf.set("TORCH.id", 50);
		conf.set("TORCH.price", .4);
		conf.set("TORCH.floor", .2);
		conf.set("TORCH.ceiling", 1);
		conf.set("TORCH.description", "Cheapest light source!");
		conf.set("TORCH.stock",50);
		conf.set("TORCH.spread", .01);
		conf.set("TORCH.time",0);
		
		conf.set("WOODENSTAIRS.velocity", .2);
		conf.set("WOODENSTAIRS.id", 53);
		conf.set("WOODENSTAIRS.price", 3);
		conf.set("WOODENSTAIRS.floor", 1.2);
		conf.set("WOODENSTAIRS.ceiling", 4.9);
		conf.set("WOODENSTAIRS.description", "Get up high with wooden stairs!");
		conf.set("WOODENSTAIRS.stock",50);
		conf.set("WOODENSTAIRS.spread", .2);
		conf.set("WOODENSTAIRS.time",0);

		conf.set("CHEST.velocity", .2);
		conf.set("CHEST.id", 54);
		conf.set("CHEST.price", 5);
		conf.set("CHEST.floor", 2);
		conf.set("CHEST.ceiling", 10);
		conf.set("CHEST.description", "Used for hiding your darkest secrets!");
		conf.set("CHEST.stock",50);
		conf.set("CHEST.spread", .2);
		conf.set("CHEST.time",0);
		
		conf.set("DIAMONDORE.velocity", 2.2);
		conf.set("DIAMONDORE.id", 56);
		conf.set("DIAMONDORE.price", 5400);
		conf.set("DIAMONDORE.floor", 4800);
		conf.set("DIAMONDORE.ceiling", 6000);
		conf.set("DIAMONDORE.description", "A block of solid Diamond!");
		conf.set("DIAMONDORE.stock",50);
		conf.set("DIAMONDORE.spread", 2.2);
		conf.set("DIAMONDORE.time",0);

		conf.set("DIAMONDBLOCK.velocity", 2.2);
		conf.set("DIAMONDBLOCK.id", 57);
		conf.set("DIAMONDBLOCK.price", 5400);
		conf.set("DIAMONDBLOCK.floor", 4800);
		conf.set("DIAMONDBLOCK.ceiling", 6000);
		conf.set("DIAMONDBLOCK.description", "A block of solid Diamond!");
		conf.set("DIAMONDBLOCK.stock",50);
		conf.set("DIAMONDBLOCK.spread", 2.2);
		conf.set("DIAMONDBLOCK.time",0);

		conf.set("CRAFTBENCH.velocity", .09);
		conf.set("CRAFTBENCH.id", 58);
		conf.set("CRAFTBENCH.price", 2.5);
		conf.set("CRAFTBENCH.floor", 1.5);
		conf.set("CRAFTBENCH.ceiling", 3.5);
		conf.set("CRAFTBENCH.description", "Use it to built just about anything!");
		conf.set("CRAFTBENCH.stock",50);
		conf.set("CRAFTBENCH.spread", .09);
		conf.set("CRAFTBENCH.time",0);
		
		conf.set("FURNACE.velocity", .09);
		conf.set("FURNACE.id", 61);
		conf.set("FURNACE.price", 2.5);
		conf.set("FURNACE.floor", 1.5);
		conf.set("FURNACE.ceiling", 2.5);
		conf.set("FURNACE.description", "Use it to burn all your stuff up or cook your food!");
		conf.set("FURNACE.stock",50);
		conf.set("FURNACE.spread", .09);
		conf.set("FURNACE.time",0);

		conf.set("LADDER.velocity", .001);
		conf.set("LADDER.id", 65);
		conf.set("LADDER.price", 1.4);
		conf.set("LADDER.floor", 1);
		conf.set("LADDER.ceiling", 2);
		conf.set("LADDER.description", "Climb straight up anything!");
		conf.set("LADDER.stock",50);
		conf.set("LADDER.spread", .001);
		conf.set("LADDER.time",0);

		conf.set("RAIL.velocity", .4);
		conf.set("RAIL.id", 66);
		conf.set("RAIL.price", 18);
		conf.set("RAIL.floor", 14);
		conf.set("RAIL.ceiling", 26);
		conf.set("RAIL.description", "Helps minecarts move along!");
		conf.set("RAIL.stock",50);
		conf.set("RAIL.spread", .4);
		conf.set("RAIL.time",0);

		conf.set("COBBLESTAIRS.velocity", .04);
		conf.set("COBBLESTAIRS.id", 67);
		conf.set("COBBLESTAIRS.price", 1.2);
		conf.set("COBBLESTAIRS.floor", .9);
		conf.set("COBBLESTAIRS.ceiling", 1.5);
		conf.set("COBBLESTAIRS.description", "Walk up high on cobble stairs!");
		conf.set("COBBLESTAIRS.stock",50);
		conf.set("COBBLESTAIRS.spread", .04);
		conf.set("COBBLESTAIRS.time",0);

		conf.set("LEVER.velocity", .08);
		conf.set("LEVER.id", 69);
		conf.set("LEVER.price", 1.2);
		conf.set("LEVER.floor", .01);
		conf.set("LEVER.ceiling", 2);
		conf.set("LEVER.description", "Either off or on!");
		conf.set("LEVER.stock",50);
		conf.set("LEVER.spread", .08);
		conf.set("LEVER.time",0);

		conf.set("STONEPLATE.velocity", .06);
		conf.set("STONEPLATE.id", 70);
		conf.set("STONEPLATE.price", 2);
		conf.set("STONEPLATE.floor", .2);
		conf.set("STONEPLATE.ceiling", 2.8);
		conf.set("STONEPLATE.description", "Step to activate!");
		conf.set("STONEPLATE.stock",50);
		conf.set("STONEPLATE.spread", .06);
		conf.set("STONEPLATE.time",0);

		conf.set("WOODENPLATE.velocity", .06);
		conf.set("WOODENPLATE.id", 72);
		conf.set("WOODENPLATE.price", 2);
		conf.set("WOODENPLATE.floor", .2);
		conf.set("WOODENPLATE.ceiling", 2.8);
		conf.set("WOODENPLATE.description", "Step to activate!");
		conf.set("WOODENPLATE.stock",50);
		conf.set("WOODENPLATE.spread", .06);
		conf.set("WOODENPLATE.time",0);
		
		conf.set("REDSTONEORE.velocity", .6);
		conf.set("REDSTONEORE.id", 73);
		conf.set("REDSTONEORE.price", 8);
		conf.set("REDSTONEORE.floor", 2);
		conf.set("REDSTONEORE.ceiling", 16);
		conf.set("REDSTONEORE.description", "Conducts power!");
		conf.set("REDSTONEORE.stock",50);
		conf.set("REDSTONEORE.spread", .01);
		conf.set("REDSTONEORE.time",0);

		conf.set("BUTTON.velocity", .06);
		conf.set("BUTTON.id", 77);
		conf.set("BUTTON.price", 2);
		conf.set("BUTTON.floor", 1);
		conf.set("BUTTON.ceiling", 2.4);
		conf.set("BUTTON.description", "Press to activate!");
		conf.set("BUTTON.stock",50);
		conf.set("BUTTON.spread", .06);
		conf.set("BUTTON.time",0);

		conf.set("CACTUS.velocity", .3);
		conf.set("CACTUS.id", 81);
		conf.set("CACTUS.price", 4);
		conf.set("CACTUS.floor", 2);
		conf.set("CACTUS.ceiling", 6);
		conf.set("CACTUS.description", "Prickly! Found in deserts.");
		conf.set("CACTUS.stock",50);
		conf.set("CACTUS.spread", .3);
		conf.set("CACTUS.time",0);

		conf.set("JUKEBOX.velocity", 1.8);
		conf.set("JUKEBOX.id", 84);
		conf.set("JUKEBOX.price", 620);
		conf.set("JUKEBOX.floor", 560);
		conf.set("JUKEBOX.ceiling", 580);
		conf.set("JUKEBOX.description", "Plays lovely records!");
		conf.set("JUKEBOX.stock",50);
		conf.set("JUKEBOX.spread", 1.8);
		conf.set("JUKEBOX.time",0);

		conf.set("FENCE.velocity", .02);
		conf.set("FENCE.id", 85);
		conf.set("FENCE.price", 1);
		conf.set("FENCE.floor", .2);
		conf.set("FENCE.ceiling", 1.8);
		conf.set("FENCE.description", "Guard your territory!");
		conf.set("FENCE.stock",50);
		conf.set("FENCE.spread", .02);
		conf.set("FENCE.time",0);

		conf.set("PUMPKIN.velocity", .8);
		conf.set("PUMPKIN.id", 86);
		conf.set("PUMPKIN.price", 10);
		conf.set("PUMPKIN.floor", 3);
		conf.set("PUMPKIN.ceiling", 15);
		conf.set("PUMPKIN.description", "Scary or delicious?!");
		conf.set("PUMPKIN.stock",50);
		conf.set("PUMPKIN.spread", .8);
		conf.set("PUMPKIN.time",0);

		conf.set("NETHERRACK.velocity", .001);
		conf.set("NETHERRACK.id", 87);
		conf.set("NETHERRACK.price", .75);
		conf.set("NETHERRACK.floor", .3);
		conf.set("NETHERRACK.ceiling", 1);
		conf.set("NETHERRACK.description", "Burns indefinitely!");
		conf.set("NETHERRACK.stock",50);
		conf.set("NETHERRACK.spread", .001);
		conf.set("NETHERRACK.time",0);

		conf.set("SOULSAND.velocity", .06);
		conf.set("SOULSAND.id", 88);
		conf.set("SOULSAND.price", 3);
		conf.set("SOULSAND.floor", 1);
		conf.set("SOULSAND.ceiling", 8);
		conf.set("SOULSAND.description", "Slows you down!");
		conf.set("SOULSAND.stock",50);
		conf.set("SOULSAND.spread", .06);
		conf.set("SOULSAND.time",0);

		conf.set("GLOWSTONE.velocity", .09);
		conf.set("GLOWSTONE.id", 89);
		conf.set("GLOWSTONE.price", 8);
		conf.set("GLOWSTONE.floor", 4);
		conf.set("GLOWSTONE.ceiling", 20);
		conf.set("GLOWSTONE.description", "Light from the pits of hell!");
		conf.set("GLOWSTONE.stock",50);
		conf.set("GLOWSTONE.spread", .09);
		conf.set("GLOWSTONE.time",0);

		conf.set("TRAPDOOR.velocity", .03);
		conf.set("TRAPDOOR.id", 96);
		conf.set("TRAPDOOR.price", 4);
		conf.set("TRAPDOOR.floor", 1);
		conf.set("TRAPDOOR.ceiling", 6);
		conf.set("TRAPDOOR.description", "Kind of like.. a door for your floor!");
		conf.set("TRAPDOOR.stock",50);
		conf.set("TRAPDOOR.spread", .03);
		conf.set("TRAPDOOR.time",0);

		conf.set("STONEBRICKS.velocity", .9);
		conf.set("STONEBRICKS.id", 98);
		conf.set("STONEBRICKS.price", 8);
		conf.set("STONEBRICKS.floor", 4);
		conf.set("STONEBRICKS.ceiling", 16);
		conf.set("STONEBRICKS.description", "Fancy Stronghold stone!");
		conf.set("STONEBRICKS.stock",50);
		conf.set("STONEBRICKS.spread", .9);
		conf.set("STONEBRICKS.time",0);

		conf.set("IRONBARS.velocity", .11);
		conf.set("IRONBARS.id", 101);
		conf.set("IRONBARS.price", 18);
		conf.set("IRONBARS.floor", 11);
		conf.set("IRONBARS.ceiling", 29);
		conf.set("IRONBARS.description", "Fit for prisons!");
		conf.set("IRONBARS.stock",50);
		conf.set("IRONBARS.spread", .11);
		conf.set("IRONBARS.time",0);

		conf.set("GLASSPANE.velocity", .005);
		conf.set("GLASSPANE.id", 102);
		conf.set("GLASSPANE.price", .14);
		conf.set("GLASSPANE.floor", .08);
		conf.set("GLASSPANE.ceiling", .2);
		conf.set("GLASSPANE.description", "Thinner than glass!");
		conf.set("GLASSPANE.stock",50);
		conf.set("GLASSPANE.spread", .005);
		conf.set("GLASSPANE.time",0);

		conf.set("BRICKSTAIRS.velocity", .09);
		conf.set("BRICKSTAIRS.id", 108);
		conf.set("BRICKSTAIRS.price", 30);
		conf.set("BRICKSTAIRS.floor", 10);
		conf.set("BRICKSTAIRS.ceiling", 50);
		conf.set("BRICKSTAIRS.description", "Walk up high with brick stairs!");
		conf.set("BRICKSTAIRS.stock",50);
		conf.set("BRICKSTAIRS.spread", .09);
		conf.set("BRICKSTAIRS.time",0);

		conf.set("STONEBRICKSTAIRS.velocity", .06);
		conf.set("STONEBRICKSTAIRS.id", 109);
		conf.set("STONEBRICKSTAIRS.price", 12);
		conf.set("STONEBRICKSTAIRS.floor", 6);
		conf.set("STONEBRICKSTAIRS.ceiling", 18);
		conf.set("STONEBRICKSTAIRS.description", "Walk up high with stairs from strongholds!");
		conf.set("STONEBRICKSTAIRS.stock",50);
		conf.set("STONEBRICKSTAIRS.spread", .06);
		conf.set("STONEBRICKSTAIRS.time",0);

		conf.set("NETHERBRICK.velocity", .07);
		conf.set("NETHERBRICK.id", 112);
		conf.set("NETHERBRICK.price", 5);
		conf.set("NETHERBRICK.floor", .5);
		conf.set("NETHERBRICK.ceiling", 10);
		conf.set("NETHERBRICK.description", "Stolen from the strongholds of hell!");
		conf.set("NETHERBRICK.stock",50);
		conf.set("NETHERBRICK.spread", .07);
		conf.set("NETHERBRICK.time",0);

		conf.set("NETHERBRICKFENCE.velocity", .11);
		conf.set("NETHERBRICKFENCE.id", 1113);
		conf.set("NETHERBRICKFENCE.price", 8);
		conf.set("NETHERBRICKFENCE.floor", 2);
		conf.set("NETHERBRICKFENCE.ceiling", 12);
		conf.set("NETHERBRICKFENCE.description", "Stolen from the strongholds of hell!");
		conf.set("NETHERBRICKFENCE.stock",50);
		conf.set("NETHERBRICKFENCE.spread", .11);
		conf.set("NETHERBRICKFENCE.time",0);

		conf.set("NETHERBRICKSTAIRS.velocity", .07);
		conf.set("NETHERBRICKSTAIRS.id", 114);
		conf.set("NETHERBRICKSTAIRS.price", 8);
		conf.set("NETHERBRICKSTAIRS.floor", 2);
		conf.set("NETHERBRICKSTAIRS.ceiling", 12);
		conf.set("NETHERBRICKSTAIRS.description", "Stolen from the strongholds of hell!");
		conf.set("NETHERBRICKSTAIRS.stock",50);
		conf.set("NETHERBRICKSTAIRS.spread", .07);
		conf.set("NETHERBRICKSTAIRS.time",0);

		conf.set("ENCHANTMENTTABLE.velocity", 1.5);
		conf.set("ENCHANTMENTTABLE.id", 116);
		conf.set("ENCHANTMENTTABLE.price", 1625);
		conf.set("ENCHANTMENTTABLE.floor", 1300);
		conf.set("ENCHANTMENTTABLE.ceiling", 1900);
		conf.set("ENCHANTMENTTABLE.description", "Used to enchant tools and armor!");
		conf.set("ENCHANTMENTTABLE.stock",50);
		conf.set("ENCHANTMENTTABLE.spread", 1.5);
		conf.set("ENCHANTMENTTABLE.time",0);

		conf.set("BREWINGSTAND.velocity", .45);
		conf.set("BREWINGSTAND.id", 379);
		conf.set("BREWINGSTAND.price", 155);
		conf.set("BREWINGSTAND.floor", 100);
		conf.set("BREWINGSTAND.ceiling", 200);
		conf.set("BREWINGSTAND.description", "Used to make potions!");
		conf.set("BREWINGSTAND.stock",50);
		conf.set("BREWINGSTAND.spread", .45);
		conf.set("BREWINGSTAND.time",0);

		conf.set("CAULDRON.velocity", .25);
		conf.set("CAULDRON.id", 380);
		conf.set("CAULDRON.price", 350);
		conf.set("CAULDRON.floor", 250);
		conf.set("CAULDRON.ceiling", 450);
		conf.set("CAULDRON.description", "Holds water for potion-making!");
		conf.set("CAULDRON.stock",50);
		conf.set("CAULDRON.spread", .25);
		conf.set("CAULDRON.time",0);

		conf.set("IRONSHOVEL.velocity", .8);
		conf.set("IRONSHOVEL.id", 256);
		conf.set("IRONSHOVEL.price", 51);
		conf.set("IRONSHOVEL.floor", 40);
		conf.set("IRONSHOVEL.ceiling", 60);
		conf.set("IRONSHOVEL.description", "Digging with iron!");
		conf.set("IRONSHOVEL.stock",50);
		conf.set("IRONSHOVEL.spread", .8);
		conf.set("IRONSHOVEL.time",0);

		conf.set("IRONPICKAXE.velocity", 1.1);
		conf.set("IRONPICKAXE.id", 257);
		conf.set("IRONPICKAXE.price", 151);
		conf.set("IRONPICKAXE.floor", 130);
		conf.set("IRONPICKAXE.ceiling", 170);
		conf.set("IRONPICKAXE.description", "Mining with iron!");
		conf.set("IRONPICKAXE.stock",50);
		conf.set("IRONPICKAXE.spread", 1.1);
		conf.set("IRONPICKAXE.time",0);

		conf.set("IRONAXE.velocity", .9);
		conf.set("IRONAXE.id", 258);
		conf.set("IRONAXE.price", 101);
		conf.set("IRONAXE.floor", 80);
		conf.set("IRONAXE.ceiling", 120);
		conf.set("IRONAXE.description", "Cutting with iron!");
		conf.set("IRONAXE.stock",50);
		conf.set("IRONAXE.spread", .9);
		conf.set("IRONAXE.time",0);

		conf.set("FLINTANDSTEEL.velocity", .001);
		conf.set("FLINTANDSTEEL.id", 259);
		conf.set("FLINTANDSTEEL.price", 62);
		conf.set("FLINTANDSTEEL.floor", 40);
		conf.set("FLINTANDSTEEL.ceiling", 80);
		conf.set("FLINTANDSTEEL.description", "Best friend to an Arsonist!");
		conf.set("FLINTANDSTEEL.stock",50);
		conf.set("FLINTANDSTEEL.spread", .001);
		conf.set("FLINTANDSTEEL.time",0);

		conf.set("APPLE.velocity", .8);
		conf.set("APPLE.id", 260);
		conf.set("APPLE.price", 150);
		conf.set("APPLE.floor", 100);
		conf.set("APPLE.ceiling", 300);
		conf.set("APPLE.description", "A delicious fruit!");
		conf.set("APPLE.stock",50);
		conf.set("APPLE.spread", .8);
		conf.set("APPLE.time",0);

		conf.set("BOW.velocity", .1);
		conf.set("BOW.id", 261);
		conf.set("BOW.price", 5);
		conf.set("BOW.floor", 2);
		conf.set("BOW.ceiling", 25);
		conf.set("BOW.description", "Kill your opponents from far away!");
		conf.set("BOW.stock",50);
		conf.set("BOW.spread", .1);
		conf.set("BOW.time",0);

		conf.set("ARROW.velocity", .14);
		conf.set("ARROW.id", 262);
		conf.set("ARROW.price", 4);
		conf.set("ARROW.floor", 1);
		conf.set("ARROW.ceiling", 10);
		conf.set("ARROW.description", "Ammo for your bow!");
		conf.set("ARROW.stock",50);
		conf.set("ARROW.spread", .14);
		conf.set("ARROW.time",0);

		conf.set("COAL.velocity", .09);
		conf.set("COAL.id", 263);
		conf.set("COAL.price", 10);
		conf.set("COAL.floor", 1);
		conf.set("COAL.ceiling", 30);
		conf.set("COAL.description", "Good for burning!");
		conf.set("COAL.stock",50);
		conf.set("COAL.spread", .09);
		conf.set("COAL.time",0);
		
		conf.set("CHARCOAL.velocity", .09);
		conf.set("CHARCOAL.id", 263001);
		conf.set("CHARCOAL.price", 10);
		conf.set("CHARCOAL.floor", 1);
		conf.set("CHARCOAL.ceiling", 30);
		conf.set("CHARCOAL.description", "Good for burning!");
		conf.set("CHARCOAL.stock",50);
		conf.set("CHARCOAL.spread", .09);
		conf.set("CHARCOAL.time",0);

		conf.set("DIAMOND.velocity", 2.9);
		conf.set("DIAMOND.id", 264);
		conf.set("DIAMOND.price", 600);
		conf.set("DIAMOND.floor", 450);
		conf.set("DIAMOND.ceiling", 900);
		conf.set("DIAMOND.description", "The most precious jewel of them all!");
		conf.set("DIAMOND.stock",50);
		conf.set("DIAMOND.spread", 2.9);
		conf.set("DIAMOND.time",0);

		conf.set("IRONINGOT.velocity", .12);
		conf.set("IRONINGOT.id", 265);
		conf.set("IRONINGOT.price", 50);
		conf.set("IRONINGOT.floor", 30);
		conf.set("IRONINGOT.ceiling", 80);
		conf.set("IRONINGOT.description", "Tough as steel!");
		conf.set("IRONINGOT.stock",50);
		conf.set("IRONINGOT.spread", .12);
		conf.set("IRONINGOT.time",0);

		conf.set("GOLDINGOT.velocity", .19);
		conf.set("GOLDINGOT.id", 266);
		conf.set("GOLDINGOT.price", 300);
		conf.set("GOLDINGOT.floor", 250);
		conf.set("GOLDINGOT.ceiling", 400);
		conf.set("GOLDINGOT.description", "Prettiest metal ever!");
		conf.set("GOLDINGOT.stock",50);
		conf.set("GOLDINGOT.spread", .19);
		conf.set("GOLDINGOT.time",0);
		
		conf.set("IRONSWORD.velocity", .29);
		conf.set("IRONSWORD.id", 267);
		conf.set("IRONSWORD.price", 101);
		conf.set("IRONSWORD.floor", 80);
		conf.set("IRONSWORD.ceiling", 120);
		conf.set("IRONSWORD.description", "Fight with Iron!");
		conf.set("IRONSWORD.stock",50);
		conf.set("IRONSWORD.spread", .29);
		conf.set("IRONSWORD.time",0);

		conf.set("WOODENSWORD.velocity", .009);
		conf.set("WOODENSWORD.id", 268);
		conf.set("WOODENSWORD.price", .5);
		conf.set("WOODENSWORD.floor", .3);
		conf.set("WOODENSWORD.ceiling", .8);
		conf.set("WOODENSWORD.description", "A weak sword made of wood!");
		conf.set("WOODENSWORD.stock",50);
		conf.set("WOODENSWORD.spread", .009);
		conf.set("WOODENSWORD.time",0);

		conf.set("WOODENSHOVEL.velocity", .009);
		conf.set("WOODENSHOVEL.id", 269);
		conf.set("WOODENSHOVEL.price", .3);
		conf.set("WOODENSHOVEL.floor", .1);
		conf.set("WOODENSHOVEL.ceiling", .5);
		conf.set("WOODENSHOVEL.description", "Not a very reliable shovel!");
		conf.set("WOODENSHOVEL.stock",50);
		conf.set("WOODENSHOVEL.spread", .009);
		conf.set("WOODENSHOVEL.time",0);

		conf.set("WOODENPICKAXE.velocity", .009);
		conf.set("WOODENPICKAXE.id", 270);
		conf.set("WOODENPICKAXE.price", .7);
		conf.set("WOODENPICKAXE.floor", .5);
		conf.set("WOODENPICKAXE.ceiling", .9);
		conf.set("WOODENPICKAXE.description", "I'm not sure if this can break stone..!");
		conf.set("WOODENPICKAXE.stock",50);
		conf.set("WOODENPICKAXE.spread", .009);
		conf.set("WOODENPICKAXE.time",0);

		conf.set("WOODENAXE.velocity", .009);
		conf.set("WOODENAXE.id", 271);
		conf.set("WOODENAXE.price", .5);
		conf.set("WOODENAXE.floor", .3);
		conf.set("WOODENAXE.ceiling", .7);
		conf.set("WOODENAXE.description", "Wood on wood cutting!!");
		conf.set("WOODENAXE.stock",50);
		conf.set("WOODENAXE.spread", .009);
		conf.set("WOODENAXE.time",0);

		conf.set("STONESWORD.velocity", .03);
		conf.set("STONESWORD.id", 272);
		conf.set("STONESWORD.price", 1);
		conf.set("STONESWORD.floor", .5);
		conf.set("STONESWORD.ceiling", 1.5);
		conf.set("STONESWORD.description", "A sword of stone!");
		conf.set("STONESWORD.stock",50);
		conf.set("STONESWORD.spread", .03);
		conf.set("STONESWORD.time",0);

		conf.set("STONESHOVEL.velocity", .03);
		conf.set("STONESHOVEL.id", 273);
		conf.set("STONESHOVEL.price", .6);
		conf.set("STONESHOVEL.floor", .3);
		conf.set("STONESHOVEL.ceiling", .9);
		conf.set("STONESHOVEL.description", "A shovel of stone!");
		conf.set("STONESHOVEL.stock",50);
		conf.set("STONESHOVEL.spread", .03);
		conf.set("STONESHOVEL.time",0);

		conf.set("STONEPICKAXE.velocity", .03);
		conf.set("STONEPICKAXE.id", 274);
		conf.set("STONEPICKAXE.price", 1.4);
		conf.set("STONEPICKAXE.floor", .7);
		conf.set("STONEPICKAXE.ceiling", 2.1);
		conf.set("STONEPICKAXE.description", "Stone on Stone mining!");
		conf.set("STONEPICKAXE.stock",50);
		conf.set("STONEPICKAXE.spread", .03);
		conf.set("STONEPICKAXE.time",0);

		conf.set("STONEAXE.velocity", .03);
		conf.set("STONEAXE.id", 275);
		conf.set("STONEAXE.price", 1);
		conf.set("STONEAXE.floor", .5);
		conf.set("STONEAXE.ceiling", 1.5);
		conf.set("STONEAXE.description", "An axe of stone!");
		conf.set("STONEAXE.stock",50);
		conf.set("STONEAXE.spread", .03);
		conf.set("STONEAXE.time",0);

		conf.set("DIAMONDSWORD.velocity", 9.3);
		conf.set("DIAMONDSWORD.id", 276);
		conf.set("DIAMONDSWORD.price", 1220);
		conf.set("DIAMONDSWORD.floor", 1100);
		conf.set("DIAMONDSWORD.ceiling", 1300);
		conf.set("DIAMONDSWORD.description", "Kill your enemies with DIAMOND!");
		conf.set("DIAMONDSWORD.stock",50);
		conf.set("DIAMONDSWORD.spread", 9.3);
		conf.set("DIAMONDSWORD.time",0);

		conf.set("DIAMONDSHOVEL.velocity", 9.3);
		conf.set("DIAMONDSHOVEL.id", 277);
		conf.set("DIAMONDSHOVEL.price", 620);
		conf.set("DIAMONDSHOVEL.floor", 480);
		conf.set("DIAMONDSHOVEL.ceiling", 700);
		conf.set("DIAMONDSHOVEL.description", "Dirt and diamond, a nasty combo!");
		conf.set("DIAMONDSHOVEL.stock",50);
		conf.set("DIAMONDSHOVEL.spread", 9.3);
		conf.set("DIAMONDSHOVEL.time",0);
		
		conf.set("DIAMONDPICKAXE.velocity", 12.2);
		conf.set("DIAMONDPICKAXE.id", 278);
		conf.set("DIAMONDPICKAXE.price", 1820);
		conf.set("DIAMONDPICKAXE.floor", 1600);
		conf.set("DIAMONDPICKAXE.ceiling", 1900);
		conf.set("DIAMONDPICKAXE.description", "Breaks through anything!");
		conf.set("DIAMONDPICKAXE.stock",50);
		conf.set("DIAMONDPICKAXE.spread", 12.2);
		conf.set("DIAMONDPICKAXE.time",0);

		conf.set("DIAMONDAXE.velocity", 9.3);
		conf.set("DIAMONDAXE.id", 279);
		conf.set("DIAMONDAXE.price", 620);
		conf.set("DIAMONDAXE.floor", 480);
		conf.set("DIAMONDAXE.ceiling", 700);
		conf.set("DIAMONDAXE.description", "Cuts through anything!");
		conf.set("DIAMONDAXE.stock",50);
		conf.set("DIAMONDAXE.spread", 9.3);
		conf.set("DIAMONDAXE.time",0);

		conf.set("STICK.velocity", .003);
		conf.set("STICK.id", 280);
		conf.set("STICK.price", .05);
		conf.set("STICK.floor", .01);
		conf.set("STICK.ceiling", .1);
		conf.set("STICK.description", "Just a wooden stick!");
		conf.set("STICK.stock",50);
		conf.set("STICK.spread", .003);
		conf.set("STICK.time",0);

		conf.set("BOWL.velocity", .003);
		conf.set("BOWL.id", 281);
		conf.set("BOWL.price", .15);
		conf.set("BOWL.floor", .05);
		conf.set("BOWL.ceiling", .4);
		conf.set("BOWL.description", "A bowl.. for soup!");
		conf.set("BOWL.stock",50);
		conf.set("BOWL.spread", .003);
		conf.set("BOWL.time",0);

		conf.set("MUSHROOMSOUP.velocity", .04);
		conf.set("MUSHROOMSOUP.id", 282);
		conf.set("MUSHROOMSOUP.price", 1.25);
		conf.set("MUSHROOMSOUP.floor", .7);
		conf.set("MUSHROOMSOUP.ceiling", 2);
		conf.set("MUSHROOMSOUP.description", "Shroom soup!");
		conf.set("MUSHROOMSOUP.stock",50);
		conf.set("MUSHROOMSOUP.spread", .04);
		conf.set("MUSHROOMSOUP.time",0);

		conf.set("GOLDSWORD.velocity", 5.6);
		conf.set("GOLDSWORD.id", 283);
		conf.set("GOLDSWORD.price", 600);
		conf.set("GOLDSWORD.floor", 400);
		conf.set("GOLDSWORD.ceiling", 800);
		conf.set("GOLDSWORD.description", "Effective, but short-lived!");
		conf.set("GOLDSWORD.stock",50);
		conf.set("GOLDSWORD.spread", 5.6);
		conf.set("GOLDSWORD.time",0);

		conf.set("GOLDSHOVEL.velocity", 5.6);
		conf.set("GOLDSHOVEL.id", 284);
		conf.set("GOLDSHOVEL.price", 300);
		conf.set("GOLDSHOVEL.floor", 200);
		conf.set("GOLDSHOVEL.ceiling", 400);
		conf.set("GOLDSHOVEL.description", "Effective, but short-lived!");
		conf.set("GOLDSHOVEL.stock",50);
		conf.set("GOLDSHOVEL.spread", 5.6);
		conf.set("GOLDSHOVEL.time",0);

		conf.set("GOLDPICKAXE.velocity", 5.6);
		conf.set("GOLDPICKAXE.id", 285);
		conf.set("GOLDPICKAXE.price", 900);
		conf.set("GOLDPICKAXE.floor", 850);
		conf.set("GOLDPICKAXE.ceiling", 1050);
		conf.set("GOLDPICKAXE.description", "Effective, but short-lived!");
		conf.set("GOLDPICKAXE.stock",50);
		conf.set("GOLDPICKAXE.spread", 5.6);
		conf.set("GOLDPICKAXE.time",0);

		conf.set("GOLDAXE.velocity", 5.6);
		conf.set("GOLDAXE.id", 286);
		conf.set("GOLDAXE.price", 600);
		conf.set("GOLDAXE.floor", 500);
		conf.set("GOLDAXE.ceiling", 700);
		conf.set("GOLDAXE.description", "Effective, but short-lived!");
		conf.set("GOLDAXE.stock",50);
		conf.set("GOLDAXE.spread", 5.6);
		conf.set("GOLDAXE.time",0);

		conf.set("STRING.velocity", .16);
		conf.set("STRING.id", 287);
		conf.set("STRING.price", 10);
		conf.set("STRING.floor", 5);
		conf.set("STRING.ceiling", 20);
		conf.set("STRING.description", "String from spiders!");
		conf.set("STRING.stock",50);
		conf.set("STRING.spread", .16);
		conf.set("STRING.time",0);

		conf.set("FEATHER.velocity", .16);
		conf.set("FEATHER.id", 288);
		conf.set("FEATHER.price", 10);
		conf.set("FEATHER.floor", 5);
		conf.set("FEATHER.ceiling", 20);
		conf.set("FEATHER.description", "Feathers from...zombies?!");
		conf.set("FEATHER.stock",50);
		conf.set("FEATHER.spread", .16);
		conf.set("FEATHER.time",0);

		conf.set("GUNPOWDER.velocity", .32);
		conf.set("GUNPOWDER.id", 189);
		conf.set("GUNPOWDER.price", 20);
		conf.set("GUNPOWDER.floor", 10);
		conf.set("GUNPOWDER.ceiling", 40);
		conf.set("GUNPOWDER.description", "The heart of a creeper!");
		conf.set("GUNPOWDER.stock",50);
		conf.set("GUNPOWDER.spread", .32);
		conf.set("GUNPOWDER.time",0);

		conf.set("WOODENHOE.velocity", .005);
		conf.set("WOODENHOE.id", 290);
		conf.set("WOODENHOE.price", .5);
		conf.set("WOODENHOE.floor", .3);
		conf.set("WOODENHOE.ceiling", .7);
		conf.set("WOODENHOE.description", "Till your farm, wood-style!");
		conf.set("WOODENHOE.stock",50);
		conf.set("WOODENHOE.spread", .005);
		conf.set("WOODENHOE.time",0);

		conf.set("STONEHOE.velocity", .01);
		conf.set("STONEHOE.id", 291);
		conf.set("STONEHOE.price", 1);
		conf.set("STONEHOE.floor", .5);
		conf.set("STONEHOE.ceiling", 1.5);
		conf.set("STONEHOE.description", "Till your farm, stone-style!");
		conf.set("STONEHOE.stock",50);
		conf.set("STONEHOE.spread", .01);
		conf.set("STONEHOE.time",0);

		conf.set("IRONHOE.velocity", 1.9);
		conf.set("IRONHOE.id", 292);
		conf.set("IRONHOE.price", 101);
		conf.set("IRONHOE.floor", 80);
		conf.set("IRONHOE.ceiling", 120);
		conf.set("IRONHOE.description", "Till your farm, iron-style!");
		conf.set("IRONHOE.stock",50);
		conf.set("IRONHOE.spread", 1.9);
		conf.set("IRONHOE.time",0);

		conf.set("DIAMONDHOE.velocity", 12.1);
		conf.set("DIAMONDHOE.id", 293);
		conf.set("DIAMONDHOE.price", 1200);
		conf.set("DIAMONDHOE.floor", 1000);
		conf.set("DIAMONDHOE.ceiling", 1400);
		conf.set("DIAMONDHOE.description", "Till your farm in excessive luxury!");
		conf.set("DIAMONDHOE.stock",50);
		conf.set("DIAMONDHOE.spread", 12.1);
		conf.set("DIAMONDHOE.time",0);

		conf.set("GOLDHOE.velocity", 7.9);
		conf.set("GOLDHOE.id", 294);
		conf.set("GOLDHOE.price", 600);
		conf.set("GOLDHOE.floor", 500);
		conf.set("GOLDHOE.ceiling", 600);
		conf.set("GOLDHOE.description", "Found on beaches!");
		conf.set("GOLDHOE.stock",50);
		conf.set("GOLDHOE.spread", 7.9);
		conf.set("GOLDHOE.time",0);

		conf.set("SEEDS.velocity", .01);
		conf.set("SEEDS.id", 295);
		conf.set("SEEDS.price", 1.2);
		conf.set("SEEDS.floor", .9);
		conf.set("SEEDS.ceiling", 1.5);
		conf.set("SEEDS.description", "Grows wheat!");
		conf.set("SEEDS.stock",50);
		conf.set("SEEDS.spread", .01);
		conf.set("SEEDS.time",0);

		conf.set("WHEAT.velocity", .07);
		conf.set("WHEAT.id", 296);
		conf.set("WHEAT.price", 3);
		conf.set("WHEAT.floor", 2);
		conf.set("WHEAT.ceiling", .4);
		conf.set("WHEAT.description", "Mmm, good for bread and cake!");
		conf.set("WHEAT.stock",50);
		conf.set("WHEAT.spread", .07);
		conf.set("WHEAT.time",0);

		conf.set("BREAD.velocity", .11);
		conf.set("BREAD.id", 297);
		conf.set("BREAD.price", 9);
		conf.set("BREAD.floor", 7);
		conf.set("BREAD.ceiling", 11);
		conf.set("BREAD.description", "Scrumptious carbohydrates!");
		conf.set("BREAD.stock",50);
		conf.set("BREAD.spread", .11);
		conf.set("BREAD.time",0);

		conf.set("LEATHERCAP.velocity", .78);
		conf.set("LEATHERCAP.id", 298);
		conf.set("LEATHERCAP.price", 25);
		conf.set("LEATHERCAP.floor", 15);
		conf.set("LEATHERCAP.ceiling", 35);
		conf.set("LEATHERCAP.description", "A hat made of cow skin!");
		conf.set("LEATHERCAP.stock",50);
		conf.set("LEATHERCAP.spread", .78);
		conf.set("LEATHERCAP.time",0);

		conf.set("LEATHERTUNIC.velocity", .8);
		conf.set("LEATHERTUNIC.id", 299);
		conf.set("LEATHERTUNIC.price", 50);
		conf.set("LEATHERTUNIC.floor", 40);
		conf.set("LEATHERTUNIC.ceiling", 60);
		conf.set("LEATHERTUNIC.description", "A shirt made of cow skin!");
		conf.set("LEATHERTUNIC.stock",50);
		conf.set("LEATHERTUNIC.spread", .8);
		conf.set("LEATHERTUNIC.time",0);

		conf.set("LEATHERPANTS.velocity", .9);
		conf.set("LEATHERPANTS.id", 300);
		conf.set("LEATHERPANTS.price", 35);
		conf.set("LEATHERPANTS.floor", 25);
		conf.set("LEATHERPANTS.ceiling", 45);
		conf.set("LEATHERPANTS.description", "Pants made of cow skin!");
		conf.set("LEATHERPANTS.stock",50);
		conf.set("LEATHERPANTS.spread", .9);
		conf.set("LEATHERPANTS.time",0);

		conf.set("LEATHERBOOTS.velocity", 1);
		conf.set("LEATHERBOOTS.id", 301);
		conf.set("LEATHERBOOTS.price", 20);
		conf.set("LEATHERBOOTS.floor", 10);
		conf.set("LEATHERBOOTS.ceiling", 30);
		conf.set("LEATHERBOOTS.description", "Boots made of cow skin!");
		conf.set("LEATHERBOOTS.stock",50);
		conf.set("LEATHERBOOTS.spread", 1);
		conf.set("LEATHERBOOTS.time",0);

		conf.set("IRONHELMET.velocity", 2.6);
		conf.set("IRONHELMET.id", 306);
		conf.set("IRONHELMET.price", 250);
		conf.set("IRONHELMET.floor", 200);
		conf.set("IRONHELMET.ceiling", 300);
		conf.set("IRONHELMET.description", "Ironhead!");
		conf.set("IRONHELMET.stock",50);
		conf.set("IRONHELMET.spread", 2.6);
		conf.set("IRONHELMET.time",0);

		conf.set("IRONCHESTPLATE.velocity", 3.6);
		conf.set("IRONCHESTPLATE.id", 307);
		conf.set("IRONCHESTPLATE.price", 400);
		conf.set("IRONCHESTPLATE.floor", 350);
		conf.set("IRONCHESTPLATE.ceiling", 450);
		conf.set("IRONCHESTPLATE.description", "IronTorso!");
		conf.set("IRONCHESTPLATE.stock",50);
		conf.set("IRONCHESTPLATE.spread", 3.6);
		conf.set("IRONCHESTPLATE.time",0);

		conf.set("IRONLEGGINGS.velocity", 3.9);
		conf.set("IRONLEGGINGS.id", 308);
		conf.set("IRONLEGGINGS.price", 350);
		conf.set("IRONLEGGINGS.floor", 275);
		conf.set("IRONLEGGINGS.ceiling", 475);
		conf.set("IRONLEGGINGS.description", "Ironlegs!");
		conf.set("IRONLEGGINGS.stock",50);
		conf.set("IRONLEGGINGS.spread", 3.9);
		conf.set("IRONLEGGINGS.time",0);

		conf.set("IRONBOOTS.velocity", 4.6);
		conf.set("IRONBOOTS.id", 309);
		conf.set("IRONBOOTS.price", 200);
		conf.set("IRONBOOTS.floor", 150);
		conf.set("IRONBOOTS.ceiling", 250);
		conf.set("IRONBOOTS.description", "IronFeet!");
		conf.set("IRONBOOTS.stock",50);
		conf.set("IRONBOOTS.spread", 4.6);
		conf.set("IRONBOOTS.time",0);

		conf.set("DIAMONDHELMET.velocity", 23.9);
		conf.set("DIAMONDHELMET.id", 310);
		conf.set("DIAMONDHELMET.price", 3000);
		conf.set("DIAMONDHELMET.floor", 2400);
		conf.set("DIAMONDHELMET.ceiling", 3600);
		conf.set("DIAMONDHELMET.description", "Flashiest helmet around!");
		conf.set("DIAMONDHELMET.stock",50);
		conf.set("DIAMONDHELMET.spread", 23.9);
		conf.set("DIAMONDHELMET.time",0);

		conf.set("DIAMONDCHESTPLATE.velocity", 59.1);
		conf.set("DIAMONDCHESTPLATE.id", 311);
		conf.set("DIAMONDCHESTPLATE.price", 4800);
		conf.set("DIAMONDCHESTPLATE.floor", 4200);
		conf.set("DIAMONDCHESTPLATE.ceiling", 5600);
		conf.set("DIAMONDCHESTPLATE.description", "Flashiest chestplate around!");
		conf.set("DIAMONDCHESTPLATE.stock",50);
		conf.set("DIAMONDCHESTPLATE.spread", 59.1);
		conf.set("DIAMONDCHESTPLATE.time",0);


		conf.set("DIAMONDLEGGINGS.velocity", 49.2);
		conf.set("DIAMONDLEGGINGS.id", 312);
		conf.set("DIAMONDLEGGINGS.price", 4200);
		conf.set("DIAMONDLEGGINGS.floor", 3500);
		conf.set("DIAMONDLEGGINGS.ceiling", 5000);
		conf.set("DIAMONDLEGGINGS.description", "Flashiest leggings around!");
		conf.set("DIAMONDLEGGINGS.stock",50);
		conf.set("DIAMONDLEGGINGS.spread", 49.2);
		conf.set("DIAMONDLEGGINGS.time",0);


		conf.set("DIAMONDBOOTS.velocity", 26.3);
		conf.set("DIAMONDBOOTS.id", 313);
		conf.set("DIAMONDBOOTS.price", 2400);
		conf.set("DIAMONDBOOTS.floor", 2000);
		conf.set("DIAMONDBOOTS.ceiling", 2800);
		conf.set("DIAMONDBOOTS.description", "Flashiest boots around!");
		conf.set("DIAMONDBOOTS.stock",50);
		conf.set("DIAMONDBOOTS.spread", 26.3);
		conf.set("DIAMONDBOOTS.time",0);


		conf.set("GOLDHELMET.velocity", 28.4);
		conf.set("GOLDHELMET.id", 314);
		conf.set("GOLDHELMET.price", 1500);
		conf.set("GOLDHELMET.floor", 1250);
		conf.set("GOLDHELMET.ceiling", 1750);
		conf.set("GOLDHELMET.description", "14K Helmet!");
		conf.set("GOLDHELMET.stock",50);
		conf.set("GOLDHELMET.spread", 28.4);
		conf.set("GOLDHELMET.time",0);


		conf.set("GOLDCHESTPLATE.velocity", 39.1);
		conf.set("GOLDCHESTPLATE.id", 315);
		conf.set("GOLDCHESTPLATE.price", 4000);
		conf.set("GOLDCHESTPLATE.floor", 3750);
		conf.set("GOLDCHESTPLATE.ceiling", 4250);
		conf.set("GOLDCHESTPLATE.description", "20K Chestplate!");
		conf.set("GOLDCHESTPLATE.stock",50);
		conf.set("GOLDCHESTPLATE.spread", 39.1);
		conf.set("GOLDCHESTPLATE.time",0);


		conf.set("GOLDLEGGINGS.velocity", 23.6);
		conf.set("GOLDLEGGINGS.id", 316);
		conf.set("GOLDLEGGINGS.price", 3500);
		conf.set("GOLDLEGGINGS.floor", 3250);
		conf.set("GOLDLEGGINGS.ceiling", 3750);
		conf.set("GOLDLEGGINGS.description", "24K Leggings!");
		conf.set("GOLDLEGGINGS.stock",50);
		conf.set("GOLDLEGGINGS.spread", 23.6);
		conf.set("GOLDLEGGINGS.time",0);


		conf.set("GOLDBOOTS.velocity", 29.3);
		conf.set("GOLDBOOTS.id", 317);
		conf.set("GOLDBOOTS.price", 2000);
		conf.set("GOLDBOOTS.floor", 1750);
		conf.set("GOLDBOOTS.ceiling", 2250);
		conf.set("GOLDBOOTS.description", "Boots, now in White Gold!");
		conf.set("GOLDBOOTS.stock",50);
		conf.set("GOLDBOOTS.spread", 29.3);
		conf.set("GOLDBOOTS.time",0);


		conf.set("FLINT.velocity", .6);
		conf.set("FLINT.id", 318);
		conf.set("FLINT.price", 5);
		conf.set("FLINT.floor", 1);
		conf.set("FLINT.ceiling", 20);
		conf.set("FLINT.description", "This has a purpose..?");
		conf.set("FLINT.stock",50);
		conf.set("FLINT.spread", .6);
		conf.set("FLINT.time",0);


		conf.set("RAWPORKCHOP.velocity", .4);
		conf.set("RAWPORKCHOP.id", 319);
		conf.set("RAWPORKCHOP.price", 6);
		conf.set("RAWPORKCHOP.floor", 3);
		conf.set("RAWPORKCHOP.ceiling", 9);
		conf.set("RAWPORKCHOP.description", "Mmm, Piggy!");
		conf.set("RAWPORKCHOP.stock",50);
		conf.set("RAWPORKCHOP.spread", .4);
		conf.set("RAWPORKCHOP.time",0);


		conf.set("COOKEDPORKCHOP.velocity", .4);
		conf.set("COOKEDPORKCHOP.id", 320);
		conf.set("COOKEDPORKCHOP.price", 8);
		conf.set("COOKEDPORKCHOP.floor", 5);
		conf.set("COOKEDPORKCHOP.ceiling", 15);
		conf.set("COOKEDPORKCHOP.description", "Mmm, Piggy read to eat!");
		conf.set("COOKEDPORKCHOP.stock",50);
		conf.set("COOKEDPORKCHOP.spread", .4);
		conf.set("COOKEDPORKCHOP.time",0);


		conf.set("PAINTING.velocity", .5);
		conf.set("PAINTING.id", 321);
		conf.set("PAINTING.price", 12);
		conf.set("PAINTING.floor", 5);
		conf.set("PAINTING.ceiling", 20);
		conf.set("PAINTING.description", "Paintings by Notch Da Vinci himself!");
		conf.set("PAINTING.stock",50);
		conf.set("PAINTING.spread", .5);
		conf.set("PAINTING.time",0);

		conf.set("GOLDENAPPLE.velocity", 231.4);
		conf.set("GOLDENAPPLE.id", 322);
		conf.set("GOLDENAPPLE.price", 21750);
		conf.set("GOLDENAPPLE.floor", 5000);
		conf.set("GOLDENAPPLE.ceiling", 30000);
		conf.set("GOLDENAPPLE.description", "An apple of GOLD. Rather hard to consume!");
		conf.set("GOLDENAPPLE.stock",50);
		conf.set("GOLDENAPPLE.spread", 231.4);
		conf.set("GOLDENAPPLE.time",0);

		conf.set("SIGN.velocity", .25);
		conf.set("SIGN.id", 323);
		conf.set("SIGN.price", 4);
		conf.set("SIGN.floor", 2);
		conf.set("SIGN.ceiling", 10);
		conf.set("SIGN.description", "Used to write meaningful information!");
		conf.set("SIGN.stock",50);
		conf.set("SIGN.spread", .25);
		conf.set("SIGN.time",0);

		conf.set("WOODENDOOR.velocity", .05);
		conf.set("WOODENDOOR.id", 324);
		conf.set("WOODENDOOR.price", 1.5);
		conf.set("WOODENDOOR.floor", 1);
		conf.set("WOODENDOOR.ceiling", 5);
		conf.set("WOODENDOOR.description", "A door of wood!");
		conf.set("WOODENDOOR.stock",50);
		conf.set("WOODENDOOR.spread", .05);
		conf.set("WOODENDOOR.time",0);

		conf.set("BUCKET.velocity", 1.4);
		conf.set("BUCKET.id", 325);
		conf.set("BUCKET.price", 150);
		conf.set("BUCKET.floor", 100);
		conf.set("BUCKET.ceiling", 200);
		conf.set("BUCKET.description", "A bucket for holding liquids.. or plasmas!");
		conf.set("BUCKET.stock",50);
		conf.set("BUCKET.spread", 1.4);
		conf.set("BUCKET.time",0);

		conf.set("WATERBUCKET.velocity", 2.1);
		conf.set("WATERBUCKET.id", 326);
		conf.set("WATERBUCKET.price", 155);
		conf.set("WATERBUCKET.floor", 130);
		conf.set("WATERBUCKET.ceiling", 180);
		conf.set("WATERBUCKET.description", "A bucket, with free water!");
		conf.set("WATERBUCKET.stock",50);
		conf.set("WATERBUCKET.spread", 2.1);
		conf.set("WATERBUCKET.time",0);

		conf.set("LAVABUCKET.velocity", 3.1);
		conf.set("LAVABUCKET.id", 327);
		conf.set("LAVABUCKET.price", 160);
		conf.set("LAVABUCKET.floor", 140);
		conf.set("LAVABUCKET.ceiling", 180);
		conf.set("LAVABUCKET.description", "A bucket, with free molten death!");
		conf.set("LAVABUCKET.stock",50);
		conf.set("LAVABUCKET.spread", 3.1);
		conf.set("LAVABUCKET.time",0);

		conf.set("MINECART.velocity", 1.6);
		conf.set("MINECART.id", 328);
		conf.set("MINECART.price", 250);
		conf.set("MINECART.floor", 200);
		conf.set("MINECART.ceiling", 400);
		conf.set("MINECART.description", "Takes you places!");
		conf.set("MINECART.stock",50);
		conf.set("MINECART.spread", 1.6);
		conf.set("MINECART.time",0);

		conf.set("SADDLE.velocity", 3.7);
		conf.set("SADDLE.id", 329);
		conf.set("SADDLE.price", 1000);
		conf.set("SADDLE.floor", 800);
		conf.set("SADDLE.ceiling", 1200);
		conf.set("SADDLE.description", "Used to ride piggies!");
		conf.set("SADDLE.stock",50);
		conf.set("SADDLE.spread", 3.7);
		conf.set("SADDLE.time",0);

		conf.set("IRONDOOR.velocity", 1.9);
		conf.set("IRONDOOR.id", 330);
		conf.set("IRONDOOR.price", 300);
		conf.set("IRONDOOR.floor", 260);
		conf.set("IRONDOOR.ceiling", 340);
		conf.set("IRONDOOR.description", "Burglars won't be able to open this badboy!");
		conf.set("IRONDOOR.stock",50);
		conf.set("IRONDOOR.spread", 1.9);
		conf.set("IRONDOOR.time",0);

		conf.set("REDSTONE.velocity", .01);
		conf.set("REDSTONE.id", 331);
		conf.set("REDSTONE.price", 1);
		conf.set("REDSTONE.floor", .1);
		conf.set("REDSTONE.ceiling", 10);
		conf.set("REDSTONE.description", "Conducts power!");
		conf.set("REDSTONE.stock",50);
		conf.set("REDSTONE.spread", .01);
		conf.set("REDSTONE.time",0);

		conf.set("BOAT.velocity", .6);
		conf.set("BOAT.id", 333);
		conf.set("BOAT.price", 8);
		conf.set("BOAT.floor", 2);
		conf.set("BOAT.ceiling", 16);
		conf.set("BOAT.description", "Use this to ride the waves into newly generated land!");
		conf.set("BOAT.stock",50);
		conf.set("BOAT.spread", .6);
		conf.set("BOAT.time",0);

		conf.set("LEATHER.velocity", .4);
		conf.set("LEATHER.id", 334);
		conf.set("LEATHER.price", 5);
		conf.set("LEATHER.floor", 2);
		conf.set("LEATHER.ceiling", 8);
		conf.set("LEATHER.description", "The skin of cattle!");
		conf.set("LEATHER.stock",50);
		conf.set("LEATHER.spread", .4);
		conf.set("LEATHER.time",0);

		conf.set("MILK.velocity", 3.6);
		conf.set("MILK.id", 335);
		conf.set("MILK.price", 180);
		conf.set("MILK.floor", 150);
		conf.set("MILK.ceiling", 200);
		conf.set("MILK.description", "White, creamy goodness!");
		conf.set("MILK.stock",50);
		conf.set("MILK.spread", 3.6);
		conf.set("MILK.time",0);

		conf.set("BRICK.velocity", .3);
		conf.set("BRICK.id", 336);
		conf.set("BRICK.price", 5);
		conf.set("BRICK.floor", 2);
		conf.set("BRICK.ceiling", 9);
		conf.set("BRICK.description", "A brick of hardened clay!");
		conf.set("BRICK.stock",50);
		conf.set("BRICK.spread", .3);
		conf.set("BRICK.time",0);

		conf.set("CLAY.velocity", .25);
		conf.set("CLAY.id", 337);
		conf.set("CLAY.price", 4);
		conf.set("CLAY.floor", 1);
		conf.set("CLAY.ceiling", 9);
		conf.set("CLAY.description", "Moldable puddy!");
		conf.set("CLAY.stock",50);
		conf.set("CLAY.spread", .25);
		conf.set("CLAY.time",0);

		conf.set("SUGARCANE.velocity", .03);
		conf.set("SUGARCANE.id", 338);
		conf.set("SUGARCANE.price", .6);
		conf.set("SUGARCANE.floor", .1);
		conf.set("SUGARCANE.ceiling", 3);
		conf.set("SUGARCANE.description", "Raw sugar!");
		conf.set("SUGARCANE.stock",50);
		conf.set("SUGARCANE.spread", .03);
		conf.set("SUGARCANE.time",0);

		conf.set("PAPER.velocity", .07);
		conf.set("PAPER.id", 339);
		conf.set("PAPER.price", 1.8);
		conf.set("PAPER.floor", .4);
		conf.set("PAPER.ceiling", 5);
		conf.set("PAPER.description", "Good for taking notes!");
		conf.set("PAPER.stock",50);
		conf.set("PAPER.spread", .07);
		conf.set("PAPER.time",0);

		conf.set("BOOK.velocity", .09);
		conf.set("BOOK.id", 340);
		conf.set("BOOK.price", 5.4);
		conf.set("BOOK.floor", 2);
		conf.set("BOOK.ceiling", 9);
		conf.set("BOOK.description", "Good for reading.. except its blank!");
		conf.set("BOOK.stock",50);
		conf.set("BOOK.spread", .09);
		conf.set("BOOK.time",0);

		conf.set("SLIMEBALL.velocity", .5);
		conf.set("SLIMEBALL.id", 341);
		conf.set("SLIMEBALL.price", 40);
		conf.set("SLIMEBALL.floor", 20);
		conf.set("SLIMEBALL.ceiling", 80);
		conf.set("SLIMEBALL.description", "A chunk of a slime!");
		conf.set("SLIMEBALL.stock",50);
		conf.set("SLIMEBALL.spread", .5);
		conf.set("SLIMEBALL.time",0);

		conf.set("MINECARTWITHCHEST.velocity", 1.8);
		conf.set("MINECARTWITHCHEST.id", 342);
		conf.set("MINECARTWITHCHEST.price", 275);
		conf.set("MINECARTWITHCHEST.floor", 200);
		conf.set("MINECARTWITHCHEST.ceiling", 350);
		conf.set("MINECARTWITHCHEST.description", "Rapid transportation of items!");
		conf.set("MINECARTWITHCHEST.stock",50);
		conf.set("MINECARTWITHCHEST.spread", 1.8);
		conf.set("MINECARTWITHCHEST.time",0);

		conf.set("MINECARTWITHFURNACE.velocity", 1.08);
		conf.set("MINECARTWITHFURNACE.id", 343);
		conf.set("MINECARTWITHFURNACE.price", .04);
		conf.set("MINECARTWITHFURNACE.floor", .001);
		conf.set("MINECARTWITHFURNACE.ceiling", .3);
		conf.set("MINECARTWITHFURNACE.description", "Choo Choo!");
		conf.set("MINECARTWITHFURNACE.stock",50);
		conf.set("MINECARTWITHFURNACE.spread", 1.08);
		conf.set("MINECARTWITHFURNACE.time",0);

		conf.set("EGG.velocity", .1);
		conf.set("EGG.id", 344);
		conf.set("EGG.price", 3);
		conf.set("EGG.floor", 1);
		conf.set("EGG.ceiling", 9);
		conf.set("EGG.description", "Right out of the chickens beh- you know.");
		conf.set("EGG.stock",50);
		conf.set("EGG.spread", .1);
		conf.set("EGG.time",0);

		conf.set("COMPASS.velocity", 2.7);
		conf.set("COMPASS.id", 345);
		conf.set("COMPASS.price", 210);
		conf.set("COMPASS.floor", 150);
		conf.set("COMPASS.ceiling", 250);
		conf.set("COMPASS.description", "Shows you direction!");
		conf.set("COMPASS.stock",50);
		conf.set("COMPASS.spread", 2.7);
		conf.set("COMPASS.time",0);

		conf.set("FISHINGROD.velocity", 1.6);
		conf.set("FISHINGROD.id", 346);
		conf.set("FISHINGROD.price", 20);
		conf.set("FISHINGROD.floor", 5);
		conf.set("FISHINGROD.ceiling", 40);
		conf.set("FISHINGROD.description", "Reel in a big one, son!");
		conf.set("FISHINGROD.stock",50);
		conf.set("FISHINGROD.spread", 1.6);
		conf.set("FISHINGROD.time",0);

		conf.set("CLOCK.velocity", 12.3);
		conf.set("CLOCK.id", 347);
		conf.set("CLOCK.price", 2450);
		conf.set("CLOCK.floor", 1500);
		conf.set("CLOCK.ceiling", 3000);
		conf.set("CLOCK.description", "Can anyone say 'What time is it?' Not this thing!");
		conf.set("CLOCK.stock",50);
		conf.set("CLOCK.spread", 12.3);
		conf.set("CLOCK.time",0);

		conf.set("GLOWSTONEDUST.velocity", .1);
		conf.set("GLOWSTONEDUST.id", 348);
		conf.set("GLOWSTONEDUST.price", 1);
		conf.set("GLOWSTONEDUST.floor", .1);
		conf.set("GLOWSTONEDUST.ceiling", 10);
		conf.set("GLOWSTONEDUST.description", "A particle of glowstone!");
		conf.set("GLOWSTONEDUST.stock",50);
		conf.set("GLOWSTONEDUST.spread", .1);
		conf.set("GLOWSTONEDUST.time",0);

		conf.set("RAWFISH.velocity", .4);
		conf.set("RAWFISH.id", 349);
		conf.set("RAWFISH.price", 5);
		conf.set("RAWFISH.floor", 1);
		conf.set("RAWFISH.ceiling", 10);
		conf.set("RAWFISH.description", "Don't eat this unless you want Salmonella!");
		conf.set("RAWFISH.stock",50);
		conf.set("RAWFISH.spread", .4);
		conf.set("RAWFISH.time",0);

		conf.set("COOKEDFISH.velocity", .6);
		conf.set("COOKEDFISH.id", 350);
		conf.set("COOKEDFISH.price", 7.5);
		conf.set("COOKEDFISH.floor", 3);
		conf.set("COOKEDFISH.ceiling", 15);
		conf.set("COOKEDFISH.description", "Delicious fishy!");
		conf.set("COOKEDFISH.stock",50);
		conf.set("COOKEDFISH.spread", .6);
		conf.set("COOKEDFISH.time",0);

		// INKS BEGIN
		
		conf.set("INK.velocity", .8);
		conf.set("INK.id", 351);
		conf.set("INK.price", 7);
		conf.set("INK.floor", 2);
		conf.set("INK.ceiling", 13);
		conf.set("INK.description", "Black Ink, from a squid!");
		conf.set("INK.stock",50);
		conf.set("INK.spread", .8);
		conf.set("INK.time",0);
		
		conf.set("REDINK.velocity", .8);
		conf.set("REDINK.id", 351001);
		conf.set("REDINK.price", 7);
		conf.set("REDINK.floor", 2);
		conf.set("REDINK.ceiling", 13);
		conf.set("REDINK.description", "Red Ink, from the petals of a rose!");
		conf.set("REDINK.stock",50);
		conf.set("REDINK.spread", .8);
		conf.set("REDINK.time",0);
		
		conf.set("GREENINK.velocity", .8);
		conf.set("GREENINK.id", 351002);
		conf.set("GREENINK.price", 7);
		conf.set("GREENINK.floor", 2);
		conf.set("GREENINK.ceiling", 13);
		conf.set("GREENINK.description", "Green ink, from melted cacti!");
		conf.set("GREENINK.stock",50);
		conf.set("GREENINK.spread", .8);
		conf.set("GREENINK.time",0);
		
		conf.set("BROWNINK.velocity", .8);
		conf.set("BROWNINK.id", 351003);
		conf.set("BROWNINK.price", 7);
		conf.set("BROWNINK.floor", 2);
		conf.set("BROWNINK.ceiling", 13);
		conf.set("BROWNINK.description", "Brown ink, from the coloring of cocoa beans!");
		conf.set("BROWNINK.stock",50);
		conf.set("BROWNINK.spread", .8);
		conf.set("BROWNINK.time",0);
		
		conf.set("LAPISLAZULI.velocity", .8);
		conf.set("LAPISLAZULI.id", 351004);
		conf.set("LAPISLAZULI.price", 7);
		conf.set("LAPISLAZULI.floor", 2);
		conf.set("LAPISLAZULI.ceiling", 13);
		conf.set("LAPISLAZULI.description", "Prettiest ore in the world");
		conf.set("LAPISLAZULI.stock",50);
		conf.set("LAPISLAZULI.spread", .8);
		conf.set("LAPISLAZULI.time",0);
		
		conf.set("PURPLEINK.velocity", .8);
		conf.set("PURPLEINK.id", 351005);
		conf.set("PURPLEINK.price", 7);
		conf.set("PURPLEINK.floor", 2);
		conf.set("PURPLEINK.ceiling", 13);
		conf.set("PURPLEINK.description", "Purple ink!");
		conf.set("PURPLEINK.stock",50);
		conf.set("PURPLEINK.spread", .8);
		conf.set("PURPLEINK.time",0);
		
		conf.set("CYANINK.velocity", .8);
		conf.set("CYANINK.id", 351006);
		conf.set("CYANINK.price", 7);
		conf.set("CYANINK.floor", 2);
		conf.set("CYANINK.ceiling", 13);
		conf.set("CYANINK.description", "Cyan ink!");
		conf.set("CYANINK.stock",50);
		conf.set("CYANINK.spread", .8);
		conf.set("CYANINK.time",0);
		
		conf.set("LIGHTGRAYINK.velocity", .8);
		conf.set("LIGHTGRAYINK.id", 351007);
		conf.set("LIGHTGRAYINK.price", 7);
		conf.set("LIGHTGRAYINK.floor", 2);
		conf.set("LIGHTGRAYINK.ceiling", 13);
		conf.set("LIGHTGRAYINK.description", "Light gray ink!");
		conf.set("LIGHTGRAYINK.stock",50);
		conf.set("LIGHTGRAYINK.spread", .8);
		conf.set("LIGHTGRAYINK.time",0);
		
		conf.set("GRAYINK.velocity", .8);
		conf.set("GRAYINK.id", 351008);
		conf.set("GRAYINK.price", 7);
		conf.set("GRAYINK.floor", 2);
		conf.set("GRAYINK.ceiling", 13);
		conf.set("GRAYINK.description", "Gray ink!");
		conf.set("GRAYINK.stock",50);
		conf.set("GRAYINK.spread", .8);
		conf.set("GRAYINK.time",0);
		
		conf.set("PINKINK.velocity", .8);
		conf.set("PINKINK.id", 351009);
		conf.set("PINKINK.price", 7);
		conf.set("PINKINK.floor", 2);
		conf.set("PINKINK.ceiling", 13);
		conf.set("PINKINK.description", "Pink ink!");
		conf.set("PINKINK.stock",50);
		conf.set("PINKINK.spread", .8);
		conf.set("PINKINK.time",0);
		
		conf.set("LIMEINK.velocity", .8);
		conf.set("LIMEINK.id", 3510010);
		conf.set("LIMEINK.price", 7);
		conf.set("LIMEINK.floor", 2);
		conf.set("LIMEINK.ceiling", 13);
		conf.set("LIMEINK.description", "Lime ink!");
		conf.set("LIMEINK.stock",50);
		conf.set("LIMEINK.spread", .8);
		conf.set("LIMEINK.time",0);
		
		conf.set("YELLOWINK.velocity", .8);
		conf.set("YELLOWINK.id", 3510011);
		conf.set("YELLOWINK.price", 7);
		conf.set("YELLOWINK.floor", 2);
		conf.set("YELLOWINK.ceiling", 13);
		conf.set("YELLOWINK.description", "Yellow ink, from the petals of dandelions!");
		conf.set("YELLOWINK.stock",50);
		conf.set("YELLOWINK.spread", .8);
		conf.set("YELLOWINK.time",0);
		
		conf.set("LIGHTBLUEINK.velocity", .8);
		conf.set("LIGHTBLUEINK.id", 3510012);
		conf.set("LIGHTBLUEINK.price", 7);
		conf.set("LIGHTBLUEINK.floor", 2);
		conf.set("LIGHTBLUEINK.ceiling", 13);
		conf.set("LIGHTBLUEINK.description", "Light blue ink!");
		conf.set("LIGHTBLUEINK.stock",50);
		conf.set("LIGHTBLUEINK.spread", .8);
		conf.set("LIGHTBLUEINK.time",0);
		
		conf.set("MAGENTAINK.velocity", .8);
		conf.set("MAGENTAINK.id", 3510013);
		conf.set("MAGENTAINK.price", 7);
		conf.set("MAGENTAINK.floor", 2);
		conf.set("MAGENTAINK.ceiling", 13);
		conf.set("MAGENTAINK.description", "Magenta ink!");
		conf.set("MAGENTAINK.stock",50);
		conf.set("MAGENTAINK.spread", .8);
		conf.set("MAGENTAINK.time",0);
		
		conf.set("ORANGEINK.velocity", .8);
		conf.set("ORANGEINK.id", 3510014);
		conf.set("ORANGEINK.price", 7);
		conf.set("ORANGEINK.floor", 2);
		conf.set("ORANGEINK.ceiling", 13);
		conf.set("ORANGEINK.description", "Orange ink!");
		conf.set("ORANGEINK.stock",50);
		conf.set("ORANGEINK.spread", .8);
		conf.set("ORANGEINK.time",0);
		
		conf.set("BONEMEAL.velocity", .8);
		conf.set("BONEMEAL.id", 3510015);
		conf.set("BONEMEAL.price", 7);
		conf.set("BONEMEAL.floor", 2);
		conf.set("BONEMEAL.ceiling", 13);
		conf.set("BONEMEAL.description", "Crushed bone.");
		conf.set("BONEMEAL.stock",50);
		conf.set("BONEMEAL.spread", .8);
		conf.set("BONEMEAL.time",0);
		
		// INKS END

		conf.set("BONE.velocity", .7);
		conf.set("BONE.id", 352);
		conf.set("BONE.price", 5);
		conf.set("BONE.floor", 1);
		conf.set("BONE.ceiling", 10);
		conf.set("BONE.description", "Not at all creepy!");
		conf.set("BONE.stock",50);
		conf.set("BONE.spread", .7);
		conf.set("BONE.time",0);

		conf.set("SUGAR.velocity", .008);
		conf.set("SUGAR.id",353);
		conf.set("SUGAR.price", 1);
		conf.set("SUGAR.floor", .1);
		conf.set("SUGAR.ceiling", 5);
		conf.set("SUGAR.description", "Mmm, sweet and delicious; ready for baking!");
		conf.set("SUGAR.stock",50);
		conf.set("SUGAR.spread", .008);
		conf.set("SUGAR.time",0);

		conf.set("CAKE.velocity", 1.8);
		conf.set("CAKE.id", 354);
		conf.set("CAKE.price", 75);
		conf.set("CAKE.floor", 5);
		conf.set("CAKE.ceiling", 150);
		conf.set("CAKE.description", "This time, the cake is not a lie!");
		conf.set("CAKE.stock",50);
		conf.set("CAKE.spread", 1.8);
		conf.set("CAKE.time",0);

		conf.set("REPEATER.velocity", .7);
		conf.set("REPEATER.id", 356);
		conf.set("REPEATER.price", 4);
		conf.set("REPEATER.floor", 1);
		conf.set("REPEATER.ceiling", 15);
		conf.set("REPEATER.description", "Extends a RedStone current!");
		conf.set("REPEATER.stock",50);
		conf.set("REPEATER.spread", .7);
		conf.set("REPEATER.time",0);

		conf.set("COOKIE.velocity", 19.2);
		conf.set("COOKIE.id", 357);
		conf.set("COOKIE.price", 470);
		conf.set("COOKIE.floor", 300);
		conf.set("COOKIE.ceiling", 2000);
		conf.set("COOKIE.description", "A Deliciously rare bite-size snack!");
		conf.set("COOKIE.stock",50);
		conf.set("COOKIE.spread", 19.2);
		conf.set("COOKIE.time",0);

		conf.set("MAP.velocity", 1.6);
		conf.set("MAP.id", 358);
		conf.set("MAP.price", 35);
		conf.set("MAP.floor", 5);
		conf.set("MAP.ceiling", 50);
		conf.set("MAP.description", "Helps you make your way through the unknown!");
		conf.set("MAP.stock",50);
		conf.set("MAP.spread", 1.6);
		conf.set("MAP.time",0);

		conf.set("SHEARS.velocity", 1.3);
		conf.set("SHEARS.id", 359);
		conf.set("SHEARS.price", 100);
		conf.set("SHEARS.floor", 70);
		conf.set("SHEARS.ceiling", 130);
		conf.set("SHEARS.description", "Sheep beware!");
		conf.set("SHEARS.stock",50);
		conf.set("SHEARS.spread", 1.3);
		conf.set("SHEARS.time",0);

		conf.set("MELONSLICE.velocity", .35);
		conf.set("MELONSLICE.id", 360);
		conf.set("MELONSLICE.price", 2.5);
		conf.set("MELONSLICE.floor", .5);
		conf.set("MELONSLICE.ceiling", 8);
		conf.set("MELONSLICE.description", "Mmm, a summer treat!");
		conf.set("MELONSLICE.stock",50);
		conf.set("MELONSLICE.spread", .35);
		conf.set("MELONSLICE.time",0);

		conf.set("PUMPKINSEED.velocity", 18.3);
		conf.set("PUMPKINSEED.id", 361);
		conf.set("PUMPKINSEED.price", 800);
		conf.set("PUMPKINSEED.floor", 500);
		conf.set("PUMPKINSEED.ceiling", 5000);
		conf.set("PUMPKINSEED.description", "The seed of a pumpkin!");
		conf.set("PUMPKINSEED.stock",50);
		conf.set("PUMPKINSEED.spread", 18.3);
		conf.set("PUMPKINSEED.time",0);

		conf.set("MELONSEED.velocity", 21.3);
		conf.set("MELONSEED.id", 362);
		conf.set("MELONSEED.price", 800);
		conf.set("MELONSEED.floor", 500);
		conf.set("MELONSEED.ceiling", 5000);
		conf.set("MELONSEED.description", "The seed of a melon!");
		conf.set("MELONSEED.stock",50);
		conf.set("MELONSEED.spread", 21.3);
		conf.set("MELONSEED.time",0);

		conf.set("RAWBEEF.velocity", .6);
		conf.set("RAWBEEF.id", 363);
		conf.set("RAWBEEF.price", 8);
		conf.set("RAWBEEF.floor", 3);
		conf.set("RAWBEEF.ceiling", 15);
		conf.set("RAWBEEF.description", "Raw moo moo!");
		conf.set("RAWBEEF.stock",50);
		conf.set("RAWBEEF.spread", .6);
		conf.set("RAWBEEF.time",0);

		conf.set("STEAK.velocity", .5);
		conf.set("STEAK.id", 364);
		conf.set("STEAK.price", 9);
		conf.set("STEAK.floor", 4);
		conf.set("STEAK.ceiling", 16);
		conf.set("STEAK.description", "Ready to eat moo moo!");
		conf.set("STEAK.stock",50);
		conf.set("STEAK.spread", .5);
		conf.set("STEAK.time",0);

		conf.set("RAWCHICKEN.velocity", .6);
		conf.set("RAWCHICKEN.id", 365);
		conf.set("RAWCHICKEN.price", 8);
		conf.set("RAWCHICKEN.floor", 3);
		conf.set("RAWCHICKEN.ceiling", 15);
		conf.set("RAWCHICKEN.description", "Cocka-doodle-munch!");
		conf.set("RAWCHICKEN.stock",50);
		conf.set("RAWCHICKEN.spread", .6);
		conf.set("RAWCHICKEN.time",0);

		conf.set("COOKEDCHICKEN.velocity", .5);
		conf.set("COOKEDCHICKEN.id", 366);
		conf.set("COOKEDCHICKEN.price", 9);
		conf.set("COOKEDCHICKEN.floor", .4);
		conf.set("COOKEDCHICKEN.ceiling", .16);
		conf.set("COOKEDCHICKEN.description", "Cocka-doodle-devour!");
		conf.set("COOKEDCHICKEN.stock",50);
		conf.set("COOKEDCHICKEN.spread", .5);
		conf.set("COOKEDCHICKEN.time",0);

		conf.set("ROTTENFLESH.velocity", 1.7);
		conf.set("ROTTENFLESH.id", 367);
		conf.set("ROTTENFLESH.price", 15);
		conf.set("ROTTENFLESH.floor", 5);
		conf.set("ROTTENFLESH.ceiling", 40);
		conf.set("ROTTENFLESH.description", "Gross. Poisonous zombie flesh!");
		conf.set("ROTTENFLESH.stock",50);
		conf.set("ROTTENFLESH.spread", 1.7);
		conf.set("ROTTENFLESH.time",0);

		conf.set("ENDERPEARL.velocity", 1.9);
		conf.set("ENDERPEARL.id", 368);
		conf.set("ENDERPEARL.price", 125);
		conf.set("ENDERPEARL.floor", 50);
		conf.set("ENDERPEARL.ceiling", 500);
		conf.set("ENDERPEARL.description", "A pearl of darkness!");
		conf.set("ENDERPEARL.stock",50);
		conf.set("ENDERPEARL.spread", 1.9);
		conf.set("ENDERPEARL.time",0);

		conf.set("BLAZEROD.velocity", 4.3);
		conf.set("BLAZEROD.id", 369);
		conf.set("BLAZEROD.price", 250);
		conf.set("BLAZEROD.floor", 75);
		conf.set("BLAZEROD.ceiling", 5000);
		conf.set("BLAZEROD.description", "A rod from the fiery guardians of hell!");
		conf.set("BLAZEROD.stock",50);
		conf.set("BLAZEROD.spread", 4.3);
		conf.set("BLAZEROD.time",0);

		conf.set("GHASTTEAR.velocity", 6.1);
		conf.set("GHASTTEAR.id", 370);
		conf.set("GHASTTEAR.price", 150);
		conf.set("GHASTTEAR.floor", 50);
		conf.set("GHASTTEAR.ceiling", 1000);
		conf.set("GHASTTEAR.description", "The tear of a very depressed, very insane creature!");
		conf.set("GHASTTEAR.stock",50);
		conf.set("GHASTTEAR.spread", 6.1);
		conf.set("GHASTTEAR.time",0);

		conf.set("GOLDNUGGET.velocity", .9);
		conf.set("GOLDNUGGET.id", 371);
		conf.set("GOLDNUGGET.price", 33.33);
		conf.set("GOLDNUGGET.floor", 15);
		conf.set("GOLDNUGGET.ceiling", 60);
		conf.set("GOLDNUGGET.description", "A small piece of gold!");
		conf.set("GOLDNUGGET.stock",50);
		conf.set("GOLDNUGGET.spread", .9);
		conf.set("GOLDNUGGET.time",0);

		conf.set("NETHERWART.velocity", 3.7);
		conf.set("NETHERWART.id", 372);
		conf.set("NETHERWART.price", 150);
		conf.set("NETHERWART.floor", 50);
		conf.set("NETHERWART.ceiling", 900);
		conf.set("NETHERWART.description", "A hell-ridden plant!");
		conf.set("NETHERWART.stock",50);
		conf.set("NETHERWART.spread", 3.7);
		conf.set("NETHERWART.time",0);

		conf.set("GLASSBOTTLE.velocity", .09);
		conf.set("GLASSBOTTLE.id", 374);
		conf.set("GLASSBOTTLE.price", 2);
		conf.set("GLASSBOTTLE.floor", .1);
		conf.set("GLASSBOTTLE.ceiling", 7);
		conf.set("GLASSBOTTLE.description", "A bottle to make potions in!");
		conf.set("GLASSBOTTLE.stock",50);
		conf.set("GLASSBOTTLE.spread", .09);
		conf.set("GLASSBOTTLE.time",0);

		conf.set("SPIDEREYE.velocity", .9);
		conf.set("SPIDEREYE.id", 375);
		conf.set("SPIDEREYE.price", 10);
		conf.set("SPIDEREYE.floor", 1);
		conf.set("SPIDEREYE.ceiling", 50);
		conf.set("SPIDEREYE.description", "I see 8 hairy legs!");
		conf.set("SPIDEREYE.stock",50);
		conf.set("SPIDEREYE.spread", .9);
		conf.set("SPIDEREYE.time",0);

		
		


		
		try {
			conf.save(itemsFile);
			log.info("[DynamicEconomy] Config created!");
		} catch (Exception e) {
			log.info("[IOEXCEPTION] Items Config not saved.");
			log.info(e.toString());
		}
		
		
		
	}
	
	public Initialize(FileConfiguration conf, File confFile) {
		config = conf;
		configFile = confFile;
	}
	
	public void setItemsData(FileConfiguration config, File items) {
		conf = config;
		itemsFile = items;
	}
	
	public static void setConfigFile(File confFile) {
		configFile = confFile;
	}
	
	
}
