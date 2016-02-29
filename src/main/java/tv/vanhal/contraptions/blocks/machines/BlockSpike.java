package tv.vanhal.contraptions.blocks.machines;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileSpike;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockSpike extends BaseCustomBlock {

	public BlockSpike() {
		super("spike");
        setBlockBounds(0.1f, 0.1f, 0.1f, 0.90f, 0.90f, 0.90f);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpike();
	}

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"sps", "psp", "sps", 'p', ContItems.plateIron, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}
}
