package tv.vanhal.contraptions.blocks.passive;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IGuiRenderer;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.Colours;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.StringHelper;
import tv.vanhal.contraptions.world.RenderOverlay;
import tv.vanhal.contraptions.world.heat.HeatRegistry;

public class BlockConductivePlate extends BaseBlock implements IHeatBlock, IGuiRenderer {

	public BlockConductivePlate() {
		super("conductivePlate");
		this.setFaceTexture(1, "conductivePlate_top");
		this.setSidesTexture("conductivePlate_side");
		setRotationType(null);
	}

	@Override
	public int getMeltingPoint() {
		return 2000;
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

		RenderOverlay.drawStringCentered(StringHelper.localize("gui.heat")+": "+currentHeat, scr_x, scr_y - 20, color);
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
			"ppp", "sss", "sss", 'p', ContItems.plateIron, 's', Blocks.stone});
		GameRegistry.addRecipe(recipe);
	}

}
