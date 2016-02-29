package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.compat.ModHelper;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileSpreader extends BaseInventoryTile {
	public final int POWER_PER_USE = 320;
	public float currentSpin = 0;
	protected boolean powered = false;
	protected int cooldown = 0;
	protected int previousStackSize = 0;

	public TileSpreader() {
		super(1, 2560);
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			if (cooldown>0) {
				cooldown--;
				if (cooldown==0) addPartialUpdate("cooldown", cooldown);
			}
			if ( (slots[0]!=null) && (slots[0].stackSize != previousStackSize) ) {
				previousStackSize = slots[0].stackSize;
				setContentsUpdate();
			} else if ( (slots[0]==null) && (previousStackSize != 0) ) {
				previousStackSize = 0;
				setContentsUpdate();
			}
		}
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setBoolean("powered", powered);
		nbt.setInteger("cooldown", cooldown);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("powered")) powered = nbt.getBoolean("powered");
		if (nbt.hasKey("cooldown")) cooldown = nbt.getInteger("cooldown");
	}
	
	public boolean isInCooldown() {
		return (cooldown>0);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		//check if it's a seed, or bonemeal (and propably fertiliser in the future
		if (ModHelper.isPlantible(itemStack)) return true;
		else if (ItemHelper.areStacksSame(itemStack, new ItemStack(Items.dye, 1, 15), true, true)) return true;
		return false;
	}
	
	public void blockUpdated() {
		if (!worldObj.isRemote) {
			boolean currentPowered = this.isPowered();
			if ( (currentPowered) && (!powered) && (cooldown <= 0) ) {
				powered = currentPowered;
				if ( (slots[0]!=null) && (consumeCharge(POWER_PER_USE)) ) {
					if (ModHelper.isPlantible(slots[0])) {
						plantSeeds();
					} else {
						useContents();
					}
					cooldown = 30;
					addPartialUpdate("cooldown", cooldown);
				}
			} else if ( (!currentPowered) && (powered) ) {
				powered = currentPowered;
			}
		}
	}

	private void useContents() {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (!( (y==0) && (x==0) )) {
					if ( (slots[0] != null) && (slots[0].stackSize>0) ) {
						Point3I plantPoint = getPoint().offset(x, 0, y);
						if (ItemDye.applyBonemeal(slots[0], worldObj, plantPoint.getPos(), null)) {
							setContentsUpdate();
							if (worldObj.isRemote) {
								worldObj.playAuxSFX(2005, plantPoint.getPos(), 0);
		                    }
						}
					}
				}
			}
		}
	}

	private void plantSeeds() {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (!( (y==0) && (x==0) )) {
					if ( (slots[0] != null) && (slots[0].stackSize>0) ) {
						Point3I plantPoint = getPoint().offset(x, 0, y);
						if (ModHelper.validBlock(worldObj, slots[0], plantPoint)) {
							if (ModHelper.placeSeed(worldObj, slots[0], plantPoint, false)) {
								ModHelper.placeSeed(worldObj, slots[0], plantPoint, true);
								decrStackSize(0, 1);
							}
						}
					}
				}
			}
		}
	}
}
