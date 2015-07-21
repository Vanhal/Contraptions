package tv.vanhal.contraptions.blocks;

public class ContBlocks {

	public static void preInit() {
		spike.preInit();
	}
	
	public static void init() {
		spike.init();
	}
	
	public static void postInit() {
		spike.postInit();
	}
	
	public static BlockSpike spike = new BlockSpike();
}
