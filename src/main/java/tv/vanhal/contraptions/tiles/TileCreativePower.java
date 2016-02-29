package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileCreativePower extends BasePoweredTile {
	public final int POWER_PER_TICK = 100;
	
	public TileCreativePower() {
		canExtract = true;
		canRecieve = false;
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			for (EnumFacing dir : EnumFacing.values()) {
				TileEntity testTile = getPoint().getAdjacentPoint(dir).getTileEntity(worldObj);
				if ( (testTile!=null) && (testTile instanceof IEnergyReceiver) ) {
					IEnergyReceiver receiver = (IEnergyReceiver)testTile;
					EnumFacing recieveSide = dir.getOpposite();
					if (receiver.canConnectEnergy(recieveSide)) {
						receiver.receiveEnergy(recieveSide, POWER_PER_TICK, false);
					}
				}
			}
		}
	}
}
