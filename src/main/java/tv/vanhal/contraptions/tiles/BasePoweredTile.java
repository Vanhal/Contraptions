package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.blockstates.EnumPowered;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class BasePoweredTile extends BaseTile implements IEnergyReceiver, IEnergyProvider  {
	protected int maxEnergyStorage;
	protected int energyStorage;
	protected boolean canExtract = false;
	protected boolean canRecieve = true;
	
	public BasePoweredTile() {
		this(0, 0);
		canRecieve = false;
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

	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("energy")) energyStorage = nbt.getInteger("energy");
	}
	
	protected void addCharge(int amount) {
		int previousEnergy = energyStorage;
		energyStorage += amount;
		if (energyStorage >= maxEnergyStorage) energyStorage = maxEnergyStorage;
		if (previousEnergy!=energyStorage) addPartialUpdate("energy", energyStorage, true);
	}
	
	protected boolean consumeCharge(int amount) {
		if (amount <= energyStorage) {
			energyStorage -= amount;
			if (energyStorage<0) {
				energyStorage = 0;
				addPartialUpdate("energy", energyStorage, true);
			} else {
				addPartialUpdate("energy", energyStorage);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing face) {
		return (canRecieve || canExtract);
	}

	@Override
	public int receiveEnergy(EnumFacing from, int amount, boolean simulate) {
		if (!canRecieve) return 0;
		int energyReceived = Math.min(getMaxEnergyStored(from) - getEnergyStored(from), amount);

		if (!simulate) {
			addCharge(energyReceived);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(EnumFacing face, int amount, boolean simulate) {
		if (!canExtract) return 0;
		int energyExtracted = Math.min(getEnergyStored(face), amount);
		if (!simulate) {
			consumeCharge(energyExtracted);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(EnumFacing face) {
		return energyStorage;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing face) {
		return maxEnergyStorage;
	}

	public EnumPowered getPoweredStatus() {
		if (energyStorage==0) return EnumPowered.unpowered;
		else if (energyStorage < maxEnergyStorage) return EnumPowered.powered;
		else return EnumPowered.full;
	}
}
