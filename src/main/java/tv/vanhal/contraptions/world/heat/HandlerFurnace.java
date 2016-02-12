package tv.vanhal.contraptions.world.heat;

import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.interfaces.IHeatBlockHandler;
import tv.vanhal.contraptions.util.Point3I;

public class HandlerFurnace implements IHeatBlockHandler {

	@Override
	public boolean canProcess(int currentHeat) {
		return (currentHeat >= 500);
	}

	@Override
	public boolean processHeat(World world, Point3I point, int currentHeat, HeatRegistry heatReg) {
		if ( (canProcess(currentHeat)) && (!world.isRemote) ) {
			TileEntity tile = world.getTileEntity(point.getX(), point.getY(), point.getZ());
			if (tile instanceof TileEntityFurnace) {
				TileEntityFurnace furnace = (TileEntityFurnace)tile;
				if (canSmelt(furnace)) {
					//Because this doesn't tick every tick updating the block doesn't really work
					//if (furnace.furnaceBurnTime==0)
					// BlockFurnace.updateFurnaceBlockState(true, world, point.getX(), point.getY(), point.getZ());
					furnace.furnaceBurnTime = ContConfig.TICKS_PER_HEAT_TICK + 1;
					furnace.currentItemBurnTime = 1;
					heatReg.removeHeat(point, 10);
					furnace.markDirty();
				}
			}
		}
		return false;
	}
	
	//copy of vanilla code so that it can be checked here
    private boolean canSmelt(TileEntityFurnace furnace) {
        if (furnace.getStackInSlot(0) == null) return false;
        
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnace.getStackInSlot(0));
        if (itemstack == null) return false;
        if (furnace.getStackInSlot(2) == null) return true;
        if (!furnace.getStackInSlot(2).isItemEqual(itemstack)) return false;
        int result = furnace.getStackInSlot(2).stackSize + itemstack.stackSize;
        return result <= furnace.getInventoryStackLimit() && result <= furnace.getStackInSlot(2).getMaxStackSize();
    }

}
