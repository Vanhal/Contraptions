package tv.vanhal.contraptions.blocks.generation;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.world.HeatRegistry;

public class BlockSolidBurner extends BaseBlock implements IHeatBlock {

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

}
