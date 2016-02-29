package tv.vanhal.contraptions.client.renderers.tiles;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.base.Function;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

public class BaseRenderer extends TileEntitySpecialRenderer {
	
	protected IModel model = null;
	protected ResourceLocation texture;
	
	protected void setName(String name) {
		texture = new ResourceLocation(Ref.MODID, "textures/models/"+name+".png");
		try {
			model = OBJLoader.instance.loadModel(new ResourceLocation(Ref.MODID, "models/"+name+".obj"));
		} catch (IOException e) {
			Contraptions.logger.error("Can not load model: "+name);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		if (model!=null) {
			GL11.glPushMatrix();
	        
	        //Bind the texture and render the model
	        renderModel(tileentity, x, y, z, partialTicks);

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
        //model.renderAll();
		//modelRender(model);
	}
	
	protected void setTexture(TileEntity tileentity) {
		bindTexture(texture);
	}
	
	Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
	     public TextureAtlasSprite apply(ResourceLocation location) {
	         return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
	      }
	};
	
	protected void modelRender(IModel model) {
		IBakedModel bakedModel = model.bake((TRSRTransformation.identity()), Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
		Tessellator tessellator = Tessellator.getInstance();

		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(7, Attributes.DEFAULT_BAKED_FORMAT);// StartDrawingQuads

		List<BakedQuad> generalQuads = bakedModel.getGeneralQuads();
		for (BakedQuad q : generalQuads)
		{
			int[] vd = q.getVertexData();
			worldRenderer.addVertexData(vd);
		}
		for (EnumFacing face : EnumFacing.values())
		{
			List<BakedQuad> faceQuads = bakedModel.getFaceQuads(face);
			for (BakedQuad q : faceQuads)
			{
				int[] vd = q.getVertexData();
				worldRenderer.addVertexData(vd);
			}
		}
		tessellator.draw();
	}

}
