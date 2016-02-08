package tv.vanhal.contraptions.blocks.passive;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.world.HeatRegistry;
import tv.vanhal.contraptions.world.RenderOverlay;

public class BlockConductivePlate extends BaseBlock implements IHeatBlock, IGuiRenderer {

	public BlockConductivePlate() {
		super("conductivePlate");
		this.setFaceTexture(1, "conductivePlate_top");
		this.setSidesTexture("conductivePlate_side");
		setRotationType(null);
	}

	@Override
	public int getMeltingPoint() {
		return 1000;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote)
			HeatRegistry.getInstance(world).addHeatBlock(x, y, z);
		super.onBlockAdded(world, x, y, z);
    }

	@Override
	public void renderGUI(World world, int x, int y, int z, ScaledResolution res) {
		int scr_x = res.getScaledWidth() / 2;
		int scr_y = res.getScaledHeight() / 2;
		
		int currentHeat = HeatRegistry.getInstance(world).getValue(x, y, z);
		int color = Colours.WHITE;
		if (currentHeat >= getMeltingPoint()*0.85) color = Colours.RED;
		else if (currentHeat >= getMeltingPoint()*0.6) color = Colours.ORANGE;

		RenderOverlay.drawStringCentered("Heat: "+currentHeat, scr_x, scr_y - 20, color);
	}
	
	

}
