package tv.vanhal.contraptions.client.renderers.tiles;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;

public class RenderGenerator extends BasePoweredRenderer {

	public RenderGenerator() {
		super("generator");
	}
	
	@Override
	protected void setRotation(TileEntity tileentity) {
		GL11.glRotatef(90, 0, 1, 0);
		super.setRotation(tileentity);
	}
	
	@Override
	protected void setPosition(double x, double y, double z) {
        GL11.glTranslated(x+0.5, y+0.46, z+0.5);
	}
}
