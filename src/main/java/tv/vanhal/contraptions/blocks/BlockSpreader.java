package tv.vanhal.contraptions.blocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.tiles.TileSpreader;

public class BlockSpreader extends BaseBlock {

	public BlockSpreader() {
		super("spreader", true);
        setBlockBounds(0.08f, 0.0f, 0.08f, 0.92f, 0.80f, 0.92f);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TileSpreader) ) {
        		((TileSpreader)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpreader();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
	
	
}
