package tv.vanhal.contraptions.blocks.transport;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.interfaces.IConfigurable;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileGrabber;
import tv.vanhal.contraptions.tiles.TilePylon;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.RenderOverlay;

public class BlockRFPylon extends BaseCustomBlock implements IConfigurable, IGuiRenderer {

	public BlockRFPylon() {
		super("RFPylon");
        setBlockBounds(0.1f, 0.1f, 0.1f, 0.90f, 0.90f, 0.90f);
	}


	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TilePylon();
	}

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"prp", "rsr", "prp", 'p', ContItems.plateIron, 'r', ContItems.rfCore, 's', Items.ender_eye});
		GameRegistry.addRecipe(recipe);
	}


	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		//render the current burning item
		
		TilePylon pylon = (TilePylon)world.getTileEntity(new BlockPos(x, y, z));
		if (pylon != null) {
			//render the range
			RenderOverlay.drawStringCentered(StringHelper.localize("gui.range")+": "+pylon.range, scr_x, scr_y - 16, Colours.WHITE);
			RenderOverlay.drawStringCentered(StringHelper.localize("gui.cost")+": "+pylon.getTransmitCost()+" RF/t", scr_x, scr_y - 28, Colours.WHITE);
		}
	}


	@Override
	public boolean click(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
		//increase range
		if (!world.isRemote) {
			if (world.getTileEntity(pos) instanceof TilePylon) {
				((TilePylon)world.getTileEntity(pos)).increaseRange();
			}
		}
		return true;
	}


	@Override
	public boolean sneakClick(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
		//decrease range
		if (!world.isRemote) {
			if (world.getTileEntity(pos) instanceof TilePylon) {
				((TilePylon)world.getTileEntity(pos)).decreaseRange();
			}
		}
		return true;
	}

}
