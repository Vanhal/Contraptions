package tv.vanhal.contraptions.client.renderers.items;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class BaseItemRenderer implements IItemRenderer {
	protected IModelCustom model = null;
	protected ResourceLocation texture;
	
	public void setModel(String name) {
		texture = new ResourceLocation(Ref.MODID, "textures/models/"+name+".png");
		model = AdvancedModelLoader.loadModel(new ResourceLocation(Ref.MODID, "models/"+name+".obj"));
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (type != ItemRenderType.INVENTORY)
			return true;
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        
        setRotation(type);
        
		
        model.renderAll();
        GL11.glPopMatrix();
	}
	
	protected void setRotation(ItemRenderType type) {
		
	}

}
