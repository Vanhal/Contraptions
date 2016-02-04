package tv.vanhal.contraptions.compat;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Point3I;
import cpw.mods.fml.common.Loader;

public class BaseMod {
	public String modID = "base";

	public boolean shouldLoad() {
		return checkModLoad();
	}
	
	protected boolean checkModLoad() {
		if (Loader.isModLoaded(modID)) {
			if (ContConfig.config.getBoolean(modID, "ModCompatibility", true, "Enable support for "+modID)) {
				Contraptions.logger.info(modID+" Loaded");
				return true;
			} else {
				Contraptions.logger.info(modID+" Found, but compatibility has been disabled in the configs");
				return false;
			}
		} else {
			Contraptions.logger.info(modID+" not found, not loading");
			return false;
		}
	}
		
	public boolean isPlantible(ItemStack item) {
		return false;
	}
	
	public boolean shouldHoe(ItemStack item) {
		return false;
	}
	
	//check if a block in the world is an ungrown plant	
	public boolean isPlant(Block plantBlock, int metadata) {
		return false;
	}

	//check if the ground is valid for planting the given seed
	public boolean validBlock(World worldObj, ItemStack itemStack, Point3I testPoint) {
		return false;
	}

	//actually place the seeds
	public boolean placeSeed(World worldObj, ItemStack itemStack, Point3I testPoint, boolean doAction) {
		return false;
	}
}
