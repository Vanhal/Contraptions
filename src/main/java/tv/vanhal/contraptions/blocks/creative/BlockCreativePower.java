package tv.vanhal.contraptions.blocks.creative;

import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.tiles.TileCreativePower;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCreativePower extends BaseBlock {

	public BlockCreativePower() {
		super("creativePower");
		setRotationType(null);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileCreativePower();
	}
}
