package tv.vanhal.contraptions.items.resources;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemLayeredGlass extends BaseItem {

	public ItemLayeredGlass() {
		super("layeredGlass");
	}
	
	@Override
	protected void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
			"ppp", "ppp", "ppp", 'p', Blocks.glass_pane});
		GameRegistry.addRecipe(recipe);
	}
}
