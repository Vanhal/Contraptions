package tv.vanhal.contraptions.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class RecipeManager {
	public static ArrayList<CrusherRecipes> crusher = new ArrayList<CrusherRecipes>();
	
	public static CrusherRecipes registerCrusherRecipe(ItemStack output, ItemStack input, int times) {
		CrusherRecipes recipe = new CrusherRecipes(output, input, times);
		crusher.add(recipe);
		return recipe;
	}
	
	public static ItemStack getCrusherOutput(ItemStack input) {
		for (CrusherRecipes recipe : crusher) {
			if (recipe.matches(input)) {
				return recipe.getOutput();
			}
		}
		return null;
	}
	
	public static int getCrusherTimes(ItemStack input) {
		for (CrusherRecipes recipe : crusher) {
			if (recipe.matches(input)) {
				return recipe.getTimes();
			}
		}
		return 0;
	}
}
