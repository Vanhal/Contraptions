package tv.vanhal.contraptions.crafting;

import tv.vanhal.contraptions.items.ContItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes {

	public static void register() {
		addCrusherRecipes();
		addHeatRecipes();
		
	}
	
	public static void addCrusherRecipes() {
		RecipeManager.registerCrusherRecipe(new ItemStack(Items.diamond), new ItemStack(Blocks.coal_block), 6000);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 2);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.cobblestone), 8);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel), 16);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sandstone), 8);
	}
	
	public static void addHeatRecipes() {
		RecipeManager.registerHeatRecipe(Blocks.lava, Blocks.cobblestone, 5000);
		RecipeManager.registerHeatRecipe(Blocks.flowing_lava, Blocks.cobblestone, 5000);
		RecipeManager.registerHeatRecipe(new ItemStack(ContItems.moltenIron), new ItemStack(Items.iron_ingot), 400);
	}
}
