package tv.vanhal.contraptions.items.resources;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemRFCore extends BaseItem {

	public ItemRFCore() {
		super("rfCore");
	}
	
	@Override
	protected void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
			" p ", "prp", " p ", 'p', ContItems.plateIron, 'r', Blocks.redstone_block});
		GameRegistry.addRecipe(recipe);
	}
}
