package tv.vanhal.contraptions.blocks.generation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
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
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.RenderOverlay;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class BlockSolidBurner extends BaseBlock implements IHeatBlock, IGuiRenderer {
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockSolidBurner() {
		super("solidBurner");
		//setFrontTexture("solidBurner_front");
		//setFrontActiveTexture("solidBurner_frontActive");
	}
	
	@Override
    protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, ACTIVE});
    }
	
	@Override
	protected void setDefaultState() {
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.WEST).withProperty(ACTIVE, false));
	}
	
	@Override
	public Axis getRotationType() {
		return Axis.FourWay;
	}
	
	@Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(pos);
        	if ( (tile != null) && (tile instanceof TileSolidBurner) ) {
        		((TileSolidBurner)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileSolidBurner) {
			TileSolidBurner burner = (TileSolidBurner)tile;
			return state.withProperty(FACING, burner.facing).withProperty(ACTIVE, burner.isActive());
		}
        return state;
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, pos, player, 0);
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSolidBurner();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }

	@Override
	public int getMeltingPoint() {
		return 2000;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote)
			HeatRegistry.getInstance(world).addHeatBlock(new Point3I(pos));
		super.onBlockAdded(world, pos, state);
    }

	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		//render the current burning item
		
		TileSolidBurner burner = (TileSolidBurner)world.getTileEntity(new BlockPos(x, y, z));
		if (burner != null) {
			ItemStack burningItem = burner.getBurningItem();
			if (burningItem != null) {
				RenderOverlay.drawItemStack(burningItem, scr_x, scr_y - 46, false);
			}
			RenderOverlay.drawProgressBar(Colours.RED, burner.getPercentageBurnt(), scr_x, scr_y - 30, 40);
		}
		
		//render the heat value
		int currentHeat = HeatRegistry.getInstance(world).getValue(x, y, z);
		int color = Colours.WHITE;
		if (currentHeat >= getMeltingPoint()*0.85) color = Colours.RED;
		else if (currentHeat >= getMeltingPoint()*0.6) color = Colours.ORANGE;
		
		RenderOverlay.drawStringCentered(StringHelper.localize("gui.heat")+": "+currentHeat, scr_x, scr_y - 20, color);
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
			"ppp", "pfp", "sss", 'p', ContItems.plateIron, 's', Blocks.stone, 'f', Blocks.furnace});
		GameRegistry.addRecipe(recipe);
	}
}
