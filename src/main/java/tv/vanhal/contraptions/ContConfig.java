package tv.vanhal.contraptions;

import net.minecraftforge.common.config.Configuration;

public class ContConfig {
	public static Configuration config;
	
	//config values
	public static int HEAT_LOSS_PER_TICK;
	public static int TICKS_PER_HEAT_TICK;
	public static int WATER_BOIL_HEAT;
	
	public static int BASE_HEAT_PER_TICK;
	
	public static void init(Configuration handle) {
		config = handle;
		config.load();
		
		config.addCustomCategoryComment("general", "General Settings");
		config.addCustomCategoryComment("heat", "Heat Registry Settings");
		
		
		syncConfig();
	}
	
	public static void syncConfig() {
		TICKS_PER_HEAT_TICK = config.getInt("TicksPerHeatTick", "heat", 5, 1, 120, "How many ticks should a heat transfer tick take place");
		HEAT_LOSS_PER_TICK = config.getInt("HeatLossPerTick", "heat", 1, 0, 100, "How much heat is lost from each block every Heat Tick");
		WATER_BOIL_HEAT = config.getInt("WaterBoilHeat", "heat", 500, 1, 1000, "How much heat is required to turn water into steam");
		
		BASE_HEAT_PER_TICK = config.getInt("BaseHeatPerTick", "heat", 4, 1, 100, "The amount of heat that generating machines generate per game tick");
		
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
