package tv.vanhal.contraptions.core;

import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.TileCreativePower;
import tv.vanhal.contraptions.tiles.TileCreativeSteam;
import tv.vanhal.contraptions.tiles.TileGenerator;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.tiles.TilePoweredPiston;
import tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston;
import tv.vanhal.contraptions.tiles.TileShaftExtender;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import tv.vanhal.contraptions.tiles.TileSpike;
import tv.vanhal.contraptions.tiles.TileSpreader;
import tv.vanhal.contraptions.tiles.TileTurbine;
import tv.vanhal.contraptions.util.Ref;

public class Proxy {
	
	public void registerItems() {
		
	}

	public void registerEntities() {
		GameRegistry.registerTileEntity(TileSpike.class, Ref.MODID+":TileSpike");
		GameRegistry.registerTileEntity(TilePoweredPiston.class, Ref.MODID+":TilePoweredPiston");
		GameRegistry.registerTileEntity(TileRedstonePoweredPiston.class, Ref.MODID+":TileRedstonePoweredPiston");
		GameRegistry.registerTileEntity(TilePlacer.class, Ref.MODID+":TilePlacer");
		GameRegistry.registerTileEntity(TileSpreader.class, Ref.MODID+":TileSpreader");
		GameRegistry.registerTileEntity(TileSolidBurner.class, Ref.MODID+":TileSolidBurner");
		GameRegistry.registerTileEntity(TileGenerator.class, Ref.MODID+":TileGenerator");
		GameRegistry.registerTileEntity(TileShaftExtender.class, Ref.MODID+":TileShaftExtender");
		GameRegistry.registerTileEntity(TileTurbine.class, Ref.MODID+":TileTurbine");
		GameRegistry.registerTileEntity(TileCreativePower.class, Ref.MODID+":TileCreativePower");
		GameRegistry.registerTileEntity(TileCreativeSteam.class, Ref.MODID+":TileCreativeSteam");
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
