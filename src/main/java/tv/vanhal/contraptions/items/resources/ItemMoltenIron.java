package tv.vanhal.contraptions.items.resources;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemMoltenIron extends BaseItem {
	public ItemMoltenIron() {
		super("moltenIron");
	}
	
	@Override
	protected void addRecipe() {
		GameRegistry.addSmelting(ContItems.crushedIron, new ItemStack(this), 0);
	}
}
