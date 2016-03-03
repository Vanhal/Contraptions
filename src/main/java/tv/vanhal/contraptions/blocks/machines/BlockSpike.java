package tv.vanhal.contraptions.blocks.machines;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileSpike;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockSpike extends BaseCustomBlock {
    private static GameProfile SPIKE = new GameProfile(UUID.fromString("41382187-79fB-4624-B857-1352C9292A377"), "[Spike]");

	public BlockSpike() {
		super("spike");
        setBlockBounds(0.1f, 0.1f, 0.1f, 0.90f, 0.90f, 0.90f);
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
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		super.onEntityCollidedWithBlock(world, pos, entity);
		if (!world.isRemote) {
			if ( (entity instanceof EntityLivingBase) && (world instanceof WorldServer) ) {
				EntityLivingBase ent = (EntityLivingBase)entity;
				ent.attackEntityFrom(DamageSource.causePlayerDamage(FakePlayerFactory.get((WorldServer) world, SPIKE)), 2);
			}
		}
    }
}
