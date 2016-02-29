package tv.vanhal.contraptions.compat;

import java.util.ArrayList;

import tv.vanhal.contraptions.compat.mods.Vanilla;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModHelper {

	//mod registry
	private static ArrayList<BaseMod> modsLoaded = new ArrayList<BaseMod>();
	
	private static void registerMods() {
		/*registerMod(new ImmersiveEngineering());
		registerMod(new Pams());
		registerMod(new Pneumaticcraft());
		registerMod(new MFR());
		registerMod(new AgriCraft());
		registerMod(new GrowOres());
		registerMod(new ThaumCraft());*/
		
		//vanilla should always be registered last since it's the main class
		//at the same time it probably should be loaded first....
		registerMod(new Vanilla());
	}
	
	public static void registerMod(BaseMod mod) {
		if (mod.shouldLoad()) {
			modsLoaded.add(mod);
		}
	}
	
	//initialisation function
	public static void init() {
		registerMods();
	}
	
	
	//check is an item is plantible in the planter
	public static boolean isPlantible(ItemStack item) {
		for (BaseMod mod: modsLoaded) {
			if (mod.isPlantible(item)) return true;
		}
		return false;
	}
	
		
	//check is an item is plantible in the planter
	public static boolean shouldHoe(ItemStack item) {
		for (BaseMod mod: modsLoaded) {
			if (mod.isPlantible(item)) {
				return mod.shouldHoe(item);
			}
		}
		return false;
	}
	
	//check if a block in the world is an ungrown plant	
	public static boolean isPlant(Block plantBlock, IBlockState state) {
		for (BaseMod mod: modsLoaded) {
			if (mod.isPlant(plantBlock, state)) return true;
		}
		return false;
	}

	
	//check if the ground is valid for planting the given seed
	public static boolean validBlock(World worldObj, ItemStack itemStack, Point3I testPoint) {
		for (BaseMod mod: modsLoaded) {
			if (mod.validBlock(worldObj, itemStack, testPoint)) return true;
		}
		return false;
	}
	
	//plant seed, return false if seed was not planted
	public static boolean placeSeed(World worldObj, ItemStack itemStack, Point3I testPoint, boolean doAction) {
		for (BaseMod mod: modsLoaded) {
			if (mod.validBlock(worldObj, itemStack, testPoint)) {
				if (mod.placeSeed(worldObj, itemStack, testPoint, doAction)) {
					return true;
				}
			}
		}
		return false;
	}

}
