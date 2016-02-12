package tv.vanhal.contraptions.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.crafting.RecipeManager;

public class ContItems {

	public static void preInit() {
		screwDriver.preInit();
	}
	
	public static void init() {
		
	}
	
	public static void postInit() {
		RecipeManager.registerCrusherRecipe(new ItemStack(Items.diamond), new ItemStack(Blocks.coal_block), 1);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 4);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.cobblestone), 4);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel), 8);
	}
	
	public static ItemScrewDriver screwDriver = new ItemScrewDriver();
}
