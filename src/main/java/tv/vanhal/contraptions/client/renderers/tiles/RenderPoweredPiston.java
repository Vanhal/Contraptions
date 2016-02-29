package tv.vanhal.contraptions.client.renderers.tiles;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;

public class RenderPoweredPiston extends BasePoweredRenderer {
	protected IModel extender = null;
	protected ResourceLocation extenderTexture;
	
	public RenderPoweredPiston() {
		this("poweredPistonExtender");
	}
	
	public RenderPoweredPiston(String type) {
		super("poweredPistonBase");
		extenderTexture = new ResourceLocation(Ref.MODID, "textures/models/"+type+".png");
		try {
			extender = OBJLoader.instance.loadModel(new ResourceLocation(Ref.MODID, "models/poweredPistonExtender.obj"));
		} catch (IOException e) {}
	}

	@Override
	protected void renderModel(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof TilePoweredPiston) {
			EnumFacing facing = ((TilePoweredPiston)tileentity).facing;

			//Contraptions.logger.info(facing.offsetX+", "+facing.offsetY+", "+facing.offsetZ);
			double dx = (facing.getFrontOffsetX()<0)?0.58:(facing.getFrontOffsetX()>0)?0.42:0.5;
			double dy = (facing.getFrontOffsetY()==0)?0.46:((facing.getFrontOffsetY()>0)?0.42:0.58);
			double dz = (facing.getFrontOffsetZ()==0)?(facing.getFrontOffsetY()==0)?0.5:((facing.getFrontOffsetY()>0)?0.46:0.54):((facing.getFrontOffsetZ()>0)?0.42:0.58);
	        GL11.glTranslated(x+dx, y+dy, z+dz);
	        
	        setRotation(tileentity);
	        setTexture(tileentity);
	        //model.renderAll();
	        
	        GL11.glPopMatrix();
			GL11.glPushMatrix();
	
			boolean inCooldown = ((TilePoweredPiston)tileentity).isInCooldown();
			dx = (facing.getFrontOffsetX()==0)?0.5:((inCooldown)?((facing.getFrontOffsetX()>0)?0.9:0.1):((facing.getFrontOffsetX()>0)?0.7:0.3));
			dy = (facing.getFrontOffsetY()==0)?0.5:((inCooldown)?((facing.getFrontOffsetY()>0)?0.9:0.1):((facing.getFrontOffsetY()>0)?0.7:0.3));
			dz = (facing.getFrontOffsetZ()==0)?0.5:((inCooldown)?((facing.getFrontOffsetZ()>0)?0.9:0.1):((facing.getFrontOffsetZ()>0)?0.7:0.3));
	        
	        //render the extender
			GL11.glTranslated(x+dx, y+dy, z+dz);

	        setRotation(tileentity);
	        bindTexture(extenderTexture);
	        //extender.renderAll();
		}
	}
}
