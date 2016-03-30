package tv.vanhal.contraptions.blocks.passive;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.items.resources.ItemSilt;
import tv.vanhal.contraptions.util.BlockHelper.Axis;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.Ref;

public class BlockSilt extends BaseBlock {
    public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);

	public BlockSilt() {
		super("silt");
	}
	
	@Override
    protected BlockState createBlockState() {
	    return new BlockState(this, new IProperty[] {LAYERS});
    }

	@Override
	protected void setDefaultState() {
		setDefaultState(blockState.getBaseState().withProperty(LAYERS, 4));
	}
    
	@Override
    public int getMetaFromState(IBlockState state) {
    	if (state.getValue(LAYERS) != null) {
    		return state.getValue(LAYERS);
    	}
    	return 0;
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
		if ( (meta==0) || (meta>8) ) return this.getDefaultState();
        return this.getDefaultState().withProperty(LAYERS, meta);
    }

	@Override
    public boolean isOpaqueCube() {
        return false;
    }

	@Override
    public boolean isFullCube() {
        return false;
    }

	@Override
	public void preInit() {
		GameRegistry.registerBlock(this, ItemSilt.class, name);
	}

	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        this.getBoundsForLayers(((Integer)iblockstate.getValue(LAYERS)).intValue());
    }

    protected void getBoundsForLayers(int p_150154_1_) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (float)p_150154_1_ / 8.0F, 1.0F);
    }

}
