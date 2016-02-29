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
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileSpike extends BaseTile {
	
	protected AxisAlignedBB boundCheck = AxisAlignedBB.fromBounds(0, 0, 0, 0, 0, 0);
	
	public TileSpike() {
		
		
	}
	
	@Override
	public void doUpdate() {
		if (!worldObj.isRemote) {
			List<TileEntity> tiles = new ArrayList<TileEntity>();
			for (EnumFacing direction: EnumFacing.values()) {
				TileEntity test = getPoint().getAdjacentPoint(direction).getTileEntity(worldObj);
				if (test!=null) {
					if (test instanceof TileEntityPiston) {
						TileEntityPiston movingPiston = (TileEntityPiston)test;
						if ( (movingPiston.getBlockType() != null) 
							&& (!movingPiston.getBlockType().equals(Blocks.piston_head)) 
							&& (movingPiston.isExtending()) 
							&& (direction.getOpposite() == movingPiston.getFacing()) ) {
							ItemHelper.dropBlockIntoWorld(worldObj, movingPiston.getPos(), movingPiston.getBlockType(), 
									movingPiston.getBlockType().getStateFromMeta(movingPiston.getBlockMetadata()));
							movingPiston.clearPistonTileEntity();
							worldObj.playAuxSFX(2001, movingPiston.getPos(), 
									movingPiston.getBlockType().getStateId(movingPiston.getPistonState()));
						}
					}
				}
			}
		}
	}
	
	
	
}
