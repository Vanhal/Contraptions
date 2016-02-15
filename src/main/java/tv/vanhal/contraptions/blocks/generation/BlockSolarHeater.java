package tv.vanhal.contraptions.blocks.generation;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileSolarHeater;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockSolarHeater extends BaseBlock {

	public BlockSolarHeater() {
		super("solarHeater", true);
		setRotationType(Axis.FourWay);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSolarHeater();
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				" l ", "s s", "ppp", 'l', ContItems.glassLens, 's', Items.stick, 'p', Blocks.planks});
		GameRegistry.addRecipe(recipe);
	}

}
