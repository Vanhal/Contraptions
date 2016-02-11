package tv.vanhal.contraptions.interfaces;

import net.minecraft.world.World;

public interface ITorqueBlock {

	public int getTorqueProduced(World world, int x, int y, int z);
	public int getTorqueTransfering(World world, int x, int y, int z, int direction);
}
