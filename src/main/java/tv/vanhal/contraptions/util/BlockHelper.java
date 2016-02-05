package tv.vanhal.contraptions.util;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockHelper {
	
	public static enum Axis {
		None, SixWay, FourWay;
	}

	public static int determineOrientation(World world, Axis axis, int x, int y, int z, EntityLivingBase entity) {
		if (axis == Axis.None) return 0;
		if (axis == Axis.SixWay) return BlockPistonBase.determineOrientation(world, x, y, z, entity);
		else if (axis == Axis.FourWay) {
	        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	        if (l == 0) return 2;
	        if (l == 1) return 5;
	        if (l == 2) return 3;
	        if (l == 3) return 4;
		}
		return 0;
	}
}
