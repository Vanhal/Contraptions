package tv.vanhal.contraptions.blocks.machines;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IConfigurable;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.tiles.TileClock;
import tv.vanhal.contraptions.tiles.TileGrabber;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.RenderOverlay;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class BlockClock extends BaseBlock implements IConfigurable, IGuiRenderer {
	public BlockClock() {
		super("clock");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileClock();
	}
	

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"tst", "srs", "tst", 
				'r', Items.redstone, 't', Blocks.redstone_torch, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}

	@Override
	public boolean sneakClick(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
		//decrease range
		if (!world.isRemote) {
			if (world.getTileEntity(pos) instanceof TileClock) {
				((TileClock)world.getTileEntity(pos)).decreaseRange();
			}
		}
		return true;
	}

	@Override
	public boolean click(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
		//increase range
		if (!world.isRemote) {
			if (world.getTileEntity(pos) instanceof TileClock) {
				((TileClock)world.getTileEntity(pos)).increaseRange();
			}
		}
		return true;
	}
	
	@Override
    public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
		TileClock clock = (TileClock) world.getTileEntity(pos);
		if (clock != null) {
			return (clock.isOn())?15:0;
		}
		return 0;
    }
	
	@Override
	public int getStrongPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return getWeakPower(world, pos, state, side);
    }
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
	
	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		//render the current burning item
		
		TileClock clock = (TileClock)world.getTileEntity(new BlockPos(x, y, z));
		if (clock != null) {
			//render the range
			RenderOverlay.drawStringCentered(StringHelper.localize("gui.time")+": "+clock.getTicks(), scr_x, scr_y - 16, Colours.WHITE);
		}
	}
	
}
