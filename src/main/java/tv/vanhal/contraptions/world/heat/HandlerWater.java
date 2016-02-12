package tv.vanhal.contraptions.world.heat;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.fluids.ContFluids;
import tv.vanhal.contraptions.interfaces.IHeatBlockHandler;
import tv.vanhal.contraptions.util.Point3I;

public class HandlerWater implements IHeatBlockHandler {
	@Override
	public boolean canProcess(int currentHeat) {
		return (currentHeat >= ContConfig.WATER_BOIL_HEAT);
	}

	@Override
	public boolean processHeat(World world, Point3I point, int currentHeat, HeatRegistry heatReg) {
		if (canProcess(currentHeat)) {
			world.setBlock(point.getX(), point.getY(), point.getZ(), ContFluids.steam);
			heatReg.removeHeatBlock(point);
			return true;
		}
		return false;
	}

}
