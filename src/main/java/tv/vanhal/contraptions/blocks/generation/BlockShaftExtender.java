package tv.vanhal.contraptions.blocks.generation;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileShaftExtender;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockShaftExtender extends BaseBlock implements ITorqueBlock {

	public BlockShaftExtender() {
		super("barExtender", true);
        setRotationType(Axis.FourWay);
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileShaftExtender();
	}


	@Override
	public int getTorqueProduced(World world, int x, int y, int z) {
		return 0;
	}


	@Override
	public int getTorqueTransfering(World world, int x, int y, int z, int direction) {
		if (world.getBlock(x, y, z) instanceof ITorqueBlock) {
			ITorqueBlock torqueBlock = (ITorqueBlock) world.getBlock(x, y, z);
			int facing = world.getBlockMetadata(x, y, z);
			if ( (direction == facing) || (direction == ForgeDirection.OPPOSITES[facing]) ) {
				ForgeDirection dir = ForgeDirection.getOrientation(direction);
				int torque = torqueBlock.getTorqueProduced(world, x, y, z);
				torque += torqueBlock.getTorqueTransfering(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, direction);
				return torque;
			}
		}
		return 0;
	}

	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		return getBounding(x, y, z, 0.4f, 0.52f, 0.0f, 0.6f, 0.72f, 1.00f);
		} else {
			return getBounding(x, y, z, 0.0f, 0.52f, 0.4f, 1.0f, 0.72f, 0.6f);
		}
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		setBlockBounds(0.4f, 0.52f, 0.0f, 0.6f, 0.72f, 1.00f);
		} else {
			setBlockBounds(0.0f, 0.52f, 0.4f, 1.0f, 0.72f, 0.6f);
		}
	}
}
