package tv.vanhal.contraptions.tiles;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileGrabber extends BaseInventoryTile {
	public int range = 2;
	public MODE mode = MODE.ITEM;
	
	protected final int MIN_RANGE = 2;
	protected final int MAX_RANGE = 8;
	protected final int RF_PER_ACTION = 10;
	
	private AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);

	public TileGrabber() {
		super(1, 5000);
		updateBounds();
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
		bounds.setBounds(
				xCoord - range, yCoord - range, zCoord - range, 
				xCoord + range + 1, yCoord + range + 1, zCoord + range + 1
			);
	}
	
	public AxisAlignedBB getRange() {
		return bounds;
	}
	
	public ItemStack getFilterItem() {
		return this.getStackInSlot(0);
	}

	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if ( (energyStorage > 0) && (!isPowered()) ) {
				List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bounds);
				for (EntityItem item : items) {
					if (energyStorage >= (RF_PER_ACTION*range)) {
						if ( (mode == MODE.ITEM) && (item.getEntityItem().isItemEqual(slots[0])) ) {
							addItemToFirstAdjacent(item);
						} else if ( (mode == MODE.NOT_ITEM) && (!item.getEntityItem().isItemEqual(slots[0])) ) {
							addItemToFirstAdjacent(item);
						}
					}
				}
			}
		}
	}
	
	protected boolean addItemToFirstAdjacent(EntityItem item) {
		
		return false;
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
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("range")) range = nbt.getInteger("range");
		if (nbt.hasKey("mode")) mode = MODE.values()[nbt.getInteger("mode")];
		updateBounds();
	}
}
