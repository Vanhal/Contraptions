package tv.vanhal.contraptions.blocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;

public class BlockPlacer extends BaseBlock {

	public BlockPlacer() {
		super("placer");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TilePlacer) ) {
        		((TilePlacer)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TilePlacer();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }

}
