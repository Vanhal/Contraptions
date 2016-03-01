package tv.vanhal.contraptions.client.intergration.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.GUIHelper;
import tv.vanhal.contraptions.util.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrusherRecipeWrapper implements IRecipeWrapper {
	private CrusherRecipes recipe;
	
	public CrusherRecipeWrapper(CrusherRecipes recipe) {
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
		int time = recipe.getTimes();
		GUIHelper.DrawStringCentered(minecraft.fontRendererObj, ""+time, 84, 22, Colours.BLACK);
		if (time==1) GUIHelper.DrawStringCentered(minecraft.fontRendererObj, StringHelper.localize("gui.time"), 84, 34, Colours.BLACK);
		else GUIHelper.DrawStringCentered(minecraft.fontRendererObj, StringHelper.localize("gui.times"), 84, 34, Colours.BLACK);
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
