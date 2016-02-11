package tv.vanhal.contraptions.client.renderers.tiles;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.tiles.TileSpreader;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSpreader extends BasePoweredRenderer {
	protected IModelCustom spinner = null;
	protected ResourceLocation spinnerTexture;
	

	public RenderSpreader() {
		super("spreader");
		spinnerTexture = new ResourceLocation(Ref.MODID, "textures/models/spreaderTop.png");
		spinner = AdvancedModelLoader.loadModel(new ResourceLocation(Ref.MODID, "models/spreaderTop.obj"));
	}
	
	@Override
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float f) {
		setPosition(x, y-0.05, z);
        setTexture(tileentity);
        model.renderAll();
        
        GL11.glPopMatrix();
		GL11.glPushMatrix();
		//render the spinner
		GL11.glTranslated(x+0.5, y+0.65, z+0.5);
		if (tileentity instanceof TileSpreader) {
			TileSpreader tile = ((TileSpreader)tileentity);
			if ( (tile.currentSpin<1.0f) && (tile.isInCooldown()) ) {
				tile.currentSpin = 1.0f;
			}
			GL11.glRotatef(tile.currentSpin, 0, 1, 0);
			
			if (tile.currentSpin>0.5f) {
				tile.currentSpin += 15;
				if (tile.currentSpin>=180) tile.currentSpin = 0.0f;
			}
		}
		
        bindTexture(spinnerTexture);
        spinner.renderAll();
	}

}
