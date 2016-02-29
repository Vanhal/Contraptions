package tv.vanhal.contraptions.world.heat;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import tv.vanhal.contraptions.crafting.HeatRecipes;
import tv.vanhal.contraptions.interfaces.IHeatBlockHandler;
import tv.vanhal.contraptions.util.Point3I;

public class HandlerRecipes implements IHeatBlockHandler {
	protected HeatRecipes recipe;
	
	public HandlerRecipes(HeatRecipes _recipe) {
		recipe = _recipe;
	}

	@Override
	public boolean canProcess(int currentHeat) {
		return (currentHeat>=recipe.getRequiredHeat());
	}

	@Override
	public boolean processHeat(World world, Point3I point, int currentHeat, HeatRegistry heatReg) {
		if (canProcess(currentHeat)) {
			world.setBlockState(point.getPos(), recipe.getOutputBlock().getDefaultState());
			heatReg.removeHeatBlock(point);
			return true;
		}
		return false;
	}

}
