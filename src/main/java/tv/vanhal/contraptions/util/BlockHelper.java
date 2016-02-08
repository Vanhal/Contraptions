package tv.vanhal.contraptions.util;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHelper {
	
	private static int[] rotateOrder = {0, 1, 2, 5, 3, 4};
	private static int[] orderLookup = {0, 1, 2, 4, 5, 3};

    private static final ForgeDirection[] FOURWAY_AXES = new ForgeDirection[] { 
    	ForgeDirection.WEST, 
    	ForgeDirection.EAST, 
    	ForgeDirection.NORTH, 
    	ForgeDirection.SOUTH
    };
    
    private static final ForgeDirection[] SIXWAY_AXES = new ForgeDirection[] { 
    	ForgeDirection.WEST, 
    	ForgeDirection.EAST, 
    	ForgeDirection.NORTH, 
    	ForgeDirection.SOUTH, 
    	ForgeDirection.UP, 
    	ForgeDirection.DOWN
    };
	
	public static enum Axis {
		None, SixWay, FourWay;
	}
	
	public static ForgeDirection[] getValidFacing(Axis input) {
		if (input == Axis.SixWay) return SIXWAY_AXES;
		if (input == Axis.FourWay) return FOURWAY_AXES;
		return new ForgeDirection[] {};
	}

	public static int determineOrientation(World world, Axis axis, int x, int y, int z, EntityLivingBase entity) {
		if (axis == Axis.None) return 0;
		if (axis == Axis.SixWay) return BlockPistonBase.determineOrientation(world, x, y, z, entity);
		else if (axis == Axis.FourWay) {
	        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	        return rotateOrder[2+l];
		}
		return 0;
	}
	
	public static int rotateBlock(int current, Axis axis) {
		if (axis == Axis.None) return 0;
		int order = orderLookup[current];
		order++;
		if (order>=6) {
			order = (axis==Axis.FourWay)?2:0;
		}
		return rotateOrder[order];
	}
}
