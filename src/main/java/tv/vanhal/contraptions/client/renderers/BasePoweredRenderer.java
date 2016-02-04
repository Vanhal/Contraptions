package tv.vanhal.contraptions.client.renderers;

import cofh.api.energy.IEnergyHandler;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class BasePoweredRenderer extends BaseRenderer {
	protected ResourceLocation midPower;
	protected ResourceLocation fullPower;
	
	public BasePoweredRenderer(String name) {
		midPower = new ResourceLocation(Ref.MODID, "textures/models/"+name+"_midpower.png");
		fullPower = new ResourceLocation(Ref.MODID, "textures/models/"+name+"_fullpower.png");
		this.setName(name);
	}

	@Override
	protected void setTexture(TileEntity tileentity) {
		if (tileentity instanceof IEnergyHandler) {
			int energy = ((IEnergyHandler)tileentity).getEnergyStored(null);
			if (energy==0) {
				bindTexture(texture);
			} else if (energy>=((IEnergyHandler)tileentity).getMaxEnergyStored(null)*0.75) {
				bindTexture(fullPower);
			} else {
				bindTexture(midPower);
			}
		}
		
	}
}
