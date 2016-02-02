package tv.vanhal.contraptions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tv.vanhal.contraptions.tiles.TileSpike;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSpike extends BaseBlock {

	public BlockSpike() {
		super("spike", true);
        setBlockBounds(0.05f, 0.05f, 0.05f, 0.95f, 0.95f, 0.95f);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSpike();
	}


}
