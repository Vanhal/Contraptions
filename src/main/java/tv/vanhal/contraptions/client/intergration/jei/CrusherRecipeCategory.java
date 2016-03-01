package tv.vanhal.contraptions.client.intergration.jei;

import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.util.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrusherRecipeCategory implements IRecipeCategory {
	private final IDrawableStatic background;

	public CrusherRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(Ref.MODID, "textures/gui/neiCrusher.png");
		background = guiHelper.createDrawable(location, 0, 0, 166, 50, 0, 0, 0, 0);
	}

	@Override
	public String getUid() {
		return Ref.MODID.toLowerCase()+".crusher";
	}

	@Override
	public String getTitle() {
		return StringHelper.localize("tile.crusher.name");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {

	}

	@Override
	public void drawAnimations(Minecraft minecraft) {

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		if (!(recipeWrapper instanceof CrusherRecipeWrapper)) return;
		
		CrusherRecipeWrapper wrapper = (CrusherRecipeWrapper)recipeWrapper;
		int index = 0;
		
		//input
		recipeLayout.getItemStacks().init(index, true, 18, 14);
		if (wrapper.getInputs().get(0)!=null) {
			recipeLayout.getItemStacks().set(index, ((ItemStack) wrapper.getInputs().get(0)));
		}
		index++;
		
		//the piston and crusher
		recipeLayout.getItemStacks().init(index, true, 75, 0);
		recipeLayout.getItemStacks().set(index, new ItemStack(Blocks.piston));
		index++;
		recipeLayout.getItemStacks().init(index, true, 75, 43);
		recipeLayout.getItemStacks().set(index, new ItemStack(ContBlocks.crusher));
		index++;
		
		recipeLayout.getItemStacks().init(index, false, 132, 14);
		if (wrapper.getOutputs().get(0)!=null) {
			recipeLayout.getItemStacks().set(index, ((ItemStack) wrapper.getOutputs().get(0)));
		}
		index++;
	}

}
