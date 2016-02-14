package tv.vanhal.contraptions.items;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Ref;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class BaseItem extends Item {
	public String itemName;
	
	public BaseItem() {
		
	}
	
	public BaseItem(String name) {
		setName(name);
		setTextureName(Ref.MODID+":"+name);
		setCreativeTab(Contraptions.ContraptionTab);
	}
	
	
	public void setName(String newName) {
		itemName = newName;
		this.setUnlocalizedName(itemName);
	}
	
	public void preInit() {
		GameRegistry.registerItem(this, itemName);
		addRecipe();
	}

	protected void addRecipe() {
		
	}
}