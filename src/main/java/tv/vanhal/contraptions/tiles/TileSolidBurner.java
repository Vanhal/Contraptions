package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.world.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileSolidBurner extends BaseInventoryTile {
	public final int HEAT_PER_TICK = 20;
	
	protected int burning = 0;
	protected boolean isRedstonePowered = false;

	public TileSolidBurner() {
		super(1);
	}

	public void blockUpdated() {
		if (!worldObj.isRemote) {
			if (isRedstonePowered!=isPowered()) {
				isRedstonePowered = isPowered();
				addPartialUpdate("isRedstonePowered", isRedstonePowered, true);
			}
		}
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (isRedstonePowered) {
				if (isActive()) {
					generateHeat();
				} else if ( (slots[0]!=null) && (ItemHelper.isFuel(slots[0])) ) {
					burning = Math.round(ItemHelper.getBurnTime(slots[0]) / 2.0f);
					addPartialUpdate("burning", burning, true);
					slots[0] = null;
				}
				if (burning>0) {
					burning--;
					if (burning<=0) { 
						burning = 0;
						addPartialUpdate("burning", burning, true);
					}
				}
			}
		}
	}

	
	protected void generateHeat() {
		//heat should spread evenly over all the plates and machines connected
		//blocks will melt if they get too hot 
		//UPDATE: This will be taken care of in the heat registry
		HeatRegistry.getInstance(worldObj).addHeat(xCoord, yCoord, zCoord, HEAT_PER_TICK);
	}
	
	@Override
	public boolean isActive() {
		return ( (burning>0) && (isRedstonePowered) );
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return ItemHelper.isFuel(itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return true;
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setBoolean("isRedstonePowerd", isRedstonePowered);
		nbt.setInteger("burning", burning);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("isRedstonePowerd")) isRedstonePowered = nbt.getBoolean("isRedstonePowerd");
		if (nbt.hasKey("burning")) burning = nbt.getInteger("burning");
	}
}
