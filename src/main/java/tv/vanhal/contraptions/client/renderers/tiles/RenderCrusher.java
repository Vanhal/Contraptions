package tv.vanhal.contraptions.client.renderers.tiles;

import tv.vanhal.contraptions.tiles.TileCrusher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderCrusher extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		//render the item on the crusher
		if (tileentity instanceof TileCrusher) {
			ItemStack itemStack = ((TileCrusher)tileentity).getStackInSlot(0);
			if (itemStack!=null) {
				renderItem(tileentity, itemStack, x, y, z);
			}
		}
	}
	
	private void renderItem(TileEntity tileentity, ItemStack itemstack, double x, double y, double z) {
		if (itemstack != null) {
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			
			Minecraft mc = Minecraft.getMinecraft();

			if (!mc.getRenderItem().shouldRenderItemIn3D(itemstack) ) {
		        GlStateManager.translate(x + 0.5D, y + 0.92D, z + 0.5D);
			} else {
		        GlStateManager.translate(x + 0.5D, y + 0.85D, z + 0.5D);
		        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		        if (itemstack.getItem() instanceof ItemSkull) GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}
			

			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(0.8F, 0.8F, 0.8F);

			GlStateManager.pushAttrib();
			RenderHelper.enableStandardItemLighting();
			mc.getRenderItem().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popAttrib();


			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
    }
}
