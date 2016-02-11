package tv.vanhal.contraptions;

import net.minecraftforge.common.config.Configuration;

public class ContConfig {
	public static Configuration config;
	
	//config values
	public static int HEAT_LOSS_PER_TICK;
	public static int TICKS_PER_HEAT_TICK;
	public static int WATER_BOIL_HEAT;
	
	public static int BASE_HEAT_PER_TICK;
	
	public static int STEAM_CONSUMED_PER_TICK;
	public static int STEAM_NEEDED_PER_ROTATION;
	public static int RF_PER_ROTATION;
	
	public static void init(Configuration handle) {
		config = handle;
		config.load();
		
		config.addCustomCategoryComment("general", "General Settings");
		config.addCustomCategoryComment("heatAndPower", "Heat Registry Settings");
		
		
		syncConfig();
	}
	
	public static void syncConfig() {
		TICKS_PER_HEAT_TICK = config.getInt("TicksPerHeatTick", "heatAndPower", 5, 1, 120, "How many ticks should a heat transfer tick take place");
		HEAT_LOSS_PER_TICK = config.getInt("HeatLossPerTick", "heatAndPower", 1, 0, 100, "How much heat is lost from each block every Heat Tick");
		WATER_BOIL_HEAT = config.getInt("WaterBoilHeat", "heatAndPower", 500, 1, 1000, "How much heat is required to turn water into steam");
		
		BASE_HEAT_PER_TICK = config.getInt("BaseHeatPerTick", "heatAndPower", 4, 1, 100, "The amount of heat that generating machines generate per game tick");
		
		//one turbine should produce 5RF/tick by default
		STEAM_CONSUMED_PER_TICK = config.getInt("SteamConsumedPerTick", "heatAndPower", 10, 1, 1000, "The amount of steam that the turbines consume per tick");
		RF_PER_ROTATION = config.getInt("RFperRotation", "heatAndPower", 5, 1, 1000, "The amount of RF produced per rotation per tick");
		STEAM_NEEDED_PER_ROTATION = config.getInt("SteamNeededPerRotation", "heatAndPower", 10, 1, 1000, "The amount of steam (in mBucket) of steam needed for 1 rotation");

		//save if changed
		if (config.hasChanged()) save();
	}
	
	public static void save() {
		config.save();
	}
	
	public static void postInit() {
		save();
	}
}
