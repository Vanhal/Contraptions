package tv.vanhal.contraptions.crafting;

import tv.vanhal.contraptions.blocks.generation.BlockShaftExtender;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;

public class HeatRecipes {
	protected Object input;
	protected Object output;
	protected int heatRequired;
	protected boolean placedBlock;
	
	public HeatRecipes(ItemStack _output, ItemStack _input, int _heat) {
		input = _input;
		output = _output;
		heatRequired = _heat;
		placedBlock = false;
	}
	
	public HeatRecipes(Block _output, Block _input, int _heat) {
		input = _input;
		output = _output;
		heatRequired = _heat;
		placedBlock = true;
	}
	
	public boolean matches(Object stack) {
		if (stack == null) return false;
		if ( (stack instanceof ItemStack) && (!placedBlock) ) {
			if (((ItemStack)stack).isItemEqual(((ItemStack)input))) return true;
		} else if ( (stack instanceof Block) && (placedBlock) ) {
			Block block = (Block)stack;
			if (block == (Block)input) {
				return true;
			}
		}
		return false;
	}
	
	public int getRequiredHeat() {
		return heatRequired;
	}
	
	public boolean isBlockRecipe() {
		return placedBlock;
	}
	
	public Block getInputBlock() {
		if (placedBlock) {
			return (Block)input;
		}
		return Blocks.air;
	}
	
	public ItemStack getInput() {
		if (!placedBlock) {
			return (ItemStack)input;
		} else {
			return new ItemStack(Item.getItemFromBlock((Block)input));
		}
	}
	
	public ItemStack getOutput() {
		if (!placedBlock) {
			return (ItemStack)output;
		} else {
			if (Item.getItemFromBlock((Block)output) != null)
				return new ItemStack(Item.getItemFromBlock((Block)output));
			else if (FluidRegistry.lookupFluidForBlock((Block)output)==FluidRegistry.LAVA)
				return new ItemStack(Items.lava_bucket);
			else if (FluidRegistry.lookupFluidForBlock((Block)output)==FluidRegistry.WATER)
				return new ItemStack(Items.water_bucket);
			else
				return null;
		}
	}
	
	public Block getOutputBlock() {
		if (placedBlock) {
			return (Block)output;
		}
		return Blocks.air;
	}
}
