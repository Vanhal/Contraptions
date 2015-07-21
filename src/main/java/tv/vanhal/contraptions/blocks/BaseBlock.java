package tv.vanhal.contraptions.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import tv.vanhal.contraptions.Contraptions;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BaseBlock extends BlockContainer {
	public String name;
	public int GUIid = -1;
	
	public BaseBlock(String _name) {
		super(Material.iron);
		setBlockName(_name);
		name = _name;
		
		setHardness(1.0f);
		setCreativeTab(Contraptions.ContraptionTab);
		
		GUIid = Contraptions.proxy.registerGui(_name);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (GUIid>=0) {
				FMLNetworkHandler.openGui(player, Contraptions.instance, GUIid, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return null;
	}
	
	public void preInit() {
		GameRegistry.registerBlock(this, name);
		addRecipe();
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public void addRecipe() {
		
	}
}
