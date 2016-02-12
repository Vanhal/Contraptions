package tv.vanhal.contraptions.crafting;

import net.minecraft.item.ItemStack;

public class CrusherRecipes {
	protected ItemStack input;
	protected ItemStack output;
	protected int times;

	public CrusherRecipes(ItemStack _output, ItemStack _input, int _times) {
		input = _input;
		output = _output;
		times = _times;
	}
	
	public CrusherRecipes(ItemStack _output, ItemStack _input) {
		this(_output, _input, 1);
	}
	
	public boolean matches(ItemStack stack) {
		if (stack.isItemEqual(input)) return true;
		//do ore dic stuff
		return false;
	}
	
	public int getTimes() {
		return times;
	}
	
	public ItemStack getInput() {
		return input;
	}
	
	public ItemStack getOutput() {
		return output.copy();
	}
}
