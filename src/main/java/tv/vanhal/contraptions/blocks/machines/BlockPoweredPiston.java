package tv.vanhal.contraptions.blocks.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockPoweredPiston extends BaseBlock {

	public BlockPoweredPiston() {
		super("poweredPiston", true);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if ( (tile != null) && (tile instanceof TilePoweredPiston) ) {
        		((TilePoweredPiston)tile).blockUpdated();
        	}
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TilePoweredPiston();
	}
	
	@Override
	public boolean canProvidePower() {
        return true;
    }
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ppp", "trp", "ppp", 'p', ContItems.plateIron, 'r', ContItems.rfCore, 't', Blocks.piston});
		GameRegistry.addRecipe(recipe);
	}
}
