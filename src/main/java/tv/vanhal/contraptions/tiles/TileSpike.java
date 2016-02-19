package tv.vanhal.contraptions.tiles;

import java.util.ArrayList;
import java.util.List;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSpike extends BaseTile {
	
	protected AxisAlignedBB boundCheck = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	
	public TileSpike() {
		
		
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			List<TileEntity> tiles = new ArrayList<TileEntity>();
			for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
				TileEntity test = getPoint().getAdjacentPoint(direction).getTileEntity(worldObj);
				if (test!=null) {
					if (test instanceof TileEntityPiston) {
						TileEntityPiston movingPiston = (TileEntityPiston)test;
						if ( (movingPiston.getStoredBlockID() != null) 
							&& (!movingPiston.getStoredBlockID().equals(Blocks.piston_head)) 
							&& (movingPiston.isExtending()) 
							&& (direction.getOpposite().ordinal() == movingPiston.getPistonOrientation()) ) {
							ItemHelper.dropBlockIntoWorld(worldObj, movingPiston.xCoord, movingPiston.yCoord,
									movingPiston.zCoord, movingPiston.getStoredBlockID(), movingPiston.getBlockMetadata());
							movingPiston.clearPistonTileEntity();
							worldObj.playAuxSFX(2001, movingPiston.xCoord, movingPiston.yCoord,	movingPiston.zCoord, 
									Block.getIdFromBlock(movingPiston.getStoredBlockID()) + (movingPiston.getBlockMetadata() << 12));
						}
					}
				}
			}
		}
	}
	
	
	
}
