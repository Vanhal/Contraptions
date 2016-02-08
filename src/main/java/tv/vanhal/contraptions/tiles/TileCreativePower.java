package tv.vanhal.contraptions.tiles;

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
			for (int i = 0; i < 6; i++) {
				TileEntity testTile = worldObj.getTileEntity(xCoord + Facing.offsetsXForSide[i],
	            		yCoord + Facing.offsetsYForSide[i], zCoord + Facing.offsetsZForSide[i]);
				if ( (testTile!=null) && (testTile instanceof IEnergyReceiver) ) {
					IEnergyReceiver receiver = (IEnergyReceiver)testTile;
					ForgeDirection recieveSide = ForgeDirection.getOrientation(Facing.oppositeSide[i]);
					if (receiver.canConnectEnergy(recieveSide)) {
						receiver.receiveEnergy(recieveSide, POWER_PER_TICK, false);
					}
				}
			}
		}
	}
}
