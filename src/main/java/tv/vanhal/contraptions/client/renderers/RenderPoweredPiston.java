package tv.vanhal.contraptions.client.renderers;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderPoweredPiston extends BasePoweredRenderer {
	protected IModelCustom extender = null;
	protected ResourceLocation extenderTexture;
	
	public RenderPoweredPiston() {
		this("poweredPistonExtender");
	}
	
	public RenderPoweredPiston(String type) {
		super("poweredPistonBase");
		extenderTexture = new ResourceLocation(Ref.MODID, "textures/models/"+type+".png");
		extender = AdvancedModelLoader.loadModel(new ResourceLocation(Ref.MODID, "models/poweredPistonExtender.obj"));
	}

	@Override
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof TilePoweredPiston) {
			ForgeDirection facing = ((TilePoweredPiston)tileentity).facing;
			

			//Contraptions.logger.info(facing.offsetX+", "+facing.offsetY+", "+facing.offsetZ);
			double dx = (facing.offsetX<0)?0.58:(facing.offsetX>0)?0.42:0.5;
			double dy = (facing.offsetY==0)?0.46:((facing.offsetY>0)?0.42:0.58);
			double dz = (facing.offsetZ==0)?(facing.offsetY==0)?0.5:((facing.offsetY>0)?0.46:0.54):((facing.offsetZ>0)?0.42:0.58);
	        GL11.glTranslated(x+dx, y+dy, z+dz);
	        
	        setRotation(tileentity);
	        setTexture(tileentity);
	        model.renderAll();
	        
	        GL11.glPopMatrix();
			GL11.glPushMatrix();
	
			boolean inCooldown = ((TilePoweredPiston)tileentity).isInCooldown();
			dx = (facing.offsetX==0)?0.5:((inCooldown)?((facing.offsetX>0)?0.9:0.1):((facing.offsetX>0)?0.7:0.3));
			dy = (facing.offsetY==0)?0.5:((inCooldown)?((facing.offsetY>0)?0.9:0.1):((facing.offsetY>0)?0.7:0.3));
			dz = (facing.offsetZ==0)?0.5:((inCooldown)?((facing.offsetZ>0)?0.9:0.1):((facing.offsetZ>0)?0.7:0.3));
	        
	        //render the extender
			GL11.glTranslated(x+dx, y+dy, z+dz);

	        setRotation(tileentity);
	        bindTexture(extenderTexture);
	        extender.renderAll();
		}
	}
}
