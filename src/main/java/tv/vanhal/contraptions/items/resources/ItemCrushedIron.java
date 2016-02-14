package tv.vanhal.contraptions.items.resources;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.BaseItem;

public class ItemCrushedIron extends BaseItem {

	public ItemCrushedIron() {
		super("crushedIron");
	}
	
	@Override
	protected void addRecipe() {
		RecipeManager.registerCrusherRecipe(new ItemStack(this), new ItemStack(Items.iron_ingot), 8);
	}
}
