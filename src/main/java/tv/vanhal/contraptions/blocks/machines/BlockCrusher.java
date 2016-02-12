package tv.vanhal.contraptions.blocks.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.tiles.TileCrusher;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.ItemHelper;

public class BlockCrusher extends BaseBlock {

	public BlockCrusher() {
		super("crusher", true);
		setRotationType(null);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileCrusher();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, x, y, z, player, 0);
	}
}
