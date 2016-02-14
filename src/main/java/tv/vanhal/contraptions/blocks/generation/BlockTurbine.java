package tv.vanhal.contraptions.blocks.generation;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileTurbine;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockTurbine extends BaseBlock implements ITorqueBlock {

	public BlockTurbine() {
		super("turbine", true);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.91f, 1.0f);
        setRotationType(Axis.FourWay);
	}

	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileTurbine();
	}


	@Override
	public int getTorqueProduced(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileTurbine) {
			return ((TileTurbine)tile).getTorque();
		}
		return 0;
	}


	@Override
	public int getTorqueTransfering(World world, int x, int y, int z, int direction) {
		if (world.getBlock(x, y, z) instanceof ITorqueBlock) {
			ITorqueBlock torqueBlock = (ITorqueBlock) world.getBlock(x, y, z);
			int facing = world.getBlockMetadata(x, y, z);
			if ( (direction == facing) || (direction == ForgeDirection.OPPOSITES[facing]) ) {
				ForgeDirection dir = ForgeDirection.getOrientation(direction);
				int torque = torqueBlock.getTorqueProduced(world, x, y, z);
				torque += torqueBlock.getTorqueTransfering(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, direction);
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
