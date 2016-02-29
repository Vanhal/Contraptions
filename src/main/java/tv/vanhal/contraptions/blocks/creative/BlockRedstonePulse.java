package tv.vanhal.contraptions.blocks.creative;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstonePulse extends Block {
	public static final Material invisable = new MaterialTransparent(MapColor.airColor);
	
	public BlockRedstonePulse() {
		super(invisable);
		setUnlocalizedName("pulse");
		//this.setBlockTextureName(Ref.MODID+":invisable");
	}

	@Override
	public int getStrongPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return 15;
    }
	
	@Override
	public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return 15;
    }
	
	@Override
	public int tickRate(World world) {
        return 1;
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.setBlockToAir(pos);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!world.isRemote)
			world.scheduleBlockUpdate(pos, this, tickRate(world), 1);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote)
			world.scheduleBlockUpdate(pos, this, tickRate(world), 1);
    }
	
	@Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
    
    @Override
   public boolean isReplaceable(World worldIn, BlockPos pos) {
       return true;
   }
}
