package tv.vanhal.contraptions.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.fluids.ContFluids;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

//WorldSavedData
public class HeatRegistry extends WorldSavedData {
	//static stuff
	public static String KEY = Ref.MODID+":HeatData";
	public static TMap<Integer, HeatRegistry> instances = new THashMap<Integer, HeatRegistry>();
	
	public static HeatRegistry getInstance(World world) {
		if (instances.containsKey(world.provider.dimensionId)) {
			return instances.get(world.provider.dimensionId);
		} else {
			MapStorage storage = world.perWorldStorage;
			if (storage!=null) {
				HeatRegistry heatReg = (HeatRegistry) storage.loadData(HeatRegistry.class, KEY);
		
				if(heatReg == null) {
					heatReg = new HeatRegistry(KEY);
					heatReg.setDemension(world.provider.dimensionId);
					storage.setData(KEY, heatReg);
				}
				
				HeatRegistry instance = (HeatRegistry) storage.loadData(HeatRegistry.class, KEY);
				instances.put(world.provider.dimensionId, instance);
				return instance;
			} else {
				return null;
			}
		}
	}
	
	//normal class
	
	public int dimensionID = 0;
	public int tickCounter = 0;
	private TMap<Point3I, Integer> heatValues = new THashMap<Point3I, Integer>();
	private ArrayList<Point3I> toRemove = new ArrayList<Point3I>();
	
	public HeatRegistry(String _name) {
		super(_name);
	}

	public void setDemension(int dimID) {
		dimensionID = dimID;
		markDirty();
	}
	
	public void addHeatBlock(int x, int y, int z) {
		addHeatBlock(new Point3I(x, y, z));
	}
	
	public void addHeatBlock(Point3I point) {
		setValue(point, 0);
	}
	
	public void removeHeatBlock(int x, int y, int z) {
		removeHeatBlock(new Point3I(x, y, z));
	}
	
	public void removeHeatBlock(Point3I point) {
		if (heatValues.containsKey(point)) {
			toRemove.add(point);
		}
	}
	
	public int removeHeat(int x, int y, int z, int amount) {
		return removeHeat(new Point3I(x, y, z), amount);
	}
	
	public int removeHeat(Point3I point, int amount) {
		int newValue = getValue(point) - amount;
		setValue(point, newValue);
		return newValue;
	}
	
	public int addHeat(int x, int y, int z, int amount) {
		return addHeat(new Point3I(x, y, z), amount);
	}
	
	public int addHeat(Point3I point, int amount) {
		int newValue = getValue(point) + amount;
		setValue(point, newValue);
		return newValue;
	}
	
	public void setValue(int x, int y, int z, int amount) {
		setValue(new Point3I(x, y, z), amount);
	}
	
	public void setValue(Point3I point, int value) {
		heatValues.put(point, value);
		markDirty();
	}
	
	public int getValue(int x, int y, int z) {
		return getValue(new Point3I(x, y, z));
	}
	
	public int getValue(Point3I point) {
		if (heatValues.containsKey(point)) {
			return heatValues.get(point);
		}
		return 0;
	}
	
	public boolean isHeatBlock(int x, int y, int z) {
		return isHeatBlock(new Point3I(x, y, z));
	}
	
	public boolean isHeatBlock(Point3I point) {
		return heatValues.containsKey(point);
	}
	
	public void tick(World world) {
		//do a tick!
		if (tickCounter > ContConfig.TICKS_PER_HEAT_TICK) {
			for (Point3I point : heatValues.keySet()) {
				if (world.blockExists(point.getX(), point.getY(), point.getZ())) {
					if (!(world.getBlock(point.getX(), point.getY(), point.getZ()) instanceof IHeatBlock)) {
						Contraptions.logger.info("Removing: "+point.toString());
						removeHeatBlock(point);
					} else {
						//do passive cooling
						int heat = heatValues.get(point);
						heat -= ContConfig.HEAT_LOSS_PER_TICK;

						//spread heat, equalize between all touching blocks if this block is hotter than the others
						if (heat>0) {
							int touchingBlocks = 1;
							int totalHeat = heat;
							for (int i = 0; i < 6; i++) {
								Point3I testBlock = point.getAdjacentBlock(i);
								if (isHeatBlock(testBlock)) {
									touchingBlocks++;
									totalHeat += getValue(testBlock);
								}
							}
							if (touchingBlocks>1) {
								heat = (int) Math.floor(totalHeat/(double)touchingBlocks);
								for (int i = 0; i < 6; i++) {
									Point3I testBlock = point.getAdjacentBlock(i);
									if (isHeatBlock(testBlock)) {
										setValue(testBlock, heat);
									}
								}
							}
						} else if (heat <= 0) {
							heat = 0;
						}
						
						//update this blocks heat
						if (heat != heatValues.get(point)) {
							setValue(point, heat);
						}
					}
				}
				
			}
			
			//now do stuff like heating water and melting blocks
			for (Point3I point : heatValues.keySet()) {
				int heat = heatValues.get(point);
				if (heat > 0) {
					if (world.blockExists(point.getX(), point.getY(), point.getZ())) {
						if (world.getBlock(point.getX(), point.getY(), point.getZ()) instanceof IHeatBlock) {
							//check if there is water above this block
							if (heat >= ContConfig.WATER_BOIL_HEAT) {
								if (world.getBlock(point.getX(), point.getY() + 1, point.getZ()) == Blocks.water) {
									world.setBlock(point.getX(), point.getY() + 1, point.getZ(), ContFluids.steam);
									heat -= ContConfig.WATER_BOIL_HEAT/2;
								}
							}
							
							//check to see if this block should melt
							IHeatBlock block = (IHeatBlock)world.getBlock(point.getX(), point.getY(), point.getZ());
							if (heat > block.getMeltingPoint()) {
								//melt block!
								Contraptions.logger.info("Block Melted! "+heat+": "+point.toString());
							}
							
							//update this blocks heat
							if (heat != heatValues.get(point)) {
								setValue(point, heat);
							}
						}
					}
				}
			}
			
			
			tickCounter = 0;
		}
		deleteRemoved();
		tickCounter++;
	}
	
	protected void deleteRemoved() {
		if (toRemove.size()>0) {
			for (Point3I point : toRemove) {
				if (heatValues.containsKey(point)) {
					heatValues.remove(point);
				}
			}
			toRemove.clear();
			markDirty();
		}
	}
	
	//nbt stuff
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("tickCounter")) tickCounter = nbt.getInteger("tickCounter");
		if (nbt.hasKey("dimID")) dimensionID = nbt.getInteger("dimID");
		if (nbt.hasKey("heatValues")) {
			heatValues.clear();
			NBTTagList contents = nbt.getTagList("heatValues", 10);
			for (int i = 0; i < contents.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) contents.getCompoundTagAt(i);
				int value = tag.getInteger("value");
				Point3I point = new Point3I();
				point.setNBT(tag.getCompoundTag("coord"));
				heatValues.put(point, value);
			}
		}
		Contraptions.logger.info("Heat Data Loaded for "+dimensionID);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("dimID", dimensionID);
		nbt.setInteger("tickCounter", tickCounter);
		NBTTagList contents = new NBTTagList();
		for (Point3I point : heatValues.keySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag("coord", point.getNBT());
			tag.setInteger("value", heatValues.get(point));
			contents.appendTag(tag);
		}
		nbt.setTag("heatValues", contents);
		Contraptions.logger.info("Heat Data Saved for "+dimensionID);
	}
	


}
