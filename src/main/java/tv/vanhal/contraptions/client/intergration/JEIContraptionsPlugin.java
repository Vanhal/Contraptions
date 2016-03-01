package tv.vanhal.contraptions.client.intergration;

import tv.vanhal.contraptions.client.intergration.jei.CrusherRecipeCategory;
import tv.vanhal.contraptions.client.intergration.jei.CrusherRecipeHandler;
import tv.vanhal.contraptions.client.intergration.jei.HeatRecipeCategory;
import tv.vanhal.contraptions.client.intergration.jei.HeatRecipeHandler;
import tv.vanhal.contraptions.crafting.RecipeManager;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEIContraptionsPlugin implements IModPlugin {

	private IJeiHelpers jeiHelpers;

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
			this.jeiHelpers = jeiHelpers;
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {

	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeCategories(
				new CrusherRecipeCategory(jeiHelpers.getGuiHelper()),
				new HeatRecipeCategory(jeiHelpers.getGuiHelper())
		);
		
		registry.addRecipeHandlers(
				new CrusherRecipeHandler(),
				new HeatRecipeHandler()
		);
		
		registry.addRecipes(RecipeManager.crusher);
		registry.addRecipes(RecipeManager.heat);
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

	}

}
