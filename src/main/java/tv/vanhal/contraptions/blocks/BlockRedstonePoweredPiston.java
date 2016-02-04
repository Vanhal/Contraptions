package tv.vanhal.contraptions.blocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston;

public class BlockRedstonePoweredPiston extends BaseBlock {
	public BlockRedstonePoweredPiston() {
		super("poweredRedstonePiston", true);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TilePoweredPiston) ) {
        		((TilePoweredPiston)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileRedstonePoweredPiston();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
}
