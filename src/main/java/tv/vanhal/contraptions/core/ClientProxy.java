package tv.vanhal.contraptions.core;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import tv.vanhal.contraptions.client.renderers.tiles.RenderCrusher;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.tiles.TilePlacer;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.world.RenderOverlay;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends Proxy {
	
	@Override
	public void registerItems() {
		//MinecraftForgeClient.registerItemRenderer(ContItems.screwDriver, new RenderScrewDriver());
	}
	
	@Override
	public void registerEntities() {
		/*ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TilePoweredPiston.class, new RenderPoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileRedstonePoweredPiston.class, new RenderRedstonePoweredPiston());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileSpreader.class, new RenderSpreader());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileGenerator.class, new RenderGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileShaftExtender.class, new RenderShaftExtender());
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileTurbine.class, new RenderTurbine());*/
		ClientRegistry.bindTileEntitySpecialRenderer(tv.vanhal.contraptions.tiles.TileCrusher.class, new RenderCrusher());
		super.registerEntities();
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
	
	@Override
	public boolean isServer() {
		return false;
	}
	
	@Override
	public void preInit() {
		super.preInit();
		OBJLoader.instance.addDomain(Ref.MODID.toLowerCase());
		B3DLoader.instance.addDomain(Ref.MODID.toLowerCase());
	}
	
	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new RenderOverlay());
	}

}
