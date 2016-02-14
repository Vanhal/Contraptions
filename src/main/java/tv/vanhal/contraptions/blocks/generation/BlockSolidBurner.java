package tv.vanhal.contraptions.blocks.generation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.RenderOverlay;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class BlockSolidBurner extends BaseBlock implements IHeatBlock, IGuiRenderer {

	public BlockSolidBurner() {
		super("solidBurner");
		setFrontTexture("solidBurner_front");
		setFrontActiveTexture("solidBurner_frontActive");
		setRotationType(Axis.FourWay);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TileSolidBurner) ) {
        		((TileSolidBurner)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, x, y, z, player, 0);
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
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote)
			HeatRegistry.getInstance(world).addHeatBlock(x, y, z);
		super.onBlockAdded(world, x, y, z);
    }

	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		//render the current burning item
		
		TileSolidBurner burner = (TileSolidBurner)world.getTileEntity(x, y, z);
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
