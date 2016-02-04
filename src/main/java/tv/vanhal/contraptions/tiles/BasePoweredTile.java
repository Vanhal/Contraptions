package tv.vanhal.contraptions.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class BasePoweredTile extends BaseTile implements IEnergyHandler  {
	protected int maxEnergyStorage;
	protected int energyStorage;
	protected boolean canExtract = false;
	
	public BasePoweredTile() {
		this(0, 0);
	}
	
	public BasePoweredTile(int _energyStorage) {	
		this(0, _energyStorage);
	}
	
	public BasePoweredTile(int slots, int _energyStorage) {	
		super(slots);
		energyStorage = 0;
		maxEnergyStorage = _energyStorage;
	}

	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("energy", energyStorage);
	}
	
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("energy")) energyStorage = nbt.getInteger("energy");
	}
	
	protected void addCharge(int amount) {
		energyStorage += amount;
		if (energyStorage >= maxEnergyStorage) energyStorage = maxEnergyStorage;
		addPartialUpdate("energy", energyStorage);
	}
	
	protected boolean consumeCharge(int amount) {
		if (amount <= energyStorage) {
			energyStorage -= amount;
			if (energyStorage<0) energyStorage = 0;
			addPartialUpdate("energy", energyStorage);
			return true;
		}
		return false;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return (maxEnergyStorage>0);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulate) {
		int energyReceived = Math.min(getMaxEnergyStored(from) - getEnergyStored(from), amount);

		if (!simulate) {
			addCharge(energyReceived);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int amount, boolean simulate) {
		if (!canExtract) return 0;
		int energyExtracted = Math.min(getEnergyStored(from), amount);
		if (!simulate) {
			consumeCharge(energyExtracted);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergyStorage;
	}

}
