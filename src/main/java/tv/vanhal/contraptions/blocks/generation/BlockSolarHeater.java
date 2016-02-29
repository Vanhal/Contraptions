package tv.vanhal.contraptions.blocks.generation;

import java.util.Arrays;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import com.google.common.collect.Lists;

import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.blocks.BaseCustomBlock;
import tv.vanhal.contraptions.items.BaseItem;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TileSolarHeater;
import tv.vanhal.contraptions.util.BlockHelper.Axis;

public class BlockSolarHeater extends BaseCustomBlock {

	public BlockSolarHeater() {
		super("solarHeater");
	}
	
	@Override
	public Axis getRotationType() {
		return Axis.FourWay;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileSolarHeater();
	}
	
	@Override
	public void addRecipe() {
		ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(this), new Object[]{
				" l ", "s s", "ppp", 'l', ContItems.glassLens, 's', Items.stick, 'p', Blocks.planks});
		GameRegistry.addRecipe(recipe);
	}

}
