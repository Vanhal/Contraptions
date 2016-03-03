package tv.vanhal.contraptions.fluids;

import java.util.Locale;

import tv.vanhal.contraptions.util.Ref;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContFluids {	
	public static Fluid steamFluid;
	
	public static FluidSteam steam;
	
	public static void preInit() {
		//fluids
		steamFluid = registerFluid("steam", -800, 0, 673, EnumRarity.COMMON);
		
		//blocks
		steam = new FluidSteam();
		steam.preInit();
	}
	
	public static void postInit() {
		steam.postInit();
	}
	
	public static Fluid registerFluid(String name, int density, EnumRarity rarity) {
		return registerFluid(name, density, -1, -1, rarity);
	}

	public static Fluid registerFluid(String name, int density, int lightValue, int temp, EnumRarity rarity) {
		name = name.toLowerCase(Locale.ENGLISH);
		Fluid fluid = new Fluid(name, 
				new ResourceLocation(Ref.MODID + ":blocks/fluids/"+name+".still"),
				new ResourceLocation(Ref.MODID + ":blocks/fluids/"+name+".flowing")
		);

		
		if (!FluidRegistry.registerFluid(fluid))
			fluid = FluidRegistry.getFluid(name);
		if (density != 0) {
			fluid.setDensity(density);
			fluid.setViscosity(Math.abs(density));
		}
		if (density<0)
			fluid.setGaseous(true);
		if (lightValue >= 0)
			fluid.setLuminosity(lightValue);
		if (temp >= 0)
			fluid.setTemperature(temp);
		//fluid.setUnlocalizedName(Ref.MODID + "." + name + ".still.name");
		fluid.setRarity(rarity);
		return fluid;
	}
}
