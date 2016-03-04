package tv.vanhal.contraptions.fluids;

import java.util.Random;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Ref;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends BlockFluidFinite {
	private static final String FLUID_MODEL_PATH = Ref.MODID + ":fluid";
	
	private static DamageSource steamDmg = new DamageSource("steam");
	{
			steamDmg.setDamageBypassesArmor().setFireDamage().setDifficultyScaled();
			steamDmg.setDamageIsAbsolute();
	}
	
	protected String name;
	
	public FluidSteam() {
		super(ContFluids.steamFluid, new MaterialLiquid(MapColor.silverColor));
		setName("steam");
		displacements.put(this, false);
		setHardness(100.0F);
		setLightOpacity(3);
		setCreativeTab(Contraptions.ContraptionTab);
	}
	
	public void setName(String _name) {
		name = _name;
		this.setUnlocalizedName(name);
	}
	
	public void preInit() {
		GameRegistry.registerBlock(this, name);
	}
	
	public void postInit() {
		if (Contraptions.proxy.isClient()) {
			
		}
	}
	
    @SideOnly(Side.CLIENT)
	public void registerRenderer() {
		Item steamItem = Item.getItemFromBlock(this);
		ModelResourceLocation steamRes = new ModelResourceLocation(FLUID_MODEL_PATH, "steam");

		ModelBakery.registerItemVariants(steamItem);
		ModelLoader.setCustomMeshDefinition(steamItem, new ItemMeshDefinition() {
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(FLUID_MODEL_PATH, "steam");
            }
        });
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(FLUID_MODEL_PATH, "steam");
            }
        });
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		super.onEntityCollidedWithBlock(world, pos, entity);

		if (entity instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase)entity;
			ent.attackEntityFrom(steamDmg, 6);
		}
	}

}
