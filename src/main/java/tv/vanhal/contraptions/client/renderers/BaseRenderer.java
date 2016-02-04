package tv.vanhal.contraptions.client.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class BaseRenderer extends TileEntitySpecialRenderer {
	
	protected IModelCustom model = null;
	protected ResourceLocation texture;
	
	protected void setName(String name) {
		texture = new ResourceLocation(Ref.MODID, "textures/models/"+name+".png");
		model = AdvancedModelLoader.loadModel(new ResourceLocation(Ref.MODID, "models/"+name+".obj"));
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		if (model!=null) {
			GL11.glPushMatrix();
	        
	        //Bind the texture and render the model
	        renderModel(tileentity, x, y, z, f);

	        //OpenGL stuff to put everything back
	        GL11.glPopMatrix();
		}
	}
	
	protected void setPosition(double x, double y, double z) {
        GL11.glTranslated(x+0.5, y+0.5, z+0.5);
	}
	
	protected void setRotation(TileEntity tileentity) {
		if (tileentity instanceof BaseTile) {
        	int direction = ((BaseTile)tileentity).getFacing();
        	if (direction>=0) {
        		if (direction==0) GL11.glRotatef(90, -1, 0, 0); //Down
        		if (direction==1) GL11.glRotatef(90, 1, 0, 0); //Up
        		if (direction==2) GL11.glRotatef(0, 0, 1, 0); //North
        		if (direction==3) GL11.glRotatef(180, 0, 1, 0); //South
        		if (direction==4) GL11.glRotatef(90, 0, 1, 0); //West
        		if (direction==5) GL11.glRotatef(90, 0, -1, 0); //East
        	}
        }
	}
	
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float f) {
        setTexture(tileentity);
		setPosition(x, y, z);
		setRotation(tileentity);
        model.renderAll();
	}
	
	protected void setTexture(TileEntity tileentity) {
		bindTexture(texture);
	}

}
