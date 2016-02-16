package tv.vanhal.contraptions.blocks.machines;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IConfigurable;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileGrabber;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.world.RenderOverlay;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class BlockGrabber extends BaseBlock implements IConfigurable, IGuiRenderer {

	public BlockGrabber() {
		super("grabber");
        setRotationType(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileGrabber();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, x, y, z, player, 0);
	}

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"php", "ere", "php", 
				'r', ContItems.rfCore, 'p', ContItems.plateIron, 
				'e', Items.ender_pearl, 'h', Blocks.hopper});
		GameRegistry.addRecipe(recipe);
	}

	@Override
	public boolean sneakClick(EntityPlayer player, World world, int x, int y, int z, int side) {
		//change mode
		if (!world.isRemote) {
			if (world.getTileEntity(x, y, z) instanceof TileGrabber) {
				((TileGrabber)world.getTileEntity(x, y, z)).changeMode();
			}
		}
		return true;
	}

	@Override
	public boolean click(EntityPlayer player, World world, int x, int y, int z, int side) {
		//increase range
		if (!world.isRemote) {
			if (world.getTileEntity(x, y, z) instanceof TileGrabber) {
				((TileGrabber)world.getTileEntity(x, y, z)).increaseRange();
			}
		}
		return true;
	}
	
	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		//render the current burning item
		
		TileGrabber grabber = (TileGrabber)world.getTileEntity(x, y, z);
		if (grabber != null) {
			ItemStack filterItem = grabber.getFilterItem();
			if (filterItem != null) {
				RenderOverlay.drawItemStack(filterItem, scr_x, scr_y - 46, false);
			}
	
			
			//render the range
			RenderOverlay.drawStringCentered(StringHelper.localize("gui.range")+": "+grabber.range, scr_x, scr_y - 16, Colours.WHITE);
			//render the current mode
			RenderOverlay.drawStringCentered(StringHelper.localize("gui.mode")+": "+
					StringHelper.localize("gui.mode."+grabber.mode.toString()), scr_x, scr_y - 28, Colours.WHITE);
		}
	}
}
