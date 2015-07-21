package tv.vanhal.contraptions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.core.Proxy;
import tv.vanhal.contraptions.gui.SimpleGuiHandler;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.ref.Ref;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Ref.MODID, name = Ref.MODNAME, version = Ref.Version)
public class Contraptions {
	@Instance(Ref.MODID)
	public static Contraptions instance;
	
	@SidedProxy(clientSide = "tv.vanhal."+Ref.MODID+".core.ClientProxy", serverSide = "tv.vanhal."+Ref.MODID+".core.Proxy")
	public static Proxy proxy;
	
	//logger
	public static final Logger logger = LogManager.getLogger(Ref.MODID);
	
	//gui handler
	public static SimpleGuiHandler guiHandler = new SimpleGuiHandler();
	
	//Creative Tab
	public static CreativeTabs ContraptionTab = new CreativeTabs("ContraptionTab") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.piston);
		}
	};
	
	public Contraptions() {
		logger.info("Making Contraptions");
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ContConfig.init(new Configuration(event.getSuggestedConfigurationFile()));
		
		ContItems.preInit();
		ContBlocks.preInit();
		
		ContConfig.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ContItems.init();
		ContBlocks.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		FMLCommonHandler.instance().bus().register(instance);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ContItems.postInit();
		ContBlocks.postInit();
		
		proxy.registerEntities();
		
		ContConfig.postInit();
	}
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(Ref.MODID))
        	ContConfig.syncConfig();
    }
}
