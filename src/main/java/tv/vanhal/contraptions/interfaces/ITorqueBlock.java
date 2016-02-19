package tv.vanhal.contraptions.interfaces;

import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.world.World;

public interface ITorqueBlock {

	public int getTorqueProduced(World world, Point3I point);
	public int getTorqueTransfering(World world, Point3I point, int direction);
}
