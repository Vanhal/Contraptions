package tv.vanhal.contraptions.tiles;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
	public void doUpdate() {
		if (!worldObj.isRemote) {
			if (energyStorage < maxEnergyStorage) {
				Block testBlock = getPoint().getAdjacentPoint(facing).getBlock(worldObj);
				if (testBlock instanceof ITorqueBlock) {
					int amountOfTorque = ((ITorqueBlock)testBlock).getTorqueTransfering(worldObj, 
							getPoint().getAdjacentPoint(facing), facing.ordinal());
					if (amountOfTorque>0) {
						addCharge(amountOfTorque * ContConfig.RF_PER_ROTATION);
					}
					
				}
			}
			//push out energy stored
			if (energyStorage > 0) {
				for (EnumFacing direction : EnumFacing.values()) {
					TileEntity test = getPoint().getAdjacentPoint(facing).getTileEntity(worldObj);
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
				worldObj.notifyBlockOfStateChange(pos, getPoint().getBlock(worldObj));
			}
		}
	}

	public int getComparatorOutput() {
		float percentagePowered = (float) energyStorage / (float) maxEnergyStorage;
		return (int)Math.floor(percentagePowered * 15);
	}
}
