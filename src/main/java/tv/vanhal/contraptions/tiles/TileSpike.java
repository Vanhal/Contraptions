package tv.vanhal.contraptions.tiles;

import java.util.ArrayList;
import java.util.List;

import tv.vanhal.contraptions.Contraptions;
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
	public void updateEntity() {
		if (!worldObj.isRemote) {
			List<TileEntity> tiles = new ArrayList<TileEntity>();
			for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
				TileEntity test = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
				if (test!=null) {
					if (test instanceof TileEntityPiston) {
						TileEntityPiston movingPiston = (TileEntityPiston)test;
						if ( (movingPiston.getStoredBlockID() != null) && (!movingPiston.getStoredBlockID().equals(Blocks.piston_head)) 
							&& (movingPiston.isExtending()) && (direction.getOpposite().ordinal() == movingPiston.getPistonOrientation()) ) {
							List<ItemStack> drops = movingPiston.getStoredBlockID().getDrops(worldObj, movingPiston.xCoord,
									movingPiston.yCoord, movingPiston.zCoord, movingPiston.getBlockMetadata(), 0);
							for (ItemStack itemStack: drops) {
								dropAsItem(movingPiston.xCoord, movingPiston.yCoord, movingPiston.zCoord, itemStack);
							}
							worldObj.setBlockToAir(movingPiston.xCoord, movingPiston.yCoord, movingPiston.zCoord);
							movingPiston.clearPistonTileEntity();
						}
					}
				}
			}
		}
	}
	
	
	protected void dropAsItem(int x, int y, int z, Block block, int meta) {
		if (block!=null) {
			ItemStack itemStack = new ItemStack(block, 1, meta);
			this.dropAsItem(x, y, z, itemStack);
		}
	}
	
	
	protected void dropAsItem(int x, int y, int z, ItemStack itemStack) {
        if (!worldObj.isRemote) {
            float f = 0.7F;
            double d0 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(worldObj, (double)x + d0, (double)y + d1, (double)z + d2, itemStack);
            entityitem.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(entityitem);
        }
    }
}
