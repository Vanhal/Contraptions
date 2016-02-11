package tv.vanhal.contraptions.blocks.generation;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.tiles.TileGenerator;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockGenerator extends BaseBlock {

	public BlockGenerator() {
		super("generator", true);
        setRotationType(Axis.FourWay);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileGenerator();
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		return getBounding(x, y, z, 0.09f, 0.0f, 0.0f, 0.91f, 0.86f, 1.0f);
		} else {
			return getBounding(x, y, z, 0.0f, 0.0f, 0.09f, 1.0f, 0.86f, 0.91f);
		}
    }
}
