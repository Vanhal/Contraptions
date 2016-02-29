package tv.vanhal.contraptions.client.renderers.tiles;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.TileShaftExtender;

public class RenderShaftExtender extends BaseRenderer {

	public RenderShaftExtender() {
		setName("barExtender");
	}
	
	@Override
	protected void setRotation(TileEntity tileentity) {
		GL11.glRotatef(90, 0, 1, 0);

		super.setRotation(tileentity);
		
        //if it's running then spin the 
		if (tileentity instanceof TileShaftExtender) {
			TileShaftExtender shaft = (TileShaftExtender)tileentity;
	        if (shaft.isRunning()) {
	        	shaft.currentSpin += 5;
				if (shaft.currentSpin>=360) shaft.currentSpin = 0.0f;
				float move = 0.081f;
				GL11.glTranslated(0, move, 0);
				GL11.glRotatef(shaft.currentSpin, shaft.facing.getFrontOffsetX() + shaft.facing.getFrontOffsetZ(), 0, 0);
				GL11.glTranslated(0, move*-1, 0);
	        }
		}
	}
	
	@Override
	protected void setPosition(double x, double y, double z) {
        GL11.glTranslated(x+0.5, y+0.54, z+0.5);
	}
}
