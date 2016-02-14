package tv.vanhal.contraptions.client.renderers.tiles;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.TileCrusher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.FMLRenderAccessLibrary;
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
				if (itemStack.getItem() instanceof ItemBlock && shouldRenderBlock(itemStack)) {
					GL11.glPushMatrix();
					GL11.glScalef(0.4F, 0.4F, 0.4F);
					GL11.glTranslatef(0F, 1.0F, 0F);
					GL11.glRotatef(90, -1, 0, 0);
					GL11.glRotatef(90, 0, -1, 0);
					RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(itemStack.getItem()), itemStack.getItemDamage(), 1F);
					GL11.glPopMatrix();
				} else {
					int renderPass = 0;
					do {
						IIcon icon = itemStack.getItem().getIcon(itemStack, renderPass);
						if(icon != null) {
							GL11.glRotatef(90, -1, 0, 0);							
							GL11.glTranslatef(-0.45F, -0.45F, 0.45F);
							GL11.glScalef(0.9F, 0.9F, 0.9F);
							Color color = new Color(itemStack.getItem().getColorFromItemStack(itemStack, renderPass));
							GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());

							ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), 
									icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
							GL11.glColor3f(1F, 1F, 1F);
						}
						renderPass++;
					} while(renderPass < itemStack.getItem().getRenderPasses(itemStack.getItemDamage()));
				}
			}
		}
	}
	
	protected boolean shouldRenderBlock(ItemStack itemStack) {
		int renderType  = Block.getBlockFromItem(itemStack.getItem()).getRenderType();
		boolean shouldRender = RenderBlocks.renderItemIn3d(renderType);
		if (!shouldRender) {
            switch (renderType) {
	            case 38: return true;
            }
		}
		return shouldRender;
	}
}
