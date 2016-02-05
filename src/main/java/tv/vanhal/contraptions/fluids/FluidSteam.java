package tv.vanhal.contraptions.fluids;

import java.util.Random;

import tv.vanhal.contraptions.util.Ref;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends BlockFluidFinite {
	private static DamageSource steamDmg = new DamageSource("steam");
	{
			steamDmg.setDamageBypassesArmor().setFireDamage().setDifficultyScaled();
			steamDmg.setDamageIsAbsolute();
	}
	
	protected String name;

	protected IIcon still;
	protected IIcon flowing;
	
	public FluidSteam() {
		super(ContFluids.steamFluid, ContFluids.material);
		setName("steam");
		setRenderPass(1);
		displacements.put(this, false);
		setHardness(100.0F);
		setLightOpacity(3);
	}
	
	public void setName(String _name) {
		name = _name;
		setBlockName(name);
	}
	
	public void preInit() {
		GameRegistry.registerBlock(this, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		return side <= 1 ? still : flowing;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		still = ir.registerIcon(Ref.MODID + ":fluids/" + name + ".still");
		flowing = ir.registerIcon(Ref.MODID + ":fluids/" + name + ".flowing");
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock(world, x, y, z, entity);

		if (entity instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase)entity;
			ent.attackEntityFrom(steamDmg, 6);
		}
	}

}
