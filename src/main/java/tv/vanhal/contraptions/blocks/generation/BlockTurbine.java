package tv.vanhal.contraptions.blocks.generation;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileTurbine;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.util.Point3I;

public class BlockTurbine extends BaseCustomBlock implements ITorqueBlock {

	public BlockTurbine() {
		super("turbine");
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.91f, 1.0f);
	}
	
	@Override
	public Axis getRotationType() {
		return Axis.FourWay;
	}

	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileTurbine();
	}


	@Override
	public int getTorqueProduced(World world, Point3I point) {
		TileEntity tile = point.getTileEntity(world);
		if (tile instanceof TileTurbine) {
			return ((TileTurbine)tile).getTorque();
		}
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
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				" p ", "pep", " p ", 'p', ContItems.plateIron, 'e', ContBlocks.shaftExtender});
		GameRegistry.addRecipe(recipe);
	}
}
