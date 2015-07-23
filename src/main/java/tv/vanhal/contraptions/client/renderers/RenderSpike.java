package tv.vanhal.contraptions.client.renderers;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSpike extends TileEntitySpecialRenderer {
	
	private IModelCustom spike;
    private ResourceLocation spikeTexture;
	
	public RenderSpike() {
		spikeTexture = new ResourceLocation(Ref.MODID, "textures/models/spike.png");
		spike = AdvancedModelLoader.loadModel(new ResourceLocation(Ref.MODID, "models/spike.obj"));
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y+0.5, z+0.5);

        //Bind the texture and render the model
        bindTexture(spikeTexture);
        spike.renderAll();

        //OpenGL stuff to put everything back
        GL11.glPopMatrix();

	}

}
