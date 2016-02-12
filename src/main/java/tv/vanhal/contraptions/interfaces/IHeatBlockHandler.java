package tv.vanhal.contraptions.interfaces;

import java.util.ArrayList;

import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IHeatBlockHandler {
	public boolean canProcess(int currentHeat);
	public boolean processHeat(World world, Point3I point, int currentHeat, HeatRegistry heatReg);
}
