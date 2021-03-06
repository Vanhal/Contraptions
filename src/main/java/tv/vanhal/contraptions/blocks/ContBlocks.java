package tv.vanhal.contraptions.blocks;

import tv.vanhal.contraptions.blocks.creative.BlockCreativePower;
import tv.vanhal.contraptions.blocks.creative.BlockCreativeSteam;
import tv.vanhal.contraptions.blocks.creative.BlockRedstonePulse;
import tv.vanhal.contraptions.blocks.generation.BlockGenerator;
import tv.vanhal.contraptions.blocks.generation.BlockShaftExtender;
import tv.vanhal.contraptions.blocks.generation.BlockSolarHeater;
import tv.vanhal.contraptions.blocks.generation.BlockSolidBurner;
import tv.vanhal.contraptions.blocks.generation.BlockTurbine;
import tv.vanhal.contraptions.blocks.machines.BlockCrusher;
import tv.vanhal.contraptions.blocks.machines.BlockGrabber;
import tv.vanhal.contraptions.blocks.machines.BlockPlacer;
import tv.vanhal.contraptions.blocks.machines.BlockPoweredPiston;
import tv.vanhal.contraptions.blocks.machines.BlockRedstonePoweredPiston;
import tv.vanhal.contraptions.blocks.machines.BlockSpike;
import tv.vanhal.contraptions.blocks.machines.BlockSpreader;
import tv.vanhal.contraptions.blocks.passive.BlockConductivePlate;
import tv.vanhal.contraptions.blocks.transport.BlockRFPylon;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContBlocks {
	//machines
	public static BlockSpike spike = new BlockSpike();
	public static BlockPoweredPiston poweredPiston = new BlockPoweredPiston();
	public static BlockRedstonePoweredPiston redstonePoweredPiston = new BlockRedstonePoweredPiston();
	public static BlockPlacer placer = new BlockPlacer();
	public static BlockSpreader spreader = new BlockSpreader();
	public static BlockCrusher crusher = new BlockCrusher();
	public static BlockGrabber grabber = new BlockGrabber();
	
	//generation
	public static BlockSolidBurner solidBurner = new BlockSolidBurner();
	public static BlockSolarHeater solarHeater = new BlockSolarHeater();
	public static BlockGenerator generator = new BlockGenerator();
	public static BlockShaftExtender shaftExtender = new BlockShaftExtender();
	public static BlockTurbine turbine = new BlockTurbine();
	
	//tranport blocks
	public static BlockRFPylon rfPylon = new BlockRFPylon();
	
	//normal blocks (No entities)
	public static BlockConductivePlate conductivePlate = new BlockConductivePlate();
	
	//creativethings
	public static BlockCreativePower creativePower = new BlockCreativePower();
	public static BlockCreativeSteam creativeSteam = new BlockCreativeSteam();
	public static BlockRedstonePulse pulse = new BlockRedstonePulse();
	
	

	public static void preInit() {
		spike.preInit();
		poweredPiston.preInit();
		placer.preInit();
		spreader.preInit();
		redstonePoweredPiston.preInit();
		crusher.preInit();
		grabber.preInit();
		
		conductivePlate.preInit();
		
		solidBurner.preInit();
		solarHeater.preInit();
		generator.preInit();
		shaftExtender.preInit();
		turbine.preInit();
		
		rfPylon.preInit();
		
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
		crusher.init();
		grabber.init();
		
		conductivePlate.init();
		
		solidBurner.init();
		solarHeater.init();
		generator.init();
		shaftExtender.init();
		turbine.init();
		
		rfPylon.init();
		
		creativePower.init();
		creativeSteam.init();
	}
	
	public static void postInit() {
		spike.postInit();
		poweredPiston.postInit();
		placer.postInit();
		spreader.postInit();
		redstonePoweredPiston.postInit();
		crusher.postInit();
		grabber.postInit();
		
		conductivePlate.postInit();
		
		solidBurner.postInit();
		solarHeater.postInit();
		generator.postInit();
		shaftExtender.postInit();
		turbine.postInit();
		
		rfPylon.postInit();
		
		creativePower.postInit();
		creativeSteam.postInit();
	}
}
