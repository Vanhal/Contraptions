package tv.vanhal.contraptions.client.renderers.tiles;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.tiles.TileTurbine;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;

public class RenderTurbine extends BaseRenderer {
	protected IModel middle = null;
	protected ResourceLocation middleTexture;

	public RenderTurbine() {
		setName("turbineBase");
		middleTexture = new ResourceLocation(Ref.MODID, "textures/models/turbineCenter.png");
		try {
			middle = OBJLoader.instance.loadModel(new ResourceLocation(Ref.MODID, "models/turbineCenter.obj"));
		} catch (IOException e) {}
	}
	
	@Override
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof TileTurbine) {
			TileTurbine turbine = ((TileTurbine)tileentity);
			EnumFacing facing = turbine.facing;

	        GL11.glTranslated(x + 0.5f, y + 0.46f, z  + 0.5f);
	        
	        GL11.glRotatef(90, 0, 1, 0);
	        setRotation(tileentity);
	        setTexture(tileentity);
	        //model.renderAll();
	        
	        GL11.glPopMatrix();
			GL11.glPushMatrix();
				
	        //render the extender
			GL11.glTranslated(x + 0.5f, y + 0.34f, z + 0.5f);
			GL11.glRotatef(90, 0, 1, 0);
	        setRotation(tileentity);
	        
	        //if it's running then spin the 
	        if (turbine.isRunning()) {
	        	turbine.currentSpin += 5;
				if (turbine.currentSpin>=360) turbine.currentSpin = 0.0f;
				float move = 0.28f;
				GL11.glTranslated(0, move, 0);
				GL11.glRotatef(turbine.currentSpin, facing.getFrontOffsetX() + facing.getFrontOffsetZ(), 0, 0);
				GL11.glTranslated(0, move*-1, 0);
	        }

	        bindTexture(middleTexture);
	        //middle.renderAll();
		}
	}
}
