package tv.vanhal.contraptions.tiles;

import java.util.List;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.InventoryHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public class TileGrabber extends BaseInventoryTile {
	public int range = 2;
	public MODE mode = MODE.ITEM;
	
	protected final int MIN_RANGE = 2;
	protected final int MAX_RANGE = 8;
	protected final int RF_PER_ACTION = 10;
	
	private AxisAlignedBB bounds = AxisAlignedBB.fromBounds(0, 0, 0, 0, 0, 0);

	public TileGrabber() {
		super(1, 5000);
		updateBounds();
	}
	
	@Override
	public boolean isActive() {
		return ( (energyStorage > 0) && (!isPowered()) );
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	public void changeMode() {
		int current = mode.ordinal() + 1;
		if (current >= mode.values().length) current = 0;
		mode = MODE.values()[current];
		addPartialUpdate("mode", mode.ordinal());
	}
	
	public void increaseRange() {
		range++;
		if (range>MAX_RANGE) range = MIN_RANGE;
		addPartialUpdate("range", range);
		updateBounds();
	}
	
	public void updateBounds() {
		bounds = AxisAlignedBB.fromBounds(
				getX() - range, getY() - range, getZ() - range, 
				getX() + range + 1, getY() + range + 1, getZ() + range + 1
			);
	}
	
	public AxisAlignedBB getRange() {
		return bounds;
	}
	
	public ItemStack getFilterItem() {
		return this.getStackInSlot(0);
	}

	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			if (isActive()) {
				List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bounds);
				for (EntityItem item : items) {
					if (energyStorage >= (RF_PER_ACTION*range)) {
						EnumFacing invDir = canGetItem(item);
						if ( (invDir!=null) && (item.getAge()>=10) ) {
							if ( (slots[0]!=null) && (mode == MODE.ITEM) && (item.getEntityItem().isItemEqual(slots[0])) ) {
								addItemToFirstAdjacent(item, invDir);
							} else if ( (slots[0]!=null) && (mode == MODE.NOT_ITEM) && (!item.getEntityItem().isItemEqual(slots[0])) ) {
								addItemToFirstAdjacent(item, invDir);
							} else if ( (mode == MODE.CONTENTS) || (mode == MODE.EVERYTHING) ) {
								addItemToFirstAdjacent(item, invDir);
							}
						}
					}
				}
			}
		}
	}
	
	protected EnumFacing canGetItem(EntityItem item) {
		for (EnumFacing dir : EnumFacing.values()) {
			TileEntity tile = getPoint().getAdjacentPoint(dir).getTileEntity(worldObj);
			ItemStack remaining = InventoryHelper.insert(tile, item.getEntityItem().copy(), dir.getOpposite(), true);
			if ( (remaining == null) || (remaining.stackSize < item.getEntityItem().stackSize) ) {
				if (mode == MODE.CONTENTS) {
					if (InventoryHelper.doesInventoryHaveItem(tile, item.getEntityItem(), dir)) return dir;
				} else {
					return dir;
				}
			}
		}
		return null;
	}
	
	protected void addItemToFirstAdjacent(EntityItem item, EnumFacing dir) {
		if (consumeCharge(RF_PER_ACTION*range)) {
			ItemStack remainder = InventoryHelper.insert(
					getPoint().getAdjacentPoint(dir).getTileEntity(worldObj), 
					item.getEntityItem(), dir.getOpposite(), false);
			if (remainder==null) worldObj.removeEntity(item);
			else item.setEntityItemStack(remainder);
		}
	}
	

	public enum MODE {
		EVERYTHING,
		CONTENTS,
		NOT_ITEM,
		ITEM
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("range", range);
		nbt.setInteger("mode", mode.ordinal());
		updateBounds();
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("range")) range = nbt.getInteger("range");
		if (nbt.hasKey("mode")) mode = MODE.values()[nbt.getInteger("mode")];
		updateBounds();
	}
}
