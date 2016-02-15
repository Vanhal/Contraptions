package tv.vanhal.contraptions.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.resources.ItemCrushedIron;
import tv.vanhal.contraptions.items.resources.ItemGlassLens;
import tv.vanhal.contraptions.items.resources.ItemLayeredGlass;
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
		layeredGlass.preInit();
		glassLens.preInit();
	}
	
	public static void init() {
		
	}
	
	public static void postInit() {
		
	}
	
	public static ItemScrewDriver screwDriver = new ItemScrewDriver();
	
	//resources
	public static ItemCrushedIron crushedIron = new ItemCrushedIron();
	public static ItemMoltenIron moltenIron = new ItemMoltenIron();
	public static ItemPlateIron plateIron = new ItemPlateIron();
	public static ItemRFCore rfCore = new ItemRFCore();
	public static ItemLayeredGlass layeredGlass = new ItemLayeredGlass();
	public static ItemGlassLens glassLens = new ItemGlassLens();

}
