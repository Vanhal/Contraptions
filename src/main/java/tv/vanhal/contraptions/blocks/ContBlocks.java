package tv.vanhal.contraptions.blocks;

public class ContBlocks {

	public static void preInit() {
		spike.preInit();
		poweredPiston.preInit();
		placer.preInit();
	}
	
	public static void init() {
		spike.init();
		poweredPiston.init();
		placer.init();
	}
	
	public static void postInit() {
		spike.postInit();
		poweredPiston.postInit();
		placer.postInit();
	}
	
	public static BlockSpike spike = new BlockSpike();
	public static BlockPoweredPiston poweredPiston = new BlockPoweredPiston();
	public static BlockPlacer placer = new BlockPlacer();
}
