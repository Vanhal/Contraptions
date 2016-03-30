package tv.vanhal.contraptions.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import tv.vanhal.contraptions.util.ItemHelper;

public class TileFrame extends BaseInventoryTile {

	public TileFrame() {
		super(1);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, EnumFacing face) {
		return true;
	}
}
