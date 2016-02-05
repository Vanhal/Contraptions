package tv.vanhal.contraptions.blocks;

import java.util.ArrayList;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseBlock extends BlockContainer {
	public String name;
	public int GUIid = -1;
	protected boolean isCustomModel = false;
	
	protected Axis rotationType = Axis.SixWay;
	
	protected IIcon[] blockIcons = new IIcon[6];
	protected IIcon frontIcon = null;
	protected IIcon frontActiveIcon = null;
	protected String[] blockTextures = new String[6];
	protected String frontTexture = "";
	protected String frontActiveTexture = "";
	
	
	public BaseBlock(String _name) {
		this(_name, false);
	}
	
	public BaseBlock(String _name, boolean _customModel) {
		super(Material.iron);
		this.isCustomModel = _customModel;
		setBlockName(_name);
		name = _name;
		
		setHardness(1.0f);
		setCreativeTab(Contraptions.ContraptionTab);
		
		GUIid = Contraptions.proxy.registerGui(_name);
		setMainTexture(_name);
		
		if (_customModel) setLightOpacity(0);
	}
	
	public void setRotationType(Axis axis) {
		if (axis == null) rotationType = Axis.None;
		rotationType = axis;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (GUIid>=0) {
			if (!world.isRemote) {
				FMLNetworkHandler.openGui(player, Contraptions.instance, GUIid, world, x, y, z);
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
		addRecipe();
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public void addRecipe() {
		
	}
	
	//normal block stuff
	public void setMainTexture(String _name) {
		for (int i = 0; i < 6; i++) {
			setFaceTexture(i, _name);
		}
	}
	
	public void setFaceTexture(int side, String _name) {
		blockTextures[side] = Ref.MODID+":"+_name;
	}
	
	public void setSidesTexture(String _name) {
		for (int i = 2; i < 6; i++) {
			setFaceTexture(i, _name);
		}
	}
	
	public void setFrontTexture(String _name) {
		frontTexture = Ref.MODID+":"+_name;
	}
	
	public void setFrontActiveTexture(String _name) {
		frontActiveTexture = Ref.MODID+":"+_name;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (frontIcon!=null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof BaseTile) {
				if ( ((BaseTile)tile).facing.ordinal() == side) {
					if ( ((BaseTile)tile).isActive() ) {
						return frontActiveIcon;
					} else {
						return frontIcon;
					}
				}
			} else {
				if (side == world.getBlockMetadata(x, y, z)) {
					//return frontIcon;
				}
			}
		}
		return this.getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		for (int i = 0; i < 6; i++) {
			blockIcons[i] = register.registerIcon(blockTextures[i]);
		}
		if (frontTexture!="") frontIcon = register.registerIcon(frontTexture);
		if (frontActiveTexture!="") frontActiveIcon = register.registerIcon(frontActiveTexture);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if ( (rotationType != Axis.FourWay) && (side==meta) && (frontIcon!=null) ) return frontIcon;
		else if ( (meta==0) && (side==3) && (frontIcon!=null) ) return frontIcon;
		return blockIcons[side];
	}
	
	@Override
	public int getRenderType() {
        return (isCustomModel)?-1:0;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return !isCustomModel;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return !isCustomModel;
	}
	
	@Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return !isCustomModel;
    }
	
	@Override
	public int isProvidingWeakPower(IBlockAccess block, int x, int y, int z, int side) {
		return 0;
    }
	
	@Override
	public int isProvidingStrongPower(IBlockAccess block, int x, int y, int z, int side) {
        return side == 0 ? this.isProvidingWeakPower(block, x, y, z, side) : 0;
    }
	
	@Override
	public boolean canProvidePower() {
        return false;
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		int l = BlockHelper.determineOrientation(world, rotationType, x, y, z, entity);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
		TileEntity tile = world.getTileEntity(x, y, z);
		if ( (tile != null) && (tile instanceof BaseTile) ) {
			((BaseTile)tile).setFacing(l);
		}
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
		BaseTile tileEntity = (BaseTile)world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            ArrayList<ItemStack> items = ItemHelper.getBlockContents(world, x, y, z, tileEntity);
            
            for (ItemStack item: items) {
            	ItemHelper.dropAsItem(world, x, y, z, item);
            }
            

            world.func_147453_f(x, y, z, block);
        }
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
}
