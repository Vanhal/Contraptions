package tv.vanhal.contraptions.blocks;

import cpw.mods.fml.common.registry.GameRegistry;

public class ContBlocks {

	public static void preInit() {
		spike.preInit();
		poweredPiston.preInit();
		placer.preInit();
		spreader.preInit();
		redstonePoweredPiston.preInit();
		
		GameRegistry.registerBlock(pulse, "pulse");
	}
	
	public static void init() {
		spike.init();
		poweredPiston.init();
		placer.init();
		spreader.init();
		redstonePoweredPiston.init();
	}
	
	public static void postInit() {
		spike.postInit();
		poweredPiston.postInit();
		placer.postInit();
		spreader.postInit();
		redstonePoweredPiston.postInit();
	}
	
	public static BlockSpike spike = new BlockSpike();
	public static BlockPoweredPiston poweredPiston = new BlockPoweredPiston();
	public static BlockRedstonePoweredPiston redstonePoweredPiston = new BlockRedstonePoweredPiston();
	public static BlockPlacer placer = new BlockPlacer();
	public static BlockSpreader spreader = new BlockSpreader();
	
	public static BlockRedstonePulse pulse = new BlockRedstonePulse();
}
