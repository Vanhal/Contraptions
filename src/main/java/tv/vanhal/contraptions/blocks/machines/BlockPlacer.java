package tv.vanhal.contraptions.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.world.RenderOverlay;

public class BlockPlacer extends BaseBlock implements IGuiRenderer {

	public BlockPlacer() {
		super("placer", true);
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
				RenderOverlay.drawItemStack(contents, scr_x, scr_y - 20, true);
			}
		}
	}

}
