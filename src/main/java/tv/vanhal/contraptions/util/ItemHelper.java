package tv.vanhal.contraptions.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemHelper {
	public static void dropAsItem(World worldObj, int x, int y, int z, Block block, int meta) {
		if (block!=null) {
			ItemStack itemStack = new ItemStack(block, 1, meta);
			dropAsItem(worldObj, x, y, z, itemStack);
		}
	}
	
	
	public static void dropAsItem(World worldObj, int x, int y, int z, ItemStack itemStack) {
        if (!worldObj.isRemote) {
            float f = 0.7F;
            double d0 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(worldObj, (double)x + d0, (double)y + d1, (double)z + d2, itemStack);
            entityitem.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(entityitem);
        }
    }
	
	public static void dropBlockIntoWorld(World worldObj, int x, int y, int z, Block block, int meta) {
		List<ItemStack> drops = block.getDrops(worldObj, x, y, z, meta, 0);
		for (ItemStack itemStack: drops) {
			dropAsItem(worldObj, x, y, z, itemStack);
		}
		worldObj.setBlockToAir(x, y, z);
	}
	
	public static ArrayList<ItemStack> getBlockContents(World world, int x, int y, int z, TileEntity tileEntity) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (tileEntity!=null) {
			if (tileEntity instanceof IInventory) {
				IInventory inventory = (IInventory)tileEntity;
				for (int i = 0; i < inventory.getSizeInventory(); ++i) {
	                ItemStack itemstack = inventory.getStackInSlot(i);
	                if (itemstack != null) {
	                	items.add(itemstack);
	                }
	            }
			}
		}
		
		return items;
	}
}
