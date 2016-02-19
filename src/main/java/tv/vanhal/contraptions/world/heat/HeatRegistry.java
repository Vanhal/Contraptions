package tv.vanhal.contraptions.world.heat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.ContBlocks;
import tv.vanhal.contraptions.crafting.RecipeManager;
import tv.vanhal.contraptions.fluids.ContFluids;
import tv.vanhal.contraptions.interfaces.IHeatBlock;
import tv.vanhal.contraptions.interfaces.IHeatBlockHandler;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Point3I;
import tv.vanhal.contraptions.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
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

	private AxisAlignedBB boundCheck = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	
	private TMap<Point3I, Integer> heatValues = new THashMap<Point3I, Integer>();
	private ArrayList<Point3I> toRemove = new ArrayList<Point3I>();
	private ArrayList<Point3I> toAdd = new ArrayList<Point3I>();
	private ArrayList<Point3I> toBurn = new ArrayList<Point3I>();
	
	public HeatRegistry(String _name) {
		super(_name);
		registerValidBlocks();
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
		if ( (heatValues.containsKey(point)) && (!toRemove.contains(point)) ) {
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
	
	public boolean isHeatableBlock(World world, Point3I point) {
		if (isHeatBlock(point)) return true;
		if ( (point.getBlock(world) instanceof IHeatBlock) ||
				(HeatHandlers.isValidBlock(world, point))  ) return true;
		return false;
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
				if (point.blockExists(world)) {
					if ( !(point.getBlock(world) instanceof IHeatBlock) &&
							(!HeatHandlers.isValidBlock(world, point))  ) {
						removeHeatBlock(point);
					} else {
						//do passive cooling
						int heat = heatValues.get(point);
						heat -= ContConfig.HEAT_LOSS_PER_TICK;

						//spread heat, equalize between all touching blocks
						if (heat>0) {
							int touchingBlocks = 1;
							int totalHeat = heat;
							for (int i = 0; i < 6; i++) {
								Point3I testBlock = point.getAdjacentBlock(i);
								if (isHeatBlock(testBlock)) {
									touchingBlocks++;
									totalHeat += getValue(testBlock);
								} else if (HeatHandlers.isValidBlock(world, testBlock)) {
									if (!toAdd.contains(testBlock))
										toAdd.add(testBlock);
								}
							}
							//check to see if there is any items on top
							boundCheck.setBounds(point.getX(), point.getY() + 1, point.getZ(), 
									point.getX()+1, point.getY()+2, point.getZ()+1);
							List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, boundCheck);
							for (EntityItem item : items) {
								if (item.getEntityData() != null) {
									if (item.getEntityData().hasKey("ContHeatValue")) {
										touchingBlocks++;
										totalHeat += item.getEntityData().getInteger("ContHeatValue");
									} else {
										item.getEntityData().setInteger("ContHeatValue", 0);
										touchingBlocks++;
									}
								}
							}
							
							//spread the heat
							if (touchingBlocks>1) {
								heat = (int) Math.floor(totalHeat/(double)touchingBlocks);
								for (int i = 0; i < 6; i++) {
									Point3I testBlock = point.getAdjacentBlock(i);
									if (isHeatBlock(testBlock)) {
										setValue(testBlock, heat);
									}
								}
								for (EntityItem item : items) {
									if ( (item.getEntityData() != null) && (item.getEntityData().hasKey("ContHeatValue")) ) {
										item.getEntityData().setInteger("ContHeatValue", heat);
										processItemHeat(world, point, item, heat);
									}
								}
							}
						} else if (heat <= 0) {
							heat = 0;
							if (HeatHandlers.isValidBlock(world, point)) {
								removeHeatBlock(point);
							}
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
					if (point.blockExists(world)) {
						if (point.getBlock(world) instanceof IHeatBlock) {
							//check to see if this block should melt
							IHeatBlock block = (IHeatBlock) point.getBlock(world);
							if (heat > block.getMeltingPoint()) {
								//melt block!
							}
							
							//update this blocks heat
							if (heat != heatValues.get(point)) {
								setValue(point, heat);
							}
						} else if (HeatHandlers.isValidBlock(world, point)) {
							if (HeatHandlers.canBlockProcess(world, point, heat)) {
								if (toBurn.contains(point)) {
									if (HeatHandlers.processBlockHeat(world, point, heat))
										toBurn.remove(point);
								} else {
									toBurn.add(point);
								}
							} else {
								toBurn.remove(point);
							}
						}
					}
				}
			}
			
			
			tickCounter = 0;
		}
		deleteRemoved(world);
		addNew();
		tickCounter++;
	}
	
	protected void deleteRemoved(World world) {
		if (toRemove.size()>0) {
			for (Point3I point : toRemove) {
				if (heatValues.containsKey(point)) {
					if ( !(point.getBlock(world) instanceof IHeatBlock) &&
						(!HeatHandlers.isValidBlock(world, point)) ) {
						heatValues.remove(point);
					}
				}
			}
			toRemove.clear();
			markDirty();
		}
	}
	
	protected void addNew() {
		if (toAdd.size()>0) {
			for (Point3I point : toAdd) {
				if (!isHeatBlock(point)) {
					addHeatBlock(point);
				}
			}
			toAdd.clear();
			markDirty();
		}
	}
	
	protected void processItemHeat(World world, Point3I point, EntityItem item, int currentHeat) {
		if (currentHeat>0) {
			int recipeHeat = RecipeManager.getHeatRequired(item.getEntityItem()) * item.getEntityItem().stackSize;
			if ( (recipeHeat>0) && (currentHeat>=recipeHeat) && (item.getEntityItem().stackSize>0) ) {
				ItemStack output = RecipeManager.getHeatOutput(item.getEntityItem());
				output.stackSize = item.getEntityItem().stackSize;
				world.removeEntity(item);
				ItemHelper.dropAsItem(world, point.getX(), point.getY() + 1, point.getZ(), output);
			} else if ( (currentHeat>=500) && (recipeHeat==0) ) {
				item.setFire(10);
			}
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
	}
	
	private void registerValidBlocks() {
		HeatHandlers.registerHandler(Blocks.water, new HandlerWater());
		HeatHandlers.registerHandler(Blocks.flowing_water, new HandlerWater());
		HeatHandlers.registerHandler(Blocks.furnace, new HandlerFurnace());
		HeatHandlers.registerHandler(Blocks.lit_furnace, new HandlerFurnace());
	}


}
