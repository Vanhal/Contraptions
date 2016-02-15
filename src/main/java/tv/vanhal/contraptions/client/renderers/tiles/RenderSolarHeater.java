package tv.vanhal.contraptions.client.renderers.tiles;

import org.lwjgl.opengl.GL11;

public class RenderSolarHeater extends BaseRenderer {

	public RenderSolarHeater() {
		setName("solarHeater");
	}
	
	@Override
	protected void setPosition(double x, double y, double z) {
        GL11.glTranslated(x+0.5, y+0.48, z+0.5);
	}
}
