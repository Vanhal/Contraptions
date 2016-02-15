package tv.vanhal.contraptions.items.resources;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemPlateIron extends BaseItem {
	public ItemPlateIron() {
		super("plateIron");
	}
	
	@Override
	protected void addRecipe() {
		RecipeManager.registerCrusherRecipe(new ItemStack(this), new ItemStack(ContItems.moltenIron), 1);
		OreDictionary.registerOre("ironPlate", this);
	}
}
