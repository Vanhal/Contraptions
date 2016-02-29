package tv.vanhal.contraptions.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.ContBlocks;

public class TileRedstonePoweredPiston extends TilePoweredPiston {
	public final int POWER_PER_USE = 150;
	
	@Override
	protected void pushDone(int lastBlock) {
		if (isAir(lastBlock + 1)) lastBlock++;
		
		if (isAir(lastBlock, false)) {
			worldObj.setBlockState(pos.offset(facing, lastBlock), ContBlocks.pulse.getDefaultState());
		}
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
	}
}
