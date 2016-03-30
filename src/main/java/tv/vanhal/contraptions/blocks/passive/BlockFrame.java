package tv.vanhal.contraptions.blocks.passive;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.tiles.TileFrame;
import tv.vanhal.contraptions.util.ItemHelper;

public class BlockFrame extends BaseBlock {

	public BlockFrame() {
		super("frame");
	}
	
	@Override
	public boolean isCustomModel() {
		return true;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, pos, player, 0);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileFrame();
	}
}
