package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCreativePower extends BasePoweredTile {
	public final int POWER_PER_TICK = 100;
	
	public TileCreativePower() {
		canExtract = true;
		canRecieve = false;
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				TileEntity testTile = getPoint().getAdjacentPoint(dir).getTileEntity(worldObj);
				if ( (testTile!=null) && (testTile instanceof IEnergyReceiver) ) {
					IEnergyReceiver receiver = (IEnergyReceiver)testTile;
					ForgeDirection recieveSide = dir.getOpposite();
					if (receiver.canConnectEnergy(recieveSide)) {
						receiver.receiveEnergy(recieveSide, POWER_PER_TICK, false);
					}
				}
			}
		}
	}
}
