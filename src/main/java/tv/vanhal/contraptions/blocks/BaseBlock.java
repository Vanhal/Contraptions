package tv.vanhal.contraptions.blocks;

import java.util.ArrayList;

import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.BlockHelper;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseBlock extends BlockContainer {
	public String name;
	public int GUIid = -1;
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public BaseBlock(String _name) {
		super(Material.iron);
		setDefaultState();

		name = _name;
		setUnlocalizedName(_name);
		
		setHardness(1.0f);
		setCreativeTab(Contraptions.ContraptionTab);
		
		GUIid = Contraptions.proxy.registerGui(_name);
		
		if (isCustomModel()) setLightOpacity(0);
	}
	
	@Override
    protected BlockState createBlockState() {
		if (getRotationType() == Axis.None) {
			return new BlockState(this, new IProperty[0]);
		} else {
		    return new BlockState(this, new IProperty[] {FACING});
		}
    }
	
    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return state;
    }
	
	protected void setDefaultState() {
		if (getRotationType() == Axis.None) {
			setDefaultState(blockState.getBaseState());
		} else {
			setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.WEST));
		}
	}
	
	public Axis getRotationType() {
		return Axis.None;
	}
	
	public boolean isCustomModel() {
		return false;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (GUIid>=0) {
			if (!world.isRemote) {
				FMLNetworkHandler.openGui(player, Contraptions.instance, GUIid, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return null;
	}
	
	public void preInit() {
		GameRegistry.registerBlock(this, name);
	}
	
	public void init() {
		addRecipe();
	}
	
	public void postInit() {
		if (Contraptions.proxy.isClient()) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Ref.MODID + ":" + name, "inventory"));
		}
	}
	
	public void addRecipe() {
		
	}
	
	
	@Override
    public boolean isFullBlock() {
        return !isCustomModel();
	}
	
	@Override
	public int getRenderType() {
        return 3;
    }
	
    @Override
    public boolean isOpaqueCube() { 
    	return !isCustomModel(); 
    }

    @Override
    public boolean isFullCube() {
        return !isCustomModel();
    }

    @Override
    public boolean isVisuallyOpaque() {
        return !isCustomModel();
    }
	
	@Override
    public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
		return 0;
    }
	
	@Override
	public int getStrongPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return side == side.DOWN ? this.getWeakPower(world, pos, state, side) : 0;
    }
	
	@Override
	public boolean canProvidePower() {
        return false;
    }
	
	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
        return canProvidePower();
    }
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int l = BlockHelper.determineOrientation(world, getRotationType(), pos, placer);
		setFacing(world, pos, l);
    }
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		BaseTile tileEntity = (BaseTile)world.getTileEntity(pos);

        if (tileEntity != null) {
            ArrayList<ItemStack> items = ItemHelper.getBlockContents(world, pos, tileEntity);
            
            for (ItemStack item: items) {
            	ItemHelper.dropAsItem(world, pos, item);
            }
            

            world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
        }
        super.breakBlock(world, pos, state);
    }
	
	protected int getFacing(IBlockAccess world, BlockPos pos) {
		if (getRotationType() == Axis.None) return 0;
		TileEntity tile = world.getTileEntity(pos);
		if ( (tile != null) && (tile instanceof BaseTile) ) {
			return ((BaseTile)tile).getFacing();
		}
		if (world.getBlockState(pos).getProperties().containsKey(FACING)) {
			return world.getBlockState(pos).getValue(FACING).ordinal();
		} else {
			return 0;
		}
	}
	
	protected void setFacing(World world, BlockPos pos, int facing) {
		if (getRotationType()!=Axis.None)
			world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, EnumFacing.values()[facing]), 2);
		TileEntity tile = world.getTileEntity(pos);
		if ( (tile != null) && (tile instanceof BaseTile) ) {
			((BaseTile)tile).setFacing(facing);
		}
	}
	
	@Override
    public int getMetaFromState(IBlockState state) {
		if (getRotationType() == Axis.None) return 0;
    	if (state.getValue(FACING) != null) {
    		return state.getValue(FACING).ordinal();
    	}
    	return 0;
    }
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		//if (worldObj.isRemote) return false;
		int currentFacing = getFacing(world, pos);
		int newFacing = BlockHelper.rotateBlock(currentFacing, getRotationType());
		setFacing(world, pos, newFacing);
		return (currentFacing != newFacing);
	}

	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        return BlockHelper.getValidFacing(getRotationType());
    }
	
	protected AxisAlignedBB getBounding(int _x, int _y, int _z, double _minX, double _minY, double _minZ, double _maxX, double _maxY, double _maxZ) {
		return AxisAlignedBB.fromBounds(
	        	(double)_x + _minX, (double)_y + _minY, (double)_z + _minZ, 
	        	(double)_x + _maxX, (double)_y + _maxY, (double)_z + _maxZ
	        );
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
		return getCollisionBoundingBox(world, pos, world.getBlockState(pos));
    }
	
	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		EnumFacing facing = world.getBlockState(pos).getValue(FACING);
		switch (facing)
		{
		case NORTH: setBlockBounds(0.0f, 0.0f, 0.875f, 2.0f, 1.0f, 1.0f); break;
		case SOUTH: setBlockBounds(0.0f, 0.0f, 0.0f, 2.0f, 1.0f, 0.125f); break;
		case WEST: setBlockBounds(0.875f, 0.0f, 0.0f, 1.0f, 1.0f, 2.0f); break;
		case EAST: setBlockBounds(0.0f, 0.0f, 0.0f, 0.125f, 1.0f, 2.0f); break;
		default: break;
		}
	}*/
}
