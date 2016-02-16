package tv.vanhal.contraptions.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IConfigurable {

	public boolean click(EntityPlayer player, World world, int x, int y, int z, int side);
	public boolean sneakClick(EntityPlayer player, World world, int x, int y, int z, int side);
}
