package tv.vanhal.contraptions.blocks.generation;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileShaftExtender;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockShaftExtender extends BaseBlock implements ITorqueBlock {

	public BlockShaftExtender() {
		super("barExtender", true);
        setRotationType(Axis.FourWay);
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileShaftExtender();
	}


	@Override
	public int getTorqueProduced(World world, Point3I point) {
		return 0;
	}


	@Override
	public int getTorqueTransfering(World world, Point3I point, int direction) {
		if (point.getBlock(world) instanceof ITorqueBlock) {
			ITorqueBlock torqueBlock = (ITorqueBlock) point.getBlock(world);
			int facing = point.getMetaData(world);
			if ( (direction == facing) || (direction == ForgeDirection.OPPOSITES[facing]) ) {
				ForgeDirection dir = ForgeDirection.getOrientation(direction);
				int torque = torqueBlock.getTorqueProduced(world, point);
				torque += torqueBlock.getTorqueTransfering(world, point.getAdjacentPoint(dir), direction);
				return torque;
			}
		}
		return 0;
	}

	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		return getBounding(x, y, z, 0.4f, 0.52f, 0.0f, 0.6f, 0.72f, 1.00f);
		} else {
			return getBounding(x, y, z, 0.0f, 0.52f, 0.4f, 1.0f, 0.72f, 0.6f);
		}
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		setBlockBounds(0.4f, 0.52f, 0.0f, 0.6f, 0.72f, 1.00f);
		} else {
			setBlockBounds(0.0f, 0.52f, 0.4f, 1.0f, 0.72f, 0.6f);
		}
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"sps", "pip", "sps", 'p', ContItems.plateIron, 'i', Items.iron_ingot, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}


}
