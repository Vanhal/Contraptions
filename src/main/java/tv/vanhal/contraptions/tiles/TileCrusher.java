package tv.vanhal.contraptions.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.util.ItemHelper;

public class TileCrusher extends BaseInventoryTile {
	protected boolean crushed = false;

	public TileCrusher() {
		super(1);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return !crushed;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			//check for a piston moved towards, and then back from the crusher
			Block aboveBlock = worldObj.getBlock(getX(), getY() + 1, getZ());
			if (!crushed) {
				TileEntity test = worldObj.getTileEntity(getX(), getY() + 1, getZ());
				if (test!=null) {
					if (test instanceof TileEntityPiston) {
						TileEntityPiston movingPiston = (TileEntityPiston)test;
						if ( (movingPiston.getStoredBlockID().equals(Blocks.piston_head)) 
							&& (movingPiston.isExtending())
							&& (ForgeDirection.DOWN.ordinal() == movingPiston.getPistonOrientation()) ) {
							//move towards
							crushed = true;
						}
					}
				}
			} else {
				if (aboveBlock.getMaterial() == Material.air) {
					crushed = false;
					//do crafting
					craftItem();
				}
			}
			
		}
	}
	
	public void craftItem() {
		if (slots[0]!=null) {
			int times = RecipeManager.getCrusherTimes(slots[0]);
			if (times>0) {
				int currentTimes = (slots[0].stackTagCompound!=null)?(slots[0].stackTagCompound.hasKey("ContCrushTimes")?
						slots[0].stackTagCompound.getInteger("ContCrushTimes"):0):0;
				currentTimes++;
				if (currentTimes >= times) {
					ItemStack output = RecipeManager.getCrusherOutput(slots[0]);
					if (output!=null) {
						ItemHelper.dropAsItem(worldObj, getX(), getY() + 1, getZ(), output);
					} else {
						Contraptions.logger.warn("Crusher Output is null");
					}
					setInventorySlotContents(0, null);
				} else {
					if (slots[0].stackTagCompound==null) slots[0].stackTagCompound = new NBTTagCompound();
					slots[0].stackTagCompound.setInteger("ContCrushTimes", currentTimes);
				}
			} else {
				ItemHelper.dropAsItem(worldObj, getX(), getY() + 1, getZ(), slots[0]);
				setInventorySlotContents(0, null);
			}
		}
	}

	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setBoolean("crushed", crushed);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("crushed")) crushed = nbt.getBoolean("crushed");
	}
}
