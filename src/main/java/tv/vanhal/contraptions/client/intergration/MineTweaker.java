package tv.vanhal.contraptions.client.intergration;

import minetweaker.MineTweakerAPI;
import tv.vanhal.contraptions.Contraptions;

public class MineTweaker {

	public static void init() {
		Contraptions.logger.info("MineTweaker Loaded. Adding recipe handlers");
		
		MineTweakerAPI.registerClass(MTCrusher.class);
		MineTweakerAPI.registerClass(MTHeat.class);
	}

}
