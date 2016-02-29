package tv.vanhal.contraptions.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.GUIHelper;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderOverlay {
	
	@SubscribeEvent
	public void RenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
		
		if ( (currentItem != null) && (currentItem.getItem() == ContItems.screwDriver) ) {
			if(e.type == ElementType.ALL) {
				MovingObjectPosition mouseOver = mc.objectMouseOver;
				if(mouseOver != null) {
					BlockPos pos = mouseOver.getBlockPos();
					Block mouseOverBlock = mc.theWorld.getBlockState(pos).getBlock();
					if (mouseOverBlock instanceof IGuiRenderer) {
						((IGuiRenderer)mouseOverBlock).renderGUI(mc.theWorld, pos.getX(), pos.getY(), pos.getZ(), e.resolution);
					} else if (HeatRegistry.getInstance(mc.theWorld).isHeatBlock(pos.getX(), pos.getY(), pos.getZ())) {
						renderHeatGUI(mc.theWorld, pos.getX(), pos.getY(), pos.getZ(), e.resolution);
					}
				}
			}
		}
	}
	
	public static void drawItemStack(ItemStack itemStack, int x, int y, boolean overlay) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		mc.getRenderItem().renderItemIntoGUI(itemStack, x - 8, y);
		if (overlay) {
			mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack, x - 8, y, null);
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}
	
	public static void drawProgressBar(int colour, float progress, int x, int y, int width) {
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		int blue = (colour >> 24 & 255);
		int red = (colour >> 16 & 255);
		int green = (colour >> 8 & 255);
		
		float halfWidth = width / 2.0f;
		double dx = x - halfWidth;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
		//tess.setColorRGBA_I(Colours.BLACK, 140);
        wr.pos(dx + width, y, 0).color(0, 0, 0, 140).endVertex();
        wr.pos(dx, y, 0).color(0, 0, 0, 140).endVertex();
        wr.pos(dx, y + 6, 0).color(0, 0, 0, 140).endVertex();
        wr.pos(dx + width, y + 6, 0).color(0, 0, 0, 140).endVertex();
		
		dx += 1;
		width -= 2;
		double percentWidth = width * progress;
		
		//tess.setColorRGBA_I(colour, 250);
		wr.pos(dx + percentWidth, y + 1, 0).color(red, green, blue, 250).endVertex();
		wr.pos(dx, y + 1, 0).color(red, green, blue, 250).endVertex();
		wr.pos(dx, y + 5, 0).color(red, green, blue, 250).endVertex();
		wr.pos(dx + percentWidth, y + 5, 0).color(red, green, blue, 250).endVertex();
		
		tessellator.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void drawStringCentered(String str, int x, int y, int colour) {
		Minecraft mc = Minecraft.getMinecraft();
		GUIHelper.DrawShadowStringCentered(mc.fontRendererObj, str, x, y, colour);
	}
	
	private void renderHeatGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		int currentHeat = HeatRegistry.getInstance(world).getValue(x, y, z);
		RenderOverlay.drawStringCentered(StringHelper.localize("gui.heat")+": "+currentHeat, scr_x, scr_y - 20, Colours.WHITE);
	}
}
