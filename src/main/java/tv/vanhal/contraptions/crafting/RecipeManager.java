package tv.vanhal.contraptions.crafting;

import java.util.ArrayList;

import tv.vanhal.contraptions.world.heat.HeatHandlers;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeManager {
	public static ArrayList<CrusherRecipes> crusher = new ArrayList<CrusherRecipes>();
	public static ArrayList<HeatRecipes> heat = new ArrayList<HeatRecipes>();
	
	//crusher recipes
	public static CrusherRecipes registerCrusherRecipe(ItemStack output, ItemStack input, int times) {
		CrusherRecipes recipe = new CrusherRecipes(output, input, times);
		return registerCrusherRecipe(recipe);
	}
	
	public static CrusherRecipes registerCrusherRecipe(CrusherRecipes recipe) {
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
	
	//heat recipes
	public static HeatRecipes registerHeatRecipe(HeatRecipes recipe) {
		heat.add(recipe);
		if (recipe.isBlockRecipe()) {
			HeatHandlers.registerRecipeHandler(recipe.getInputBlock(), recipe);
		}
		return recipe;
	}
	
	public static HeatRecipes registerHeatRecipe(ItemStack output, ItemStack input, int heatRequired) {
		HeatRecipes recipe = new HeatRecipes(output, input, heatRequired);
		return registerHeatRecipe(recipe);
	}
	
	public static HeatRecipes registerHeatRecipe(Block output, Block input, int heatRequired) {
		HeatRecipes recipe = new HeatRecipes(output, input, heatRequired);
		return registerHeatRecipe(recipe);
	}
	
	public static ItemStack getHeatOutput(ItemStack input) {
		for (HeatRecipes recipe : heat) {
			if ( (!recipe.isBlockRecipe()) && (recipe.matches(input)) ) {
				return recipe.getOutput();
			}
		}
		return null;
	}
	
	public static int getHeatRequired(ItemStack input) {
		for (HeatRecipes recipe : heat) {
			if ( (!recipe.isBlockRecipe()) && (recipe.matches(input)) ) {
				return recipe.getRequiredHeat();
			}
		}
		return 0;
	}
}
