package tv.vanhal.contraptions.blocks;

import tv.vanhal.contraptions.tiles.TileSpike;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSpike extends BaseBlock {

	public BlockSpike() {
		super("spike");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpike();
	}

}
