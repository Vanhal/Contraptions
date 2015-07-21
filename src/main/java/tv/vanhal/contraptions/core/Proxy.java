package tv.vanhal.contraptions.core;

import tv.vanhal.contraptions.Contraptions;

public class Proxy {

	public void registerEntities() {
		
	}
	
	public int registerGui(String guiName) {
		return registerGui(guiName, guiName);
	}
	
	public int registerGui(String guiName, String containerName) {
		Class<?> gui = null;
		Class<?> container = null;
		try {
			gui = Proxy.class.getClassLoader().loadClass("tv.vanhal.contraptions.gui.client.GUI" + guiName);
		} catch (ClassNotFoundException e) {
			
		}
		try {
			container = Proxy.class.getClassLoader().loadClass("tv.vanhal.contraptions.gui.container.Container" + containerName);
		} catch (ClassNotFoundException e) {
			return -1;
		}
		if (gui == null) {
			return Contraptions.guiHandler.registerServerGui(container);
		} else {
			return Contraptions.guiHandler.registerGui(gui, container);
		}
		
	}
}
