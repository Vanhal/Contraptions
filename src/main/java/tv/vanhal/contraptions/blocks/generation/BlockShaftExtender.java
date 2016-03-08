package tv.vanhal.contraptions.blocks.generation;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TileShaftExtender;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockShaftExtender extends BaseCustomBlock implements ITorqueBlock {

	public BlockShaftExtender() {
		super("barExtender");
	}
	
	@Override
	public Axis getRotationType() {
		return Axis.FourWay;
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
			int facing = getFacing(world, point.getPos());
			if ( (direction == facing) || (direction == EnumFacing.values()[facing].getOpposite().ordinal()) ) {
				EnumFacing dir = EnumFacing.values()[direction];
				int torque = torqueBlock.getTorqueProduced(world, point);
				torque += torqueBlock.getTorqueTransfering(world, point.getAdjacentPoint(dir), direction);
				return torque;
			}
		}
		return 0;
	}

	
	@Override
	 public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		int facing = getFacing(world, pos);
    	if ( (facing == 2) || (facing == 3) ) {
    		return getBounding(pos.getX(), pos.getY(), pos.getZ(), 0.4f, 0.52f, 0.0f, 0.6f, 0.72f, 1.00f);
		} else {
			return getBounding(pos.getX(), pos.getY(), pos.getZ(), 0.0f, 0.52f, 0.4f, 1.0f, 0.72f, 0.6f);
		}
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		int facing = getFacing(world, pos);
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
