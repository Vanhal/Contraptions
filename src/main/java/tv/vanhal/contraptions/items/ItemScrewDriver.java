package tv.vanhal.contraptions.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.util.Ref;

public class ItemScrewDriver extends BaseItem {
	
	public ItemScrewDriver() {
		super("screwDriver");
		setTextureName(Ref.MODID+":screwDriver");
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		if (player.isSneaking()) {
			//dismantle
			/*if (block instanceof IDismantleable) {
				IDismantleable dBlock = (IDismantleable)block;
				if (dBlock.canDismantle(player, world, x, y, z)) {
					if (!world.isRemote) dBlock.dismantleBlock(player, world, x, y, z, false);
				}
			}*/
		} else {
			ForgeDirection face = ForgeDirection.getOrientation(side);
			block.rotateBlock(world, x, y, z, face);

		}
        return !world.isRemote;
    }
}
