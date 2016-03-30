package tv.vanhal.contraptions.tiles;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.TileGrabber.MODE;
import tv.vanhal.contraptions.util.InventoryHelper;

public class TileClock extends BaseTile {
	public int range = 2;
	public int count = 0;
	
	protected final int MIN_RANGE = 1;
	protected final int MAX_RANGE = 8;

	public TileClock() {
		super();
	}
	
	public void increaseRange() {
		range++;
		if (range>MAX_RANGE) range = MIN_RANGE;
		addPartialUpdate("range", range);
	}
	
	public void decreaseRange() {
		range--;
		if (range<MIN_RANGE) range = MAX_RANGE;
		addPartialUpdate("range", range);
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			boolean wasOn = isOn();
			count++;
			if (count>getTicks()) {
				count = 0;
			}
			boolean nowOn = isOn();
			if (nowOn != wasOn) {
				worldObj.notifyNeighborsOfStateChange(pos, getPoint().getBlock(worldObj));
			}

		}
	}

	public int getTicks() {
		return (int)Math.pow(2.0, (double)range);
	}
	
	public boolean isOn() {
		return (getTicks()==count);
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("range", range);
		nbt.setInteger("count", count);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("range")) range = nbt.getInteger("range");
		if (nbt.hasKey("count")) count = nbt.getInteger("count");
	}
}
