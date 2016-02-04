package tv.vanhal.contraptions.fluids;

import java.util.Locale;

import tv.vanhal.contraptions.util.Ref;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContFluids {
	public static final Material material = new MaterialLiquid(MapColor.waterColor);
	
	public static Fluid steamFluid;
	
	public static FluidSteam steam;
	
	public static void preInit() {
		//fluids
		steamFluid = registerFluid("steam", -20, 0, 173, EnumRarity.common);
		
		//blocks
		steam = new FluidSteam();
		steam.preInit();
	}
	
	public static Fluid registerFluid(String name, int density, EnumRarity rarity) {
		return registerFluid(name, density, -1, -1, rarity);
	}

	public static Fluid registerFluid(String name, int density, int lightValue, int temp, EnumRarity rarity) {
		name = name.toLowerCase(Locale.ENGLISH);
		Fluid fluid = new Fluid(name);
		if (!FluidRegistry.registerFluid(fluid))
			fluid = FluidRegistry.getFluid(name);
		if (density != 0) {
			fluid.setDensity(density);
			fluid.setViscosity(Math.abs(density));
		}
		if (lightValue >= 0)
			fluid.setLuminosity(lightValue);
		if (temp >= 0)
			fluid.setTemperature(temp);
		fluid.setUnlocalizedName(Ref.MODID + "." + name + ".still.name");
		fluid.setRarity(rarity);
		return fluid;
	}
}
