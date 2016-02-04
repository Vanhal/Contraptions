package tv.vanhal.contraptions.core;

import tv.vanhal.contraptions.client.renderers.RenderPlacer;
import tv.vanhal.contraptions.client.renderers.RenderPoweredPiston;
import tv.vanhal.contraptions.client.renderers.RenderRedstonePoweredPiston;
import tv.vanhal.contraptions.client.renderers.RenderSpike;
import tv.vanhal.contraptions.client.renderers.RenderSpreader;
import tv.vanhal.contraptions.tiles.TilePlacer;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends Proxy {
	@Override
	public void registerEntities() {
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpike.class, new RenderSpike());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TilePoweredPiston.class, new RenderPoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston.class, new RenderRedstonePoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TilePlacer.class, new RenderPlacer());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpreader.class, new RenderSpreader());
		super.registerEntities();
	}
}
