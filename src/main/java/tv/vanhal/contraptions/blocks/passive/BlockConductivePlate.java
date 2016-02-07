package tv.vanhal.contraptions.blocks.passive;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.tiles.BaseTile;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.world.HeatRegistry;

public class BlockConductivePlate extends BaseBlock implements IHeatBlock {

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

}
