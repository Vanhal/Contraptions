package tv.vanhal.contraptions.blocks;

import tv.vanhal.contraptions.blocks.creative.BlockCreativePower;
import tv.vanhal.contraptions.blocks.creative.BlockCreativeSteam;
import tv.vanhal.contraptions.blocks.creative.BlockRedstonePulse;
import tv.vanhal.contraptions.blocks.generation.BlockSolidBurner;
import tv.vanhal.contraptions.blocks.machines.BlockPlacer;
import tv.vanhal.contraptions.blocks.machines.BlockPoweredPiston;
import tv.vanhal.contraptions.blocks.machines.BlockRedstonePoweredPiston;
import tv.vanhal.contraptions.blocks.machines.BlockSpike;
import tv.vanhal.contraptions.blocks.machines.BlockSpreader;
import tv.vanhal.contraptions.blocks.passive.BlockConductivePlate;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContBlocks {

	public static void preInit() {
		spike.preInit();
		poweredPiston.preInit();
		placer.preInit();
		spreader.preInit();
		redstonePoweredPiston.preInit();
		
		conductivePlate.preInit();
		
		solidBurner.preInit();
		
		creativePower.preInit();
		creativeSteam.preInit();
		GameRegistry.registerBlock(pulse, "pulse");
	}
	
	public static void init() {
		spike.init();
		poweredPiston.init();
		placer.init();
		spreader.init();
		redstonePoweredPiston.init();
		
		conductivePlate.init();
		
		solidBurner.init();
		
		creativePower.init();
		creativeSteam.init();
	}
	
	public static void postInit() {
		spike.postInit();
		poweredPiston.postInit();
		placer.postInit();
		spreader.postInit();
		redstonePoweredPiston.postInit();
		
		conductivePlate.postInit();
		
		solidBurner.init();
		
		creativePower.postInit();
		creativeSteam.postInit();
	}
	
	//machines
	public static BlockSpike spike = new BlockSpike();
	public static BlockPoweredPiston poweredPiston = new BlockPoweredPiston();
	public static BlockRedstonePoweredPiston redstonePoweredPiston = new BlockRedstonePoweredPiston();
	public static BlockPlacer placer = new BlockPlacer();
	public static BlockSpreader spreader = new BlockSpreader();
	
	//generation
	public static BlockSolidBurner solidBurner = new BlockSolidBurner();
	
	//normal blocks (No entities)
	public static BlockConductivePlate conductivePlate = new BlockConductivePlate();
	
	//creativethings
	public static BlockCreativePower creativePower = new BlockCreativePower();
	public static BlockCreativeSteam creativeSteam = new BlockCreativeSteam();
	public static BlockRedstonePulse pulse = new BlockRedstonePulse();
}
