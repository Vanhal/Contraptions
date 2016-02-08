package tv.vanhal.contraptions.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.world.World;

public interface IGuiRenderer {
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res);
}
