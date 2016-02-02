package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TilePlacer extends BaseISidedTile {
	protected boolean powered = false;
	
	public TilePlacer() {
		super(1);
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
				int dx = xCoord + facing.offsetX;
				int dy = yCoord + facing.offsetY;
				int dz = zCoord + facing.offsetZ;
				if ( (block.canPlaceBlockAt(worldObj, dx, dy, dz)) 
						&& (worldObj.getBlock(dx, dy, dz) != block) ) {
					worldObj.setBlock(dx, dy, dz, block);
					slots[0] = null;
				}
			}
		}
	}
}
