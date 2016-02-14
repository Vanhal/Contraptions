package tv.vanhal.contraptions.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.resources.ItemCrushedIron;
import tv.vanhal.contraptions.items.resources.ItemMoltenIron;
import tv.vanhal.contraptions.items.resources.ItemPlateIron;
import tv.vanhal.contraptions.items.resources.ItemRFCore;

public class ContItems {

	public static void preInit() {
		screwDriver.preInit();
		
		//resources
		crushedIron.preInit();
		moltenIron.preInit();
		plateIron.preInit();
		rfCore.preInit();
	}
	
	public static void init() {
		
	}
	
	public static void postInit() {
		addCrusherRecipes();
	}
	
	public static ItemScrewDriver screwDriver = new ItemScrewDriver();
	
	//resources
	public static ItemCrushedIron crushedIron = new ItemCrushedIron();
	public static ItemMoltenIron moltenIron = new ItemMoltenIron();
	public static ItemPlateIron plateIron = new ItemPlateIron();
	public static ItemRFCore rfCore = new ItemRFCore();
	
	
	public static void addCrusherRecipes() {
		RecipeManager.registerCrusherRecipe(new ItemStack(Items.diamond), new ItemStack(Blocks.coal_block), 6000);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 2);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.cobblestone), 8);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel), 16);
		RecipeManager.registerCrusherRecipe(new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sandstone), 8);
		
	}
}
