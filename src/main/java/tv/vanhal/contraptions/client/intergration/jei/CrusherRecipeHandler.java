package tv.vanhal.contraptions.client.intergration.jei;

import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.util.Ref;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipes> {

	@Override
	public Class<CrusherRecipes> getRecipeClass() {
		return CrusherRecipes.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return Ref.MODID.toLowerCase()+".crusher";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CrusherRecipes recipe) {
		return new CrusherRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(CrusherRecipes recipe) {
		return (recipe.getInput() != null) && (recipe.getOutput() != null) && (recipe.getTimes()>0);
	}

}
