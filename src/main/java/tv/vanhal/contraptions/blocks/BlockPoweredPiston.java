package tv.vanhal.contraptions.blocks;

import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPoweredPiston extends BaseBlock {

	public BlockPoweredPiston() {
		super("poweredPiston", true);
	}
	
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
		return new TilePoweredPiston();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
}
