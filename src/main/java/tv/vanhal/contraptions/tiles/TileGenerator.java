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
	
	public TileGenerator() {
		super(20000);
		this.canExtract = true;
		this.canRecieve = false;
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (energyStorage < maxEnergyStorage) {
				Block testBlock = worldObj.getBlock(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ);
				if (testBlock instanceof ITorqueBlock) {
					int amountOfTorque = ((ITorqueBlock)testBlock).getTorqueTransfering(worldObj, xCoord + facing.offsetX, 
							yCoord + facing.offsetY, zCoord + facing.offsetZ, facing.ordinal());
					if (amountOfTorque>0) {
						addCharge(amountOfTorque * ContConfig.RF_PER_ROTATION);
					}
					
				}
			}
			//push out energy stored
			if (energyStorage > 0) {
				for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
					TileEntity test = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, 
							zCoord + direction.offsetZ);
					if  (test instanceof IEnergyReceiver) {
						IEnergyReceiver energyBlock = (IEnergyReceiver)test;
						if (energyBlock.canConnectEnergy(direction.getOpposite())) {
							int transferAmt = Math.min(energyStorage, maxEnergyTransferPerTick);
							consumeCharge(energyBlock.receiveEnergy(direction.getOpposite(), transferAmt, false));
						}
					}
				}
			}
		}
	}
}
