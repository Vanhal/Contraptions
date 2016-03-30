package tv.vanhal.contraptions.client.renderers.tiles;

import tv.vanhal.contraptions.tiles.TileCrusher;
import tv.vanhal.contraptions.tiles.TileFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderBlockFrame extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		//render the item on the crusher
		if (tileentity instanceof TileFrame) {
			ItemStack itemStack = ((TileFrame)tileentity).getStackInSlot(0);
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

	        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
			if (!mc.getRenderItem().shouldRenderItemIn3D(itemstack) ) {
				GlStateManager.scale(0.8F, 0.8F, 0.8F);
			} else {
				GlStateManager.scale(1.5F, 1.5F, 1.5F);
		        if (itemstack.getItem() instanceof ItemSkull) GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}
			
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
