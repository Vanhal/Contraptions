package tv.vanhal.contraptions.tiles;

import java.util.ArrayList;

import cofh.api.energy.IEnergyReceiver;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePylon extends BasePoweredTile {
	private final int TICKS_PER_REFRESH = 40;
	private final int MIN_RANGE = 1;
	private final int MAX_RANGE = 16;
	private final int COST_TO_TRANSMIT = 5;
	private final int MAX_PACKET_SIZE = 50;
	protected int currentIndex = 0;
	public int range = 4;
	protected int tickCount = 0;
	protected ArrayList<Point3I> connectedMachines = new ArrayList<Point3I>();
	protected ArrayList<Point3I> toRemove = new ArrayList<Point3I>();

	public TilePylon() {
		super(10000);
		canExtract = true;
	}
	
	//main update
	@Override
	public void update() {
		tickCount++;
		if (!worldObj.isRemote) {
			if (tickCount >= TICKS_PER_REFRESH)
				refreshConnectedMachines();
			
			//send power if we have any
			if ( (energyStorage > getTransmitCost()) && (connectedMachines.size()>0) ) {
				currentIndex++;
				if (currentIndex>=connectedMachines.size()) currentIndex = 0;
				if (connectedMachines.get(currentIndex)!=null) {
					Point3I point = connectedMachines.get(currentIndex);
					TileEntity tile = point.getTileEntity(worldObj);
					if (tile instanceof IEnergyReceiver) {
						IEnergyReceiver energyTile = (IEnergyReceiver) tile;
						for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
							if (energyTile.canConnectEnergy(dir)) {
								int consumedAmount = energyTile.receiveEnergy(dir, getMaxPacketSize(), true);
								if ( (consumedAmount>0) && (consumeCharge(consumedAmount + getTransmitCost())) ) {
									energyTile.receiveEnergy(dir, consumedAmount, false);
								}
								break;
							}
						}
					}
				}
			}
			
			removeMachines();
		}
		if (tickCount >= TICKS_PER_REFRESH) tickCount = 0;
	}
	
	//get the cost to send one energy packet
	public int getTransmitCost() {
		return (range-1) * COST_TO_TRANSMIT;
	}
	
	public int getMaxPacketSize() {
		return Math.min((energyStorage - getTransmitCost()), MAX_PACKET_SIZE);
	}
	
	//Update the list of connected machines
	protected void refreshConnectedMachines() {
		connectedMachines.clear();
		for (int x = range * -1; x <= range; x++) {
			for (int y = range * -1; y <= range; y++) {
				for (int z = range * -1; z <= range; z++) {
					if (!( (x==0) && (y==0) && (z==0) )) {
						Point3I testPoint = new Point3I(getX() + x, getY() + y, getZ() + z);
						if (testPoint.getTileEntity(worldObj) instanceof IEnergyReceiver) {
							IEnergyReceiver reciever = (IEnergyReceiver) testPoint.getTileEntity(worldObj);
							
							//try all the directions to see if we can connect
							for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
								if ( (reciever.canConnectEnergy(dir)) && (reciever.getMaxEnergyStored(dir)>0) ) {
									if (!connectedMachines.contains(testPoint))
										connectedMachines.add(testPoint);
									break;
								}
							}
						} else if (connectedMachines.contains(testPoint)) {
							toRemove.add(testPoint);
						}
					}
				}
			}
		}
	}
	
	//remove any machines that have been marked for removal
	protected void removeMachines() {
		for (Point3I point : toRemove) {
			if (connectedMachines.contains(point))
				connectedMachines.remove(point);
		}
		if (toRemove.size()>0) markDirty();
		toRemove.clear();
	}

	public void increaseRange() {
		range++;
		if (range>MAX_RANGE) range = MIN_RANGE;
		addPartialUpdate("range", range);
		refreshConnectedMachines();
	}
	
	public void decreaseRange() {
		range--;
		if (range<MIN_RANGE) range = MAX_RANGE;
		addPartialUpdate("range", range);
		refreshConnectedMachines();
	}
	
	//nbt stuff
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("range", range);
		nbt.setInteger("tickCount", tickCount);
		nbt.setInteger("currentIndex", currentIndex);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("range")) range = nbt.getInteger("range");
		if (nbt.hasKey("currentIndex")) currentIndex = nbt.getInteger("currentIndex");
		if (nbt.hasKey("tickCount")) tickCount = nbt.getInteger("tickCount");
	}
	
	@Override
	public void writeNonSyncableNBT(NBTTagCompound nbt) {
		super.writeNonSyncableNBT(nbt);
		NBTTagList contents = new NBTTagList();
		for (Point3I point: connectedMachines) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag("point", point.getNBT());
			contents.appendTag(tag);
		}
		nbt.setTag("connectedMachines", contents);
	}
	
	@Override
	public void readNonSyncableNBT(NBTTagCompound nbt) {
		super.readNonSyncableNBT(nbt);
		if (nbt.hasKey("connectedMachines")) {
			connectedMachines.clear();
			NBTTagList contents = nbt.getTagList("connectedMachines", 10);
			for (int i = 0; i < contents.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) contents.getCompoundTagAt(i);
				if (tag.hasKey("point")) connectedMachines.add(new Point3I(tag.getCompoundTag("point")));
			}
		}
	}
}
