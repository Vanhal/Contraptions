package tv.vanhal.contraptions.core;

import tv.vanhal.contraptions.client.renderers.RenderSpike;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends Proxy {
	@Override
	public void registerEntities() {
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpike.class, new RenderSpike());
		super.registerEntities();
	}
}
