package tv.vanhal.contraptions.client.intergration.jei;

import java.util.List;

import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.GUIHelper;
import tv.vanhal.contraptions.util.StringHelper;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HeatRecipeWrapper implements IRecipeWrapper {
	private HeatRecipes recipe;
	
	public HeatRecipeWrapper(HeatRecipes recipe) {
		if(recipe == null) return;
		this.recipe = recipe;
	}
	
	@Override
	public List getInputs() {
		return ImmutableList.of(recipe.getInput());
	}

	@Override
	public List getOutputs() {
		return ImmutableList.of(recipe.getOutput());
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return ImmutableList.of();
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return ImmutableList.of();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {

	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (recipe.isBlockRecipe())
			GUIHelper.DrawStringCentered(minecraft.fontRendererObj, StringHelper.localize("gui.placed"), 26, 34, Colours.BLACK);
		
		int heat = recipe.getRequiredHeat();
		GUIHelper.DrawStringCentered(minecraft.fontRendererObj, ""+heat, 84, 12, Colours.RED);
		GUIHelper.DrawStringCentered(minecraft.fontRendererObj, StringHelper.localize("gui.heat"), 84, 24, Colours.RED);
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {

	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return ImmutableList.of();
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

}
