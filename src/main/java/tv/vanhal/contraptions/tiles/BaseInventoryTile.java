package tv.vanhal.contraptions.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public class BaseInventoryTile extends BasePoweredTile implements ISidedInventory {
	
	public BaseInventoryTile(int size) {
		this(size, 0);
		this.canExtract = false;
		this.canRecieve = false;
	}
	
	public BaseInventoryTile(int size, int _energyStorage) {
		super(size, _energyStorage);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		if (slots[slot] != null) {
			ItemStack newStack;
			if (slots[slot].stackSize <= amt) {
				newStack = slots[slot];
				slots[slot] = null;
			} else {
				newStack = slots[slot].splitStack(amt);
				if (slots[slot].stackSize == 0) {
					slots[slot] = null;
				}
			}
			setContentsUpdate();
			return newStack;
		}
		return null;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (slots[slot]!=null) {
			ItemStack stack = slots[slot];
			slots[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		slots[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
		setContentsUpdate();
	}


	@Override
	public String getName() {

		return blockType.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return getPoint().getTileEntity(worldObj) == this &&
				 player.getDistanceSq(getX() + 0.5, getY() + 0.5, getZ() + 0.5) < 64;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] output = new int[slots.length];
		for (int i=0; i<slots.length; i++) {
			output[i] = i;
		}
		return output;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, EnumFacing direction) {
		if ( (slots[slot] != null) 
				&& (slots[slot].isItemEqual(itemStack))
				&& (ItemStack.areItemStackTagsEqual(itemStack, slots[slot])) ) {
			int availSpace = this.getInventoryStackLimit() - slots[slot].stackSize;
			if (availSpace>0) {
				return true;
			}
		} else if (isItemValidForSlot(slot, itemStack)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, EnumFacing direction) {
		return false;
	}



	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = null;
		}
	}

}
