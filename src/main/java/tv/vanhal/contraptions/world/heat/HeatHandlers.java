package tv.vanhal.contraptions.world.heat;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.interfaces.IHeatBlockHandler;
import tv.vanhal.contraptions.util.Point3I;

public class HeatHandlers {
	private static TMap<Block, IHeatBlockHandler> validHeatBlocks = new THashMap<Block, IHeatBlockHandler>();
	
	public static void registerHandler(Block block, IHeatBlockHandler handler) {
		validHeatBlocks.put(block, handler);
	}
	
	public static void registerRecipeHandler(Block block, HeatRecipes recipe) {
		registerHandler(block, new HandlerRecipes(recipe));
	}
	
	public static void removeRecipeHandler(Block block) {
		validHeatBlocks.remove(block);
	}
	
	public static boolean isValidBlock(World world, Point3I point) {
		return validHeatBlocks.containsKey(point.getBlock(world));
	}
	
	public static boolean canBlockProcess(World world, Point3I point, int heat) {
		Block block = point.getBlock(world);
		if (validHeatBlocks.containsKey(block)) {
			return validHeatBlocks.get(block).canProcess(heat);
		}
		return false;
	}
	
	public static boolean processBlockHeat(World world, Point3I point, int currentHeat) {
		Block block = point.getBlock(world);
		if (!validHeatBlocks.containsKey(block)) return false;
		return validHeatBlocks.get(block).processHeat(world, point, currentHeat, HeatRegistry.getInstance(world));
	}
}
