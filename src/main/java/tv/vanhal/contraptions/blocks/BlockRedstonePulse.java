package tv.vanhal.contraptions.blocks;

import java.util.Random;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstonePulse extends Block {
	public static final Material invisable = new MaterialTransparent(MapColor.airColor);
	
	protected BlockRedstonePulse() {
		super(invisable);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int meta) {
        return 15;
    }
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int meta) {
        return 15;
    }
	
	@Override
	public int tickRate(World world) {
        return 1;
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rnd) {
		world.setBlockToAir(x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }
	
	@Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {}

}
