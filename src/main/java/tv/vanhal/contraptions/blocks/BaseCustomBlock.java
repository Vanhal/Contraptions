package tv.vanhal.contraptions.blocks;

import java.util.Arrays;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import tv.vanhal.contraptions.util.BlockHelper.Axis;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BaseCustomBlock extends BaseBlock {

	public BaseCustomBlock(String _name) {
		super(_name);
	}
	
	@Override
	public boolean isCustomModel() {
		return true;
	}
	
	@Override
	protected BlockState createBlockState() {
		if (getRotationType() == Axis.None) {
			return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{OBJModel.OBJProperty.instance});

		} else {
			return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance});
		}
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		/*EnumFacing facing = EnumFacing.SOUTH;
		if (getRotationType() != Axis.None) {
			facing = (EnumFacing) state.getValue(FACING);
		}
        OBJModel.OBJState retState = new OBJModel.OBJState(Arrays.asList(new String[]{OBJModel.Group.ALL}), true, transpose(facing));
        return ((IExtendedBlockState) state).withProperty(OBJModel.OBJProperty.instance, retState);*/
		return state;
	}


}
