package tv.vanhal.contraptions.blocks.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileCrusher;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.util.ItemHelper;

public class BlockCrusher extends BaseBlock {

	public BlockCrusher() {
		super("crusher", true);
		setRotationType(null);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileCrusher();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return ItemHelper.clickAddToTile(world, x, y, z, player, 0);
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"ooo", "sss", "s s", 'o', Blocks.obsidian, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}
}
