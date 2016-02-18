package tv.vanhal.contraptions.tiles;

import net.minecraft.block.Block;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;

public class TileShaftExtender extends BaseTile {
	public float currentSpin;
	
	public TileShaftExtender() {
		super();
	}
	
	public boolean isRunning() {
		Block thisBlock = worldObj.getBlock(getX(), getY(), getZ());
		if (thisBlock instanceof ITorqueBlock) {
			int amountOfTorque = ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj, getX(), 
					getY(), getZ(), facing.ordinal());
			amountOfTorque += ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj, getX(), 
					getY(), getZ(), facing.getOpposite().ordinal());
			return (amountOfTorque > 0);
		}
		return false;
	}
}
