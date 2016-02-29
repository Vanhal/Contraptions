package tv.vanhal.contraptions.blocks.machines;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.blocks.BasePoweredBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockRedstonePoweredPiston extends BasePoweredBlock {
	protected final Axis rotationType = Axis.SixWay;
	
	public BlockRedstonePoweredPiston() {
		super("poweredRedstonePiston");
	}
	
	@Override
	public Axis getRotationType() {
		return Axis.SixWay;
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(pos);
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
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ppp", "ptp", "ppp", 'p', Items.redstone, 't', ContBlocks.poweredPiston});
		GameRegistry.addRecipe(recipe);
	}
}
