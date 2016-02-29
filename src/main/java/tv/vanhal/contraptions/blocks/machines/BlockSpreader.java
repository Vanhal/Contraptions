package tv.vanhal.contraptions.blocks.machines;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.blocks.BasePoweredBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileSpreader;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.RenderOverlay;

public class BlockSpreader extends BasePoweredBlock implements IGuiRenderer {

	public BlockSpreader() {
		super("spreader");
        setBlockBounds(0.08f, 0.0f, 0.08f, 0.92f, 0.80f, 0.92f);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(pos);
        	if ( (tile != null) && (tile instanceof TileSpreader) ) {
        		((TileSpreader)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpreader();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
	
	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		
		TileSpreader spreader = (TileSpreader)world.getTileEntity(new BlockPos(x, y, z));
		if (spreader != null) {
			ItemStack contents = spreader.getStackInSlot(0);
			if (contents != null) {
				RenderOverlay.drawItemStack(contents, scr_x, scr_y - 24, true);
			}
		}
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"pdp", " c ", "prp", 'p', ContItems.plateIron, 'r', ContItems.rfCore, 'd', Blocks.dispenser, 'c', Blocks.chest});
		GameRegistry.addRecipe(recipe);
	}
}
