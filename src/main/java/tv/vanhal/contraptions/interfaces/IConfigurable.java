package tv.vanhal.contraptions.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IConfigurable {

	public boolean click(EntityPlayer player, World world, BlockPos pos, EnumFacing side);
	public boolean sneakClick(EntityPlayer player, World world, BlockPos pos, EnumFacing side);
}
