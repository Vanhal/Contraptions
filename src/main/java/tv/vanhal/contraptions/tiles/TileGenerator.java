package tv.vanhal.contraptions.tiles;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;

public class TileGenerator extends BasePoweredTile {
	public final int maxEnergyTransferPerTick = 250;
	private int currentOutputPower = 0;
	
	public TileGenerator() {
		super(20000);
		this.canExtract = true;
		this.canRecieve = false;
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (energyStorage < maxEnergyStorage) {
				Block testBlock = worldObj.getBlock(getX() + facing.offsetX, getY() + facing.offsetY, getZ() + facing.offsetZ);
				if (testBlock instanceof ITorqueBlock) {
					int amountOfTorque = ((ITorqueBlock)testBlock).getTorqueTransfering(worldObj, getX() + facing.offsetX, 
							getY() + facing.offsetY, getZ() + facing.offsetZ, facing.ordinal());
					if (amountOfTorque>0) {
						addCharge(amountOfTorque * ContConfig.RF_PER_ROTATION);
					}
					
				}
			}
			//push out energy stored
			if (energyStorage > 0) {
				for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
					TileEntity test = worldObj.getTileEntity(getX() + direction.offsetX, getY() + direction.offsetY, 
							getZ() + direction.offsetZ);
					if  (test instanceof IEnergyReceiver) {
						IEnergyReceiver energyBlock = (IEnergyReceiver)test;
						if (energyBlock.canConnectEnergy(direction.getOpposite())) {
							int transferAmt = Math.min(energyStorage, maxEnergyTransferPerTick);
							consumeCharge(energyBlock.receiveEnergy(direction.getOpposite(), transferAmt, false));
						}
					}
				}
			}
			if (currentOutputPower != getComparatorOutput()) {
				currentOutputPower = getComparatorOutput();
				worldObj.notifyBlockChange(getX(), getY(), getZ(), worldObj.getBlock(getX(), getY(), getZ()));
			}
		}
	}

	public int getComparatorOutput() {
		float percentagePowered = (float) energyStorage / (float) maxEnergyStorage;
		return (int)Math.floor(percentagePowered * 15);
	}
}
