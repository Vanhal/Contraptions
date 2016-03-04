package tv.vanhal.contraptions.blocks.machines;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileSpike;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockSpike extends BaseBlock {
	private static GameProfile SPIKE = new GameProfile(UUID.fromString("41382187-79fB-4624-B857-1352C9292A377"), "[Spike]");

	public BlockSpike() {
		super("spike", true);
        setBlockBounds(0.1f, 0.1f, 0.1f, 0.90f, 0.90f, 0.90f);
        setRotationType(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpike();
	}

	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"sps", "psp", "sps", 'p', ContItems.plateIron, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}
	
	@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
		if (!world.isRemote) {
			if ( (entity instanceof EntityLivingBase) && (world instanceof WorldServer) ) {
				EntityLivingBase ent = (EntityLivingBase)entity;
				ent.attackEntityFrom(DamageSource.causePlayerDamage(FakePlayerFactory.get((WorldServer) world, SPIKE)), 2);
			}
		}
    }
}
