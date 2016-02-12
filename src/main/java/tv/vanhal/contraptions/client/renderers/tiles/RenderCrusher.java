package tv.vanhal.contraptions.client.renderers.tiles;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.tiles.TileCrusher;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class RenderCrusher extends BaseRenderer {
	public RenderCrusher() {
		setName("crusher");
	}
	
	@Override
	protected void setRotation(TileEntity tileentity) {

	}
	
	@Override
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float fo) {
		super.renderModel(tileentity, x, y, z, fo);
		
		//render the item on the crusher
		if (tileentity instanceof TileCrusher) {
			ItemStack itemStack = ((TileCrusher)tileentity).getStackInSlot(0);
			if (itemStack!=null) {
				Minecraft mc = Minecraft.getMinecraft();
				mc.renderEngine.bindTexture(itemStack.getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
				if(itemStack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemStack.getItem()).getRenderType())) {
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glTranslatef(1F, 1.1F, 0F);
					GL11.glPushMatrix();
					RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(itemStack.getItem()), itemStack.getItemDamage(), 1F);
					GL11.glPopMatrix();
					GL11.glTranslatef(-1F, -1.1F, 0F);
					GL11.glScalef(2F, 2F, 2F);
				} else {
					int renderPass = 0;
					do {
						IIcon icon = itemStack.getItem().getIcon(itemStack, renderPass);
						if(icon != null) {
							Color color = new Color(itemStack.getItem().getColorFromItemStack(itemStack, renderPass));
							GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
							float f = icon.getMinU();
							float f1 = icon.getMaxU();
							float f2 = icon.getMinV();
							float f3 = icon.getMaxV();

							ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
							GL11.glColor3f(1F, 1F, 1F);
						}
						renderPass++;
					} while(renderPass < itemStack.getItem().getRenderPasses(itemStack.getItemDamage()));
				}
			}
		}
	}
}
