package tv.vanhal.contraptions.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderOverlay {
	
	@SubscribeEvent
	public void RenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
		
		if ( (currentItem != null) && (currentItem.getItem() == ContItems.screwDriver) ) {
			if(e.type == ElementType.ALL) {
				MovingObjectPosition pos = mc.objectMouseOver;
				if(pos != null) {
					Block mouseOverBlock = mc.theWorld.getBlock(pos.blockX, pos.blockY, pos.blockZ);
					if (mouseOverBlock instanceof IGuiRenderer) {
						((IGuiRenderer)mouseOverBlock).renderGUI(mc.theWorld, pos.blockX, pos.blockY, pos.blockZ, e.resolution);
					} else if (HeatRegistry.getInstance(mc.theWorld).isHeatBlock(pos.blockX, pos.blockY, pos.blockZ)) {
						renderHeatGUI(mc.theWorld, pos.blockX, pos.blockY, pos.blockZ, e.resolution);
					}
				}
			}
		}
	}
	
	public static void drawItemStack(ItemStack itemStack, int x, int y, boolean overlay) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderItem.getInstance().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x - 8, y);
		if (overlay) {
			RenderItem.getInstance().renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x - 8, y);
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}
	
	public static void drawProgressBar(int colour, float progress, int x, int y, int width) {
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		float blue = (colour >> 24 & 255) / 255F;
		float red = (colour >> 16 & 255) / 255F;
		float green = (colour >> 8 & 255) / 255F;
		
		float halfWidth = width / 2.0f;
		double dx = x - halfWidth;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_I(Colours.BLACK, 140);
		tess.addVertex(dx + width, y, 0);
		tess.addVertex(dx, y, 0);
		tess.addVertex(dx, y + 6, 0);
		tess.addVertex(dx + width, y + 6, 0);
		
		dx += 1;
		width -= 2;
		double percentWidth = width * progress;
		
		tess.setColorRGBA_I(colour, 250);
		tess.addVertex(dx + percentWidth, y + 1, 0);
		tess.addVertex(dx, y + 1, 0);
		tess.addVertex(dx, y + 5, 0);
		tess.addVertex(dx + percentWidth, y + 5, 0);
		tess.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void drawStringCentered(String str, int x, int y, int colour) {
		Minecraft mc = Minecraft.getMinecraft();
		int strWidth = Math.round(mc.fontRenderer.getStringWidth(str) / 2.0f);
		mc.fontRenderer.drawStringWithShadow(str, x - strWidth, y, colour);
	}
	
	private void renderHeatGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		int currentHeat = HeatRegistry.getInstance(world).getValue(x, y, z);
		RenderOverlay.drawStringCentered(StringHelper.localize("gui.heat")+": "+currentHeat, scr_x, scr_y - 20, Colours.WHITE);
	}
}
