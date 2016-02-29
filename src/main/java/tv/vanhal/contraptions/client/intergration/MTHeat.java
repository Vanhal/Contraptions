package tv.vanhal.contraptions.client.intergration;
/*
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.world.heat.HeatHandlers;

@ZenClass("mod.contraptions.Heat")
public class MTHeat {

	@ZenMethod
	public static void addRecipe(@NotNull IItemStack output, @NotNull IItemStack input, int heat) {
		MineTweakerAPI.apply(new AddRecipeAction(getItemStack(output), getItemStack(input), heat, false));
	}
	
	@ZenMethod
	public static void addPlacedRecipe(@NotNull IItemStack output, @NotNull IItemStack input, int heat) {
		MineTweakerAPI.apply(new AddRecipeAction(getItemStack(output), getItemStack(input), heat, true));
	}
	
	@ZenMethod
	public static void remove(@NotNull IItemStack output) {
		removeRecipe(output, null);
	}
	
	@ZenMethod
	public static void removeRecipe(@NotNull IItemStack output, @Optional IItemStack input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(getItemStack(output), input));
	}
	
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final ItemStack output;
		private final HeatRecipes recipe;
		private boolean placed;
		
		public AddRecipeAction(ItemStack _output, ItemStack _input, int _heat, boolean _placed) {
			output = _output;
			placed = _placed;
			if (placed) {
				Block outputBlock = Block.getBlockFromItem(_output.getItem());
				Block inputBlock = Block.getBlockFromItem(_input.getItem());
			
				recipe = new HeatRecipes(outputBlock, inputBlock, _heat);
			} else {
				recipe = new HeatRecipes(_output, _input, _heat);
			}
		}

		@Override
		public void apply() {
			RecipeManager.registerHeatRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (placed) HeatHandlers.removeRecipeHandler(recipe.getInputBlock());
			RecipeManager.heat.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding heat recipe for " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing heat recipe for " + output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final ItemStack output;
		private ArrayList<HeatRecipes> recipes = new ArrayList<HeatRecipes>();
		
		public RemoveRecipeAction(ItemStack _output, IItemStack _input) {
			output = _output;
			ItemStack input = null;
			if (_input!=null) {
				input = getItemStack(_input);
			}
			if (output!=null) {
				for (HeatRecipes test : RecipeManager.heat) {
					if (test.getOutput().isItemEqual(_output)) {
						if (input==null) {
							recipes.add(test);
						} else if (test.getInput().isItemEqual(input)) {
							recipes.add(test);
						}
					}
				}
			}
		}

		@Override
		public void apply() {
			for (HeatRecipes recipe: recipes) {
				if (recipe != null) {
					if (recipe.isBlockRecipe()) HeatHandlers.removeRecipeHandler(recipe.getInputBlock());
					RecipeManager.heat.remove(recipe);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (HeatRecipes recipe: recipes) {
				if (recipe != null)
					RecipeManager.registerHeatRecipe(recipe);
			}
		}

		@Override
		public String describe() {
			return "Removing heat recipe for " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Adding heat recipe for " + output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
*/