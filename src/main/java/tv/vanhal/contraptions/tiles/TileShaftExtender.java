package tv.vanhal.contraptions.tiles;

import net.minecraft.block.Block;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;

public class TileShaftExtender extends BaseTile {
	public float currentSpin;
	
	public TileShaftExtender() {
		super();
	}
	
	public boolean isRunning() {
		Block thisBlock = worldObj.getBlock(xCoord, yCoord, zCoord);
		if (thisBlock instanceof ITorqueBlock) {
			int amountOfTorque = ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj, xCoord, 
					yCoord, zCoord, facing.ordinal());
			amountOfTorque += ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj, xCoord, 
					yCoord, zCoord, facing.getOpposite().ordinal());
			return (amountOfTorque > 0);
		}
		return false;
	}
}
