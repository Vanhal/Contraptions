package tv.vanhal.contraptions.blocks.creative;

import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.tiles.TileCreativeSteam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCreativeSteam extends BaseBlock {
	public BlockCreativeSteam() {
		super("creativeSteam");
		setRotationType(null);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileCreativeSteam();
	}
}
