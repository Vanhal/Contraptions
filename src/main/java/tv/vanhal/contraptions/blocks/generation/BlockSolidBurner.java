package tv.vanhal.contraptions.blocks.generation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.HeatRegistry;
import tv.vanhal.contraptions.world.RenderOverlay;

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
		if (player.isSneaking()) {
			if (!world.isRemote) {
				if (player.getCurrentEquippedItem()==null) {
					TileEntity tile = world.getTileEntity(x, y, z);
					if ( (tile != null) && (tile instanceof TileSolidBurner) ) {
						TileSolidBurner burner = (TileSolidBurner)tile;
						if (burner.getStackInSlot(0)!=null) {
							ItemHelper.dropAsItem(world, x, y, z, burner.getStackInSlot(0));
							burner.setInventorySlotContents(0, null);
						}
					}
				}
			}
			return true;
		} else if (player.getCurrentEquippedItem() != null) {
			if (ItemHelper.isFuel(player.getCurrentEquippedItem())) {
				TileEntity tile = world.getTileEntity(x, y, z);
				if ( (tile != null) && (tile instanceof TileSolidBurner) ) {
					if (!world.isRemote) {
						TileSolidBurner burner = (TileSolidBurner)tile;
						if (burner.canInsertItem(0, player.getCurrentEquippedItem(), 0)) {
							if (burner.getStackInSlot(0)==null) {
								burner.setInventorySlotContents(0, new ItemStack(player.getCurrentEquippedItem().getItem(), 
										1, player.getCurrentEquippedItem().getItemDamage()));
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
							}
						}
					}
					return true;
				}
			}
		}
		return false;
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
}
