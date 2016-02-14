package tv.vanhal.contraptions.client.intergration;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.GUIHelper;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.util.StringHelper;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.PositionedStack;

public class NEIRecipeHandler extends TemplateRecipeHandler {
	
	public class CachedCrusherRecipe extends CachedRecipe {

		public List<PositionedStack> inputs = new ArrayList<PositionedStack>();
		public PositionedStack output;
		public int times;

		public CachedCrusherRecipe(CrusherRecipes recipe) {
			if(recipe == null) return;
			inputs.add(new PositionedStack(recipe.getInput(), 18, 24));
			output = new PositionedStack(recipe.getOutput(), 132, 24);
			times = recipe.getTimes();
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
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> otherStacks = new ArrayList<PositionedStack>();
			otherStacks.add(new PositionedStack(new ItemStack(Blocks.piston), 76, 2));
			otherStacks.add(new PositionedStack(new ItemStack(ContBlocks.crusher), 76, 46));
			return otherStacks;
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
		return StringHelper.localize("tile.crusher.name");
	}

	@Override
	public String getGuiTexture() {
		return Ref.MODID+":textures/gui/neiCrusher.png";
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(102, 22, 27, 20), "contraptions.crusher"));
		transferRects.add(new RecipeTransferRect(new Rectangle(37, 23, 26, 19), "contraptions.crusher"));
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
		int time = ((CachedCrusherRecipe) this.arecipes.get(recipe)).times;
		GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, ""+time, 84, 22, Colours.BLACK);
		if (time==1) GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, StringHelper.localize("gui.time"), 84, 34, Colours.BLACK);
		else GUIHelper.DrawStringCentered(GuiDraw.fontRenderer, StringHelper.localize("gui.times"), 84, 34, Colours.BLACK);
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if(outputId.equals("contraptions.crusher"))
			for(CrusherRecipes recipe : RecipeManager.crusher)
				arecipes.add(new CachedCrusherRecipe(recipe));
		else super.loadCraftingRecipes(outputId, results);
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for(CrusherRecipes recipe : RecipeManager.crusher) {
			if(recipe == null) continue;
			if(result.isItemEqual(recipe.getOutput()))
				arecipes.add(new CachedCrusherRecipe(recipe));
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for(CrusherRecipes recipe : RecipeManager.crusher) {
			if(recipe == null) continue;
			
			if (recipe.matches(ingredient)) {
				arecipes.add(new CachedCrusherRecipe(recipe));
			}
		}
	}

}
