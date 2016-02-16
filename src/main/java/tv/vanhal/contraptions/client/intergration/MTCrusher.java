package tv.vanhal.contraptions.client.intergration;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.crafting.CrusherRecipes;
import tv.vanhal.contraptions.crafting.RecipeManager;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

@ZenClass("mod.contraptions.Crusher")
public class MTCrusher {

	@ZenMethod
	public static void addRecipe(@NotNull IItemStack output, @NotNull IItemStack input, int times) {
		MineTweakerAPI.apply(new AddRecipeAction(getItemStack(output), getItemStack(input), times));
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
		private final CrusherRecipes recipe;
		
		public AddRecipeAction(ItemStack _output, ItemStack _input, int _times) {
			output = _output;
			recipe = new CrusherRecipes(_output, _input, _times);
		}

		@Override
		public void apply() {
			RecipeManager.registerCrusherRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RecipeManager.crusher.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding crusher recipe for " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing crusher recipe for " + output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final ItemStack output;
		private ArrayList<CrusherRecipes> recipes = new ArrayList<CrusherRecipes>();
		
		public RemoveRecipeAction(ItemStack _output, IItemStack _input) {
			output = _output;
			ItemStack input = null;
			if (_input!=null) {
				input = getItemStack(_input);
			}
			if (output!=null) {
				for (CrusherRecipes test : RecipeManager.crusher) {
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
			for (CrusherRecipes recipe: recipes) {
				if (recipe != null)
					RecipeManager.crusher.remove(recipe);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (CrusherRecipes recipe: recipes) {
				if (recipe != null)
					RecipeManager.registerCrusherRecipe(recipe);
			}
		}

		@Override
		public String describe() {
			return "Removing crusher recipe for " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Adding crusher recipe for " + output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
