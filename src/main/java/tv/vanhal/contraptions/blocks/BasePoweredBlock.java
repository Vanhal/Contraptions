package tv.vanhal.contraptions.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import tv.vanhal.contraptions.blockstates.EnumPowered;
import tv.vanhal.contraptions.blockstates.PropertyPowered;
import tv.vanhal.contraptions.tiles.BasePoweredTile;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BasePoweredBlock extends BaseCustomBlock {
	public static final PropertyPowered POWERED = PropertyPowered.create("powered");

	public BasePoweredBlock(String _name) {
		super(_name);
	}
	
	@Override
	protected BlockState createBlockState() {
		if (getRotationType() == Axis.None) {
			return new ExtendedBlockState(this, new IProperty[]{POWERED}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance});

		} else {
			return new ExtendedBlockState(this, new IProperty[]{FACING, POWERED}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance});
		}
	}
	
	protected void setDefaultState() {
		if (getRotationType() == Axis.None) {
			setDefaultState(blockState.getBaseState().withProperty(POWERED, EnumPowered.unpowered));
		} else {
			setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.WEST).withProperty(POWERED, EnumPowered.unpowered));
		}
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getTileEntity(pos) instanceof BasePoweredTile) {
			BasePoweredTile poweredTile = (BasePoweredTile) world.getTileEntity(pos);
			EnumPowered newState = poweredTile.getPoweredStatus();
			EnumPowered currentState = state.getValue(POWERED);
			if (newState!=currentState) {
				return state.withProperty(POWERED, newState);
			}
		}
		return state;
	}

}
