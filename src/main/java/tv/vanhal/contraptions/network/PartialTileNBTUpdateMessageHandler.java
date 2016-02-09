package tv.vanhal.contraptions.network;

import tv.vanhal.contraptions.tiles.BaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PartialTileNBTUpdateMessageHandler implements IMessageHandler<PartialTileNBTUpdateMessage, IMessage> {

	@Override
	public IMessage onMessage(PartialTileNBTUpdateMessage message, MessageContext ctx) {
		TileEntity entity =  Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(message.x, message.y, message.z);
		
		if (entity != null && entity instanceof BaseTile) {
			BaseTile paEntity = (BaseTile) entity;
			paEntity.readCommonNBT(message.nbtTag);
			paEntity.readSyncOnlyNBT(message.nbtTag);
			if (message.nbtTag.hasKey("Contents"))
				paEntity.readNonSyncableNBT(message.nbtTag);
		}
		return null;
	}
}

