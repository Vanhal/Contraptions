package tv.vanhal.contraptions.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;

public class BlockFilter extends BaseBlock {

	public BlockFilter() {
		super("filter");
	}

	@Override
	public boolean isCustomModel() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return null;
	}
}
