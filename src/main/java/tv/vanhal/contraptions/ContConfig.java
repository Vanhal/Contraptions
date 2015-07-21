package tv.vanhal.contraptions;

import net.minecraftforge.common.config.Configuration;

public class ContConfig {
	public static Configuration config;
	
	public static void init(Configuration handle) {
		config = handle;
		config.load();
		
		config.addCustomCategoryComment("general", "General Settings");
		
		
		syncConfig();
	}
	
	public static void syncConfig() {
		
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
