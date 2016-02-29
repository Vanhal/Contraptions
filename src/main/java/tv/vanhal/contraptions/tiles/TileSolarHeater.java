package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class TileSolarHeater extends BaseTile {
	public final int SOLAR_HEAT_PER_TICK = 1;
	private int ticks = 0;
	
	public TileSolarHeater() {
		super();
	}
	
	public boolean isActive() {
		return ( (worldObj.isDaytime()) && (worldObj.canBlockSeeSky(pos)));
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			if (ticks>ContConfig.TICKS_PER_HEAT_TICK) {
				if (worldObj.isDaytime()) {
					if (worldObj.canBlockSeeSky(pos)) {
						Point3I targetBlock = getPoint().getAdjacentPoint(facing.getOpposite()).getAdjacentPoint(facing.DOWN);
						if (HeatRegistry.getInstance(worldObj).isHeatableBlock(worldObj, targetBlock))
							HeatRegistry.getInstance(worldObj).addHeat(targetBlock, SOLAR_HEAT_PER_TICK);
					}
				}
				ticks=0;
			}
			ticks++;
		}
	}
}
