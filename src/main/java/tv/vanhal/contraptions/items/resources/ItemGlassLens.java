package tv.vanhal.contraptions.items.resources;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemGlassLens extends BaseItem {
	
	public ItemGlassLens() {
		super("glassLens");
	}
	
	@Override
	protected void addRecipe() {
		GameRegistry.addSmelting(ContItems.layeredGlass, new ItemStack(this), 0);
	}
}
