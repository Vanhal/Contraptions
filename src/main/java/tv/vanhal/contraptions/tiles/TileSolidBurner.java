package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileSolidBurner extends BaseInventoryTile {
	protected int burning = 0;
	protected boolean isRedstonePowered = false;
	protected ItemStack burningItem = null;

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
			if (!isRedstonePowered) {
				if (isActive()) {
					generateHeat();
				} else if ( (slots[0]!=null) && (ItemHelper.isFuel(slots[0])) ) {
					burning = Math.round(ItemHelper.getBurnTime(slots[0]) / 2.0f);
					burningItem = new ItemStack(slots[0].getItem(), 1, slots[0].getItemDamage());
					addPartialUpdate("burning", burning, true);
					addPartialUpdate("burningItem", burningItem, true);
					slots[0] = null;
				}
				if (burning>0) {
					burning--;
					if (burning<=0) { 
						burning = 0;
						burningItem = null;
						addPartialUpdate("burning", burning, true);
						addPartialUpdate("burningItem", burningItem, true);
					} else if ((burning%20)==0) {
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
		HeatRegistry.getInstance(worldObj).addHeat(getX(), getY(), getZ(), ContConfig.BASE_HEAT_PER_TICK);
	}
	
	public ItemStack getBurningItem() {
		return burningItem;
	}
	
	public float getPercentageBurnt() {
		if ( (burning > 0) && (burningItem != null) ) {
			float percent = burning / ((float) ItemHelper.getBurnTime(burningItem) / 2.0f);
			return percent;
		}
		return 0;
	}
	
	@Override
	public boolean isActive() {
		return ( (burning>0) && (!isRedstonePowered) );
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
		NBTTagCompound tag = new NBTTagCompound();
		if (burningItem!=null) {
			burningItem.writeToNBT(tag);
		}
		nbt.setTag("burningItem", tag);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("isRedstonePowerd")) isRedstonePowered = nbt.getBoolean("isRedstonePowerd");
		if (nbt.hasKey("burning")) burning = nbt.getInteger("burning");
		if (nbt.hasKey("burningItem")) {
			NBTTagCompound tag = (NBTTagCompound) nbt.getTag("burningItem");
			burningItem = ItemStack.loadItemStackFromNBT(tag);
		}
	}
}
