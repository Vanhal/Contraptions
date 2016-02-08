package tv.vanhal.contraptions.core;

import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.TileCreativePower;
import tv.vanhal.contraptions.tiles.TileCreativeSteam;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.tiles.TileSpike;
import tv.vanhal.contraptions.tiles.TileSpreader;

public class Proxy {

	public void registerEntities() {
		GameRegistry.registerTileEntity(TileSpike.class, "TileSpike");
		GameRegistry.registerTileEntity(TilePoweredPiston.class, "TilePoweredPiston");
		GameRegistry.registerTileEntity(TileRedstonePoweredPiston.class, "TileRedstonePoweredPiston");
		GameRegistry.registerTileEntity(TilePlacer.class, "TilePlacer");
		GameRegistry.registerTileEntity(TileSpreader.class, "TileSpreader");
		GameRegistry.registerTileEntity(TileSolidBurner.class, "TileSolidBurner");
		GameRegistry.registerTileEntity(TileCreativePower.class, "TileCreativePower");
		GameRegistry.registerTileEntity(TileCreativeSteam.class, "TileCreativeSteam");
	}
	
	public int registerGui(String guiName) {
		return registerGui(guiName, guiName);
	}
	
	public int registerGui(String guiName, String containerName) {
		Class<?> gui = null;
		Class<?> container = null;
		try {
			gui = Proxy.class.getClassLoader().loadClass("tv.vanhal.contraptions.gui.client.GUI" + guiName);
		} catch (ClassNotFoundException e) {
			
		}
		try {
			container = Proxy.class.getClassLoader().loadClass("tv.vanhal.contraptions.gui.container.Container" + containerName);
		} catch (ClassNotFoundException e) {
			return -1;
		}
		if (gui == null) {
			return Contraptions.guiHandler.registerServerGui(container);
		} else {
			return Contraptions.guiHandler.registerGui(gui, container);
		}
		
	}
	
	public boolean isClient() {
		return false;
	}
	
	public boolean isServer() {
		return true;
	}
	
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
}
