package tv.vanhal.contraptions.world;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.world.heat.HeatRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public class WorldTicker {
	 //Called when the world ticks
	 @SubscribeEvent
	 public void onWorldTick(TickEvent.WorldTickEvent event) {
		 if ( (event.side == Side.SERVER) && (event.phase == Phase.END) ) {
			 HeatRegistry.getInstance(event.world).tick(event.world);
		 }
	 }
}
