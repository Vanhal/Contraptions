package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.fluids.ContFluids;

public class TileCreativeSteam extends BaseTile {
	public final int TICKS_PER_STEAM = 5;
	protected int currentCount = 0;
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (currentCount>=TICKS_PER_STEAM) {
				if (worldObj.isAirBlock(xCoord, yCoord+1, zCoord)) {
					worldObj.setBlock(xCoord, yCoord+1, zCoord, ContFluids.steam);
				}
				currentCount = 0;
			}
			currentCount++;
		}
	}
}
