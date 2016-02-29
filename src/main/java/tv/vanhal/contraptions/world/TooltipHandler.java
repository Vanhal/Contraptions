package tv.vanhal.contraptions.world;

import tv.vanhal.contraptions.util.StringHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipHandler {
	
	@SubscribeEvent
	public void ItemTooltipEvent(ItemTooltipEvent event) {
		if (event.itemStack.hasTagCompound()) {
			if (event.itemStack.getTagCompound().hasKey("ContCrushTimes")) {
				event.toolTip.add(EnumChatFormatting.GRAY+StringHelper.localize("gui.smashedup"));
			}
		}
	}
}
