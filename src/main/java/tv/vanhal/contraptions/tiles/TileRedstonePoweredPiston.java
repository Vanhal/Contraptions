package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;

public class TileRedstonePoweredPiston extends TilePoweredPiston {
	public final int POWER_PER_USE = 150;
	
	@Override
	protected void pushDone(int lastBlock) {
		Contraptions.logger.info("Push is done: "+lastBlock);
	}
}
