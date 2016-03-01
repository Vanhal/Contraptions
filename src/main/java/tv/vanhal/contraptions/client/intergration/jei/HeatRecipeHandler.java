package tv.vanhal.contraptions.client.intergration.jei;

import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.util.Ref;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HeatRecipeHandler implements IRecipeHandler<HeatRecipes> {

	@Override
	public Class<HeatRecipes> getRecipeClass() {
		return HeatRecipes.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return Ref.MODID.toLowerCase()+".heat";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(HeatRecipes recipe) {
		return new HeatRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(HeatRecipes recipe) {
		return (recipe.getInput() != null) && (recipe.getOutput() != null) && (recipe.getRequiredHeat()>0);
	}

}
