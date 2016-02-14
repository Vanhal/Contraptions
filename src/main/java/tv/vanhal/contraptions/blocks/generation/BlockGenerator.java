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
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileGenerator;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockGenerator extends BaseBlock {

	public BlockGenerator() {
		super("generator", true);
        setRotationType(Axis.FourWay);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileGenerator();
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		return getBounding(x, y, z, 0.09f, 0.0f, 0.0f, 0.91f, 0.86f, 1.0f);
		} else {
			return getBounding(x, y, z, 0.0f, 0.0f, 0.09f, 1.0f, 0.86f, 0.91f);
		}
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int facing = world.getBlockMetadata(x, y, z);
    	if ( (facing == 2) || (facing == 3) ) {
    		setBlockBounds(0.09f, 0.0f, 0.0f, 0.91f, 0.86f, 1.0f);
		} else {
			setBlockBounds(0.0f, 0.0f, 0.09f, 1.0f, 0.86f, 0.91f);
		}
	}
	
	@Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

	@Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if ( (tile != null) && (tile instanceof TileGenerator) ) {
			return ((TileGenerator)tile).getComparatorOutput();
		}
        return 0;
    }
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ppp", "erp", "ppp", 'p', ContItems.plateIron, 'r', ContItems.rfCore, 'e', ContBlocks.shaftExtender});
		GameRegistry.addRecipe(recipe);
	}
}
