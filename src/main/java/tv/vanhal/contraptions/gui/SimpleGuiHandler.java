package tv.vanhal.contraptions.gui;

import java.lang.reflect.Constructor;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SimpleGuiHandler  implements IGuiHandler {
	private int guiIdCounter = 1;
	public static int manualGUI = 0;
	
	private final TMap containerMap = new THashMap();
	private final TMap guiMap = new THashMap();
	
	public int registerGui(Class gui, Class container) {
		guiIdCounter++;
		guiMap.put(guiIdCounter, gui);
		containerMap.put(guiIdCounter, container);
		return guiIdCounter;
	}
	

	public int registerServerGui(Class container) {
		guiIdCounter++;
		containerMap.put(guiIdCounter, container);
		return guiIdCounter;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (containerMap.containsKey(ID)) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			TileEntity tile = world.getTileEntity(x, y, z);
			try {
				if (!world.isRemote) {
					Packet packet = tile.getDescriptionPacket();
					if (packet != null) {
						((EntityPlayerMP)player).playerNetServerHandler.sendPacket(packet);
					}
					
				}
				Class<? extends Container> containerClass = (Class<? extends Container>) containerMap.get(ID);
				Constructor containerConstructor = containerClass.getDeclaredConstructor(new Class[] { InventoryPlayer.class, TileEntity.class });
				return containerConstructor.newInstance(player.inventory, tile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (guiMap.containsKey(ID)) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			TileEntity tile = world.getTileEntity(x, y, z);
			try {
				Class<? extends GuiScreen> guiClass = (Class<? extends GuiScreen>) guiMap.get(ID);
				Constructor guiConstructor = guiClass.getDeclaredConstructor(new Class[] { InventoryPlayer.class, TileEntity.class });
				return guiConstructor.newInstance(player.inventory, tile);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else if (ID==manualGUI) {
			return null;
		}

		return null;
	}


}
