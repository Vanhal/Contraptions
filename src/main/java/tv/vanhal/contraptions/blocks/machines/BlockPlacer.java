package tv.vanhal.contraptions.blocks.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.world.RenderOverlay;

public class BlockPlacer extends BaseBlock implements IGuiRenderer {

	public BlockPlacer() {
		super("placer", true);
		setBlockBounds(0.1f, 0.1f, 0.1f, 0.9f, 0.9f, 0.9f);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TilePlacer) ) {
        		((TilePlacer)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TilePlacer();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }

	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		
		TilePlacer placer = (TilePlacer)world.getTileEntity(x, y, z);
		if (placer != null) {
			ItemStack contents = placer.getStackInSlot(0);
			if (contents != null) {
				RenderOverlay.drawItemStack(contents, scr_x, scr_y - 24, true);
			}
		}
	}

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ppp", "dcr", "ppp", 'p', ContItems.plateIron, 'r', ContItems.rfCore, 'd', Blocks.dispenser, 'c', Blocks.chest});
		GameRegistry.addRecipe(recipe);
	}
}
