package tv.vanhal.contraptions.blocks.machines;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileCrusher;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockCrusher extends BaseCustomBlock {

	public BlockCrusher() {
		super("crusher");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileCrusher();
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, pos, player, 0);
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ooo", "sss", "s s", 'o', Blocks.obsidian, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
		recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
			"lll", "sss", "sws", 's', Blocks.stone, 'w', Items.water_bucket, 'l', Items.lava_bucket});
		GameRegistry.addRecipe(recipe);
	}
}
