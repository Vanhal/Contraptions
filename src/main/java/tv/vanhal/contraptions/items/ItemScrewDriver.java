package tv.vanhal.contraptions.items;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
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
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof IConfigurable) {
			boolean activated = false;
			if (player.isSneaking())
				activated = ((IConfigurable)block).sneakClick(player, world, pos, side);
			else 
				activated = ((IConfigurable)block).click(player, world, pos, side);
	        if (activated) return !world.isRemote;
		}
		if (player.isSneaking()) {
			//dismantle
		} else {
			block.rotateBlock(world, pos, side);
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
