package tv.vanhal.contraptions.world;

import tv.vanhal.contraptions.Contraptions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;

public class WorldTicker {
	 //Called when the world ticks
	 @SubscribeEvent
	 public void onWorldTick(TickEvent.WorldTickEvent event) {
		 if ( (event.side == Side.SERVER) && (event.phase == Phase.END) ) {
			 HeatRegistry.getInstance(event.world).tick(event.world);
		 }
	 }
}
