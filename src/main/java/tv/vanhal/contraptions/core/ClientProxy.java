package tv.vanhal.contraptions.core;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import tv.vanhal.contraptions.client.renderers.items.RenderScrewDriver;
import tv.vanhal.contraptions.client.renderers.tiles.RenderPlacer;
import tv.vanhal.contraptions.client.renderers.tiles.RenderPoweredPiston;
import tv.vanhal.contraptions.client.renderers.tiles.RenderRedstonePoweredPiston;
import tv.vanhal.contraptions.client.renderers.tiles.RenderSpike;
import tv.vanhal.contraptions.client.renderers.tiles.RenderSpreader;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.world.RenderOverlay;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends Proxy {
	
	@Override
	public void registerItems() {
		MinecraftForgeClient.registerItemRenderer(ContItems.screwDriver, new RenderScrewDriver());
	}
	
	@Override
	public void registerEntities() {
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpike.class, new RenderSpike());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TilePoweredPiston.class, new RenderPoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston.class, new RenderRedstonePoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TilePlacer.class, new RenderPlacer());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpreader.class, new RenderSpreader());
		super.registerEntities();
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
	
	@Override
	public boolean isServer() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new RenderOverlay());
	}
}
