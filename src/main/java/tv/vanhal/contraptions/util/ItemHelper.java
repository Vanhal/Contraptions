package tv.vanhal.contraptions.util;

import java.util.ArrayList;
import java.util.List;

import tv.vanhal.contraptions.tiles.TileCrusher;
import tv.vanhal.contraptions.tiles.TileSolidBurner;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
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
	
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		if ( (stack1 == null) && (stack2 == null) ) return true;
		if (stack1 == null) return false;
		if (stack2 == null) return false;
		return ( 
			(stack1.isItemEqual(stack2)) && 
			(stack1.stackSize == stack2.stackSize) && 
			(stack1.getItemDamage() == stack2.getItemDamage())
		);
	}
	
    public static boolean areStacksSame(ItemStack itemStack1, ItemStack itemStack2, boolean checkMeta, boolean checkNBT) {
        return itemStack1 == null && itemStack2 == null || (!(itemStack1 == null || itemStack2 == null) && (itemStack1.getItem() == itemStack2.getItem() && ((!checkMeta || itemStack1.getItemDamage() == itemStack2.getItemDamage()) && (!checkNBT || !(itemStack1.stackTagCompound == null && itemStack2.stackTagCompound != null) && (itemStack1.stackTagCompound == null || itemStack1.stackTagCompound.equals(itemStack2.stackTagCompound))))));
    }
	
	public static boolean isFuel(ItemStack itemStack) {
		return (getBurnTime(itemStack)>0);
	}
	
	public static int getBurnTime(ItemStack itemStack) {
		return TileEntityFurnace.getItemBurnTime(itemStack);
	}
	
	public static boolean clickAddToTile(World world, int x, int y, int z, EntityPlayer player, int slot) {
		if (player.isSneaking()) {
			if (!world.isRemote) {
				if (player.getCurrentEquippedItem()==null) {
					TileEntity tile = world.getTileEntity(x, y, z);
					if ( (tile != null) && (tile instanceof ISidedInventory) ) {
						ISidedInventory inventory = (ISidedInventory)tile;
						if (inventory.getStackInSlot(slot)!=null) {
							dropAsItem(world, x, y + 1, z, inventory.getStackInSlot(slot));
							inventory.setInventorySlotContents(slot, null);
						}
					}
				}
			}
			return true;
		} else if (player.getCurrentEquippedItem() != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if ( (tile != null) && (tile instanceof ISidedInventory) ) {
				ISidedInventory inventory = (ISidedInventory)tile;
				if (inventory.isItemValidForSlot(slot, player.getCurrentEquippedItem())) {
					if (!world.isRemote) {
						if (inventory.canInsertItem(slot, player.getCurrentEquippedItem(), 0)) {
							if (inventory.getStackInSlot(slot)==null) {
								inventory.setInventorySlotContents(slot, new ItemStack(player.getCurrentEquippedItem().getItem(), 
										1, player.getCurrentEquippedItem().getItemDamage()));
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
							}
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}
