package tv.vanhal.contraptions.items;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Ref;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BaseItem extends Item {
	public String itemName;
	
	public BaseItem() {
		
	}
	
	public BaseItem(String name) {
		setName(name);
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
	
	public void init() {
		if (Contraptions.proxy.isClient())
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(this, 0, new ModelResourceLocation(Ref.MODID + ":" + itemName, "inventory"));
	}

	protected void addRecipe() {
		
	}
}