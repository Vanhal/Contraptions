package tv.vanhal.contraptions.client.intergration;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.client.intergration.NEICrusherRecipeHandler.CachedCrusherRecipe;
import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.GUIHelper;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.util.StringHelper;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class NEIHeatRecipeHandler extends TemplateRecipeHandler {
	public class CachedHeatRecipe extends CachedRecipe {

		public List<PositionedStack> inputs = new ArrayList<PositionedStack>();
		public PositionedStack output;
		public int heatRequired;
		public boolean isBlockRecipe;

		public CachedHeatRecipe(HeatRecipes recipe) {
			if(recipe == null) return;
			inputs.add(new PositionedStack(recipe.getInput(), 18, 24));
			output = new PositionedStack(recipe.getOutput(), 132, 24);
			heatRequired = recipe.getRequiredHeat();
			isBlockRecipe = recipe.isBlockRecipe();
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return inputs;
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public boolean contains(Collection<PositionedStack> ingredients, ItemStack ingredient) {
			if(ingredients == inputs) {
				boolean skippedPool = false;

				for(PositionedStack stack : ingredients) {
					if(!skippedPool) {
						skippedPool = true;
						continue;
					}

					if(stack.contains(ingredient))
						return true;
				}
			}

			return super.contains(ingredients, ingredient);
		}

	}
	
	
	@Override
	public String getRecipeName() {
		return StringHelper.localize("gui.heat")+" "+StringHelper.localize("gui.recipes");
	}

	@Override
	public String getGuiTexture() {
		return Ref.MODID+":textures/gui/neiCrusher.png";
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(102, 22, 27, 20), "contraptions.heat"));
		transferRects.add(new RecipeTransferRect(new Rectangle(37, 23, 26, 19), "contraptions.heat"));
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
		if (((CachedHeatRecipe) this.arecipes.get(recipe)).isBlockRecipe)
			GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, StringHelper.localize("gui.placed"), 26, 44, Colours.BLACK);
		int heat = ((CachedHeatRecipe) this.arecipes.get(recipe)).heatRequired;
		GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, ""+heat, 84, 22, Colours.RED);
		GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, StringHelper.localize("gui.heat"), 84, 34, Colours.RED);
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if(outputId.equals("contraptions.heat"))
			for(HeatRecipes recipe : RecipeManager.heat)
				arecipes.add(new CachedHeatRecipe(recipe));
		else super.loadCraftingRecipes(outputId, results);
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for(HeatRecipes recipe : RecipeManager.heat) {
			if(recipe == null) continue;
			if(result.isItemEqual(recipe.getOutput()))
				arecipes.add(new CachedHeatRecipe(recipe));
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for(HeatRecipes recipe : RecipeManager.heat) {
			if ( (recipe == null) || (ingredient.getItem() == null) ) continue;
			
			if ( (recipe.matches(ingredient)) || (recipe.matches(Block.getBlockFromItem(ingredient.getItem()))) ) {
				arecipes.add(new CachedHeatRecipe(recipe));
			}
		}
	}
}
