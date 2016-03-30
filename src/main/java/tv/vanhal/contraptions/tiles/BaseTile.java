package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BaseBlock;
import tv.vanhal.contraptions.network.NetworkHandler;
import tv.vanhal.contraptions.network.PartialTileNBTUpdateMessage;
import tv.vanhal.contraptions.util.ForgeDirection;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class BaseTile extends TileEntity implements ITickable {
	protected ItemStack[] slots;
	protected Point3I point = null;
	public EnumFacing facing = EnumFacing.WEST;

	public static final byte TICKS_PER_MESSAGE = 5;
	
	//flags and tags for updating the NBT data
	private boolean dirty = true;
	private boolean blockUpdate = true;
	private boolean contentsUpdate = true;
	private NBTTagCompound partialUpdateTag = new NBTTagCompound();
	
	public BaseTile() {
		this(0);
	}
	
	public BaseTile(int numSlots) {
		slots = new ItemStack[numSlots];
	}
	
	public boolean isActive() {
		return false;
	}
	
	public BaseBlock getBlock() {
		if (getPoint().getBlock(worldObj) instanceof BaseBlock)
			return (BaseBlock) getPoint().getBlock(worldObj);
		return null;
	}
	
	//these methods are here to improve compat between 1.7 and 1.8 (less rewriting stuff
	public int getX() {
		return pos.getX();
	}
	
	public int getY() {
		return pos.getY();
	}
	
	public int getZ() {
		return pos.getZ();
	}
	
	public Point3I getPoint() {
		if (point==null) point = new Point3I(getX(), getY(), getZ());
		else if (!( (getX() == point.getX()) && (getY() == point.getY()) && (getZ() == point.getZ()) ))
			point = new Point3I(getX(), getY(), getZ());
		return point;
	}
	
	/////START NBT DATA METHODS
	/**
	 * Do not extend this method, use writeSyncOnlyNBT, writeCommonNBT or writeNonSyncableNBT as needed.
	 */
	@Override
	public final void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		writeCommonNBT(nbt);
		writeNonSyncableNBT(nbt);
	}
	
	/**
	 * Do not extend this method, use readSyncOnlyNBT, readCommonNBT or readNonSyncableNBT as needed.
	 */
	@Override
	public final void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		readCommonNBT(nbt);
		readNonSyncableNBT(nbt);
	}
	
	public void readFromItemStack(ItemStack itemStack) {
		if (itemStack == null || itemStack.getTagCompound() == null) {
			return;
		}
		readCommonNBT(itemStack.getTagCompound());
		readNonSyncableNBT(itemStack.getTagCompound());
	}

	public void writeToItemStack(ItemStack itemStack) {
		if (itemStack == null ) {
			return;
		}
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		writeCommonNBT(itemStack.getTagCompound());
		writeNonSyncableNBT(itemStack.getTagCompound());
	}
	
	/**
	 * This overridable method is intended for writing to NBT all data that is used only at runtime
	 * and never saved to hdd.
	 * @param nbt
	 */
	protected void writeSyncOnlyNBT(NBTTagCompound nbt) {
		
	}
	
	/**
	 * This overridable method is intended for writing to NBT all data that is both saved to hdd
	 * and can be sent to the client TileEntity through S35PacketUpdateTileEntity.
	 * 
	 * WARNING: Do not include slot contents here. 
	 * They are automagically synced when a GUI is opened using S30PacketWindowItems 
	 * @param nbt
	 */
	public void writeCommonNBT(NBTTagCompound nbt) {
		nbt.setInteger("facing", facing.ordinal());
	}
	
	/**
	 * This overridable method is intended for writing to NBT all data that will 
	 * NOT be synced using S35PacketUpdateTileEntity packets.
	 * 
	 * This includes, but is not limited to, slot contents. 
	 * See writeSyncableNBT for more info.
	 * @param nbt
	 */
	public void writeNonSyncableNBT(NBTTagCompound nbt) {
		NBTTagList contents = new NBTTagList();
		for (int i = 0; i < slots.length; i++) {
			
			ItemStack stack = slots[i];
			NBTTagCompound tag = new NBTTagCompound();
			tag.setByte("Slot", (byte)i);
			if (slots[i] != null) {
				tag.setBoolean("isNull", false);
				stack.writeToNBT(tag);
			} else {
				tag.setBoolean("isNull", true);
			}
			contents.appendTag(tag);
		}
		nbt.setTag("Contents", contents);
	}
	
	/**
	 * This overridable method is intended for reading from NBT all data that is used only at runtime
	 * and never saved to hdd.
	 * @param nbt
	 */
	public void readSyncOnlyNBT(NBTTagCompound nbt) {
	}
	
	/**
	 * This overridable method is intended for reading from NBT all data that is both saved to hdd
	 * and can be sent to the client TileEntity through S35PacketUpdateTileEntity and PartialTileNBTUpdateMessage.
	 * 
	 * WARNING: Please check if the tag exists before reading it. Due to the nature of 
	 * PartialTileNBTUpdateMessage properties that didn't change since the last message
	 * will not be included.
	 * 
	 * WARNING: Do not include slot contents here. 
	 * They are automagically synced when a GUI is opened using S30PacketWindowItems 
	 * @param nbt
	 */
	public void readCommonNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("facing")) facing = EnumFacing.values()[nbt.getInteger("facing")];
		else if (facing == null) facing = EnumFacing.WEST;
	}
	
	/**
	 * This overridable method is intended for reading from NBT all data that is only saved to hdd 
	 * and never synced between client and server, or is synced using a different method (e.g. inventory 
	 * contents and S30PacketWindowItems)
	 * @param nbt
	 */
	public void readNonSyncableNBT(NBTTagCompound nbt) {
		NBTTagList contents = nbt.getTagList("Contents", 10);
		for (int i = 0; i < contents.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) contents.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			boolean isNull = tag.getBoolean("isNull");
			if ( (slot < slots.length) && (!isNull) ) {
				slots[slot] = ItemStack.loadItemStackFromNBT(tag);
			} else if (isNull) {
				slots[slot] = null;
			}
		}
	}
	
    /**
     * This method is used to sync data when a GUI is opened. the packet will contain
     * all syncable data.
     */
	@Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeCommonNBT(nbttagcompound);
        this.writeSyncOnlyNBT(nbttagcompound);
        
        return new S35PacketUpdateTileEntity(pos, -1, nbttagcompound);
    }
	
	/**
	 * This method is used to load syncable data when a GUI is opened.
	 */
	@Override    
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readCommonNBT(pkt.getNbtCompound());
    	this.readSyncOnlyNBT(pkt.getNbtCompound());
 
    	worldObj.markBlockForUpdate(pos);
    }
	
	/**
	 * This method will generate a message with partial updates for this TileEntity.
	 * 
	 * WARNING: Using this method resets the dirty flag and clears all pending updates up to this point.
	 * @return
	 */
	public PartialTileNBTUpdateMessage getPartialUpdateMessage() {
		if ( (shouldUpdateContents()) && (!worldObj.isRemote) ) {
			writeNonSyncableNBT(partialUpdateTag);
		}
		PartialTileNBTUpdateMessage message = new PartialTileNBTUpdateMessage(this.getX(), this.getY(), this.getZ(), partialUpdateTag);
		dirty = false;
		contentsUpdate = false;
		partialUpdateTag = new NBTTagCompound();
		
		return message;
	}
	
	/**
	 * Utility method, so you don't have to remember to set dirty to true
	 */
	protected void addPartialUpdate(String fieldName, Integer value) {
		addPartialUpdate(fieldName, value, false);
	}
	
	protected void addPartialUpdate(String fieldName, Integer value, boolean updateBlock) {
		partialUpdateTag.setInteger(fieldName, value);
		dirty = true;
		blockUpdate = updateBlock;
	}
	
	/**
	 * Utility method, so you don't have to remember to set dirty to true
	 */
	protected void addPartialUpdate(String fieldName, ItemStack value) {
		addPartialUpdate(fieldName, value, false);
	}
	
	protected void addPartialUpdate(String fieldName, ItemStack value, boolean updateBlock) {
		NBTTagCompound tag = new NBTTagCompound();
		if (value!=null) {
			value.writeToNBT(tag);
		}
		partialUpdateTag.setTag("fieldName", tag);
		dirty = true;
		blockUpdate = updateBlock;
	}
	/**
	 * Utility method, so you don't have to remember to set dirty to true
	 */	
	protected void addPartialUpdate(String fieldName, String value) {
		addPartialUpdate(fieldName, value, false);
	}
	protected void addPartialUpdate(String fieldName, String value, boolean updateBlock) {
		partialUpdateTag.setString(fieldName, value);
		dirty = true;
		blockUpdate = updateBlock;
	}
	/**
	 * Utility method, so you don't have to remember to set dirty to true
	 */
	protected void addPartialUpdate(String fieldName, NBTBase value) {
		addPartialUpdate(fieldName, value, false);
	}
	protected void addPartialUpdate(String fieldName, NBTBase value, boolean updateBlock) {
		partialUpdateTag.setTag(fieldName, value);
		dirty = true;
		blockUpdate = updateBlock;
	}
	/**
	 * Utility method, so you don't have to remember to set dirty to true
	 */	
	protected void addPartialUpdate(String fieldName, Boolean value) {
		addPartialUpdate(fieldName, value, false);
	}
	protected void addPartialUpdate(String fieldName, Boolean value, boolean updateBlock) {
		partialUpdateTag.setBoolean(fieldName, value);
		dirty = true;
		blockUpdate = updateBlock;
	}

	protected void addPartialUpdate(String fieldName, NBTTagCompound value) {
		addPartialUpdate(fieldName, value, false);
	}
	protected void addPartialUpdate(String fieldName, NBTTagCompound value, boolean updateBlock) {
		partialUpdateTag.setTag(fieldName, value);
		dirty = true;
		blockUpdate = updateBlock;
	}

	protected NBTTagCompound getCompoundTagFromPartialUpdate(String fieldName) {
		return partialUpdateTag.getCompoundTag(fieldName);
	}
	
	/**
	 * Whether the TileEntity needs syncing.
	 * @return
	 */
	public boolean isDirty() {
		return dirty;
	}
	
	public boolean shouldUpdateBlock() {
		return blockUpdate;
	}
	
	public boolean shouldUpdateContents() {
		return contentsUpdate;
	}
	
	public void setContentsUpdate() {
		if (!worldObj.isRemote) {
			contentsUpdate = true;
			dirty = true;
		}
	}
	
	protected void notifyUpdate() {
		worldObj.markBlockForUpdate(pos);
	}
	
	/////END NBT DATA METHODS
	@Override
	public void update() {
		doUpdate();
		if ( (isDirty()) && ((worldObj.getWorldTime() % TICKS_PER_MESSAGE) == 0)) {
			PartialTileNBTUpdateMessage message = getPartialUpdateMessage();
			
			NetworkHandler.sendToAllAroundNearby(message, this);
			if (shouldUpdateBlock()) {
				notifyUpdate();
				blockUpdate = false;
			}
		}
	}
	
	public void doUpdate() {
		
	}
	
	public boolean isPowered() {
		return (isPoweredLevel()>0);
	}
	
	public boolean isPoweredNotFacing() {
		return (isPoweredLevelNotFacing()>0);
	}
	
	public int isPoweredLevel() {
		int highestPower = 0;
		for (EnumFacing test : EnumFacing.VALUES) {
			int testVal = worldObj.getRedstonePower(pos.offset(test), test);
			if (testVal >= 15) 
				return 15;
			if (testVal > highestPower)
				highestPower = testVal;
		}
		return highestPower;
	}
	
	public int isPoweredLevelNotFacing() {
		int highestPower = 0;
		for (EnumFacing test : EnumFacing.VALUES) {
			if (test != facing) {
				int testVal = worldObj.getRedstonePower(pos.offset(test), test);
				if (testVal >= 15) 
	            	return 15;
				if (testVal > highestPower)
	            	highestPower = testVal;
			}
		}
		return highestPower;
	}
	
	public void setFacing(int _facing) {
		facing = EnumFacing.values()[_facing];
	}
	
	public int getFacing() {
		if (facing == null) return -1; 
		return facing.ordinal();
	}
	
	
}
