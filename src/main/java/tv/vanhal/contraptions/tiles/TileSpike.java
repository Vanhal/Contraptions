package tv.vanhal.contraptions.tiles;

import java.util.List;

import tv.vanhal.contraptions.Contraptions;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class TileSpike extends BaseTile {
	
	protected AxisAlignedBB boundCheck = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	
	public TileSpike() {
		
		
	}
	
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			boundCheck.setBounds(xCoord-1, yCoord-1, zCoord -1, xCoord +2, yCoord +2, zCoord +2);
			List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, boundCheck);
			if (!entities.isEmpty()) {
				for (Entity ent: entities) {
					Contraptions.logger.info("Entity Found: "+ent.getClass());
				}
			}
		}
	}
}
