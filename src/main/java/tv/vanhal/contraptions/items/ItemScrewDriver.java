package tv.vanhal.contraptions.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.interfaces.IConfigurable;
import tv.vanhal.contraptions.util.Ref;

public class ItemScrewDriver extends BaseItem {
	
	public ItemScrewDriver() {
		super("screwDriver");
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		if (block instanceof IConfigurable) {
			boolean activated = false;
			if (player.isSneaking())
				activated = ((IConfigurable)block).sneakClick(player, world, x, y, z, side);
			else 
				activated = ((IConfigurable)block).click(player, world, x, y, z, side);
	        if (activated) return !world.isRemote;
		}
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
	
	@Override
	protected void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				"p  ", " i ", "   ", 'p', ContItems.plateIron, 'i', Items.iron_ingot});
		GameRegistry.addRecipe(recipe);
	}
}
