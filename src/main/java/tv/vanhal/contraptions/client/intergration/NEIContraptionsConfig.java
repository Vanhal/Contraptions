package tv.vanhal.contraptions.client.intergration;

import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.util.Ref;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIContraptionsConfig implements IConfigureNEI {

	@Override
	public String getName() {
		return Ref.MODNAME;
	}

	@Override
	public String getVersion() {
		return Ref.Version;
	}

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new NEICrusherRecipeHandler());
		API.registerUsageHandler(new NEICrusherRecipeHandler());
		
		API.registerRecipeHandler(new NEIHeatRecipeHandler());
		API.registerUsageHandler(new NEIHeatRecipeHandler());
		
		API.hideItem(new ItemStack(ContBlocks.pulse));
	}

}
