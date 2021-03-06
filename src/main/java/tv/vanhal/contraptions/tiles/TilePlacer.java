package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TilePlacer extends BaseInventoryTile {
	public final int POWER_PER_USE = 40;
	protected boolean powered = false;
	
	public TilePlacer() {
		super(1, 2560);
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setBoolean("powered", powered);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("powered")) powered = nbt.getBoolean("powered");
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return (Block.getBlockFromItem(itemStack.getItem())!=Blocks.air);
	}

	public void blockUpdated() {
		if (!worldObj.isRemote) {
			boolean currentPowered = this.isPowered();
			if ( (currentPowered) && (!powered) ) {
				powered = currentPowered;
				if (consumeCharge(POWER_PER_USE))
					placeBlock();
			} else if ( (!currentPowered) && (powered) ) {
				powered = currentPowered;
			}
		}
	}

	private void placeBlock() {
		if (slots[0]!=null) {
			Block block = Block.getBlockFromItem(slots[0].getItem());
			if (block != Blocks.air) {
				Point3I placePoint = getPoint().getAdjacentPoint(facing);
				if ( (block.canPlaceBlockAt(worldObj, placePoint.getX(), placePoint.getY(), placePoint.getZ())) 
						&& (placePoint.getBlock(worldObj) != block) ) {
					worldObj.setBlock(placePoint.getX(), placePoint.getY(), placePoint.getZ(), block);
					slots[0] = null;
					setContentsUpdate();
				}
			}
		}
	}
}
