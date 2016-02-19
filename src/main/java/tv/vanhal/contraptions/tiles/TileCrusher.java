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
import tv.vanhal.contraptions.util.Point3I;

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
	public void doUpdate() {
		if (!worldObj.isRemote) {
			//check for a piston moved towards, and then back from the crusher
			Block aboveBlock = getPoint().offset(0, 1, 0).getBlock(worldObj);
			if (!crushed) {
				TileEntity test = getPoint().offset(0, 1, 0).getTileEntity(worldObj);
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
