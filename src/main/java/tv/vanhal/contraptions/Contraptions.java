package tv.vanhal.contraptions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.compat.ModHelper;
import tv.vanhal.contraptions.core.Proxy;
import tv.vanhal.contraptions.crafting.Recipes;
import tv.vanhal.contraptions.fluids.ContFluids;
import tv.vanhal.contraptions.gui.SimpleGuiHandler;
import tv.vanhal.contraptions.items.ContItems;
import tv.vanhal.contraptions.network.NetworkHandler;
import tv.vanhal.contraptions.network.PartialTileNBTUpdateMessage;
import tv.vanhal.contraptions.network.PartialTileNBTUpdateMessageHandler;
import tv.vanhal.contraptions.util.Ref;
import tv.vanhal.contraptions.world.TooltipHandler;
import tv.vanhal.contraptions.world.WorldTicker;

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
			return ContItems.screwDriver;
		}
	};
	
	public Contraptions() {
		logger.info("Making Contraptions");
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		NetworkHandler.registerMessageHandler(PartialTileNBTUpdateMessageHandler.class,
				PartialTileNBTUpdateMessage.class, Side.CLIENT);
		
		ContConfig.init(new Configuration(event.getSuggestedConfigurationFile()));
		
		ModHelper.init();
		
		ContFluids.preInit();
		
		ContItems.preInit();
		ContBlocks.preInit();
		
		ContConfig.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ContItems.init();
		ContBlocks.init();
		
		/*if(Loader.isModLoaded("MineTweaker3"))
			MineTweaker.init();*/
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(new WorldTicker());
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ContItems.postInit();
		ContBlocks.postInit();
		ContFluids.postInit();
		
		proxy.registerEntities();
		proxy.registerItems();
		
		Recipes.register();
		
		ContConfig.postInit();
	}
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(Ref.MODID))
        	ContConfig.syncConfig();
    }
}
