package tv.vanhal.contraptions.client.renderers;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderPoweredPiston extends BaseRenderer {
	protected ResourceLocation active;
	
	public RenderPoweredPiston() {
		active = new ResourceLocation(Ref.MODID, "textures/models/poweredRedstonePiston.png");
		this.setName("poweredPiston");
	}
	
	@Override
	protected void setTexture(TileEntity tileentity) {
		if (tileentity instanceof TilePoweredPiston) {
			if (((TilePoweredPiston)tileentity).isInCooldown()) {
				bindTexture(active);
			} else {
				bindTexture(texture);
			}
		}
	}
}
