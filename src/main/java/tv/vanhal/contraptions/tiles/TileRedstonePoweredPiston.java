package tv.vanhal.contraptions.tiles;

import net.minecraft.world.WorldServer;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.ContBlocks;

public class TileRedstonePoweredPiston extends TilePoweredPiston {
	public final int POWER_PER_USE = 150;
	
	@Override
	protected void pushDone(int lastBlock) {
		if (isAir(lastBlock + 1)) lastBlock++;
		
		if (isAir(lastBlock, false)) {
			int x = xCoord + (facing.offsetX*lastBlock);
			int y = yCoord + (facing.offsetY*lastBlock);
			int z = zCoord + (facing.offsetZ*lastBlock);
			worldObj.setBlock(x, y, z, ContBlocks.pulse);
		}
	}
}
