package tv.vanhal.contraptions.items.resources;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;

public class ItemPlateWood extends BaseItem {
	public ItemPlateWood() {
		super("plateWood");
	}
	
	@Override
	protected void addRecipe() {
		RecipeManager.registerCrusherRecipe(new ItemStack(this), new ItemStack(Blocks.planks), 4);
		OreDictionary.registerOre("woodenPlate", this);
	}
}
