package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.fluids.ContFluids;

public class TileCreativeSteam extends BaseTile {
	public final int TICKS_PER_STEAM = 5;
	protected int currentCount = 0;
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			if (currentCount>=TICKS_PER_STEAM) {
				if (worldObj.isAirBlock(pos.up())) {
					worldObj.setBlockState(pos.up(), ContFluids.steam.getDefaultState());
				}
				currentCount = 0;
			}
			currentCount++;
		}
	}
}
