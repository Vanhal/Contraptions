package tv.vanhal.contraptions.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.machines.BlockSpike;
import tv.vanhal.contraptions.util.ItemHelper;

public class TilePoweredPiston extends BasePoweredTile {
	public final int POWER_PER_USE = 100;
	
	protected int lastPower = 0;
	protected int cooldown = 0;
	
	public TilePoweredPiston() {
		super(1600);
	}
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("lastPower", lastPower);
		nbt.setInteger("cooldown", cooldown);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("lastPower")) lastPower = nbt.getInteger("lastPower");
		if (nbt.hasKey("cooldown")) cooldown = nbt.getInteger("cooldown");
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (cooldown>0) {
				cooldown--;
				if (cooldown==0) addPartialUpdate("cooldown", cooldown);
			}
		}
	}
	
	public boolean isInCooldown() {
		return (cooldown>0);
	}
	
	
	public void blockUpdated() {
		if (!worldObj.isRemote) {
			int currentPower = this.isPoweredLevelNotFacing();
			if ( (currentPower>0) && (lastPower==0) && (cooldown <= 0) ) {
				lastPower = currentPower;
				if (consumeCharge(POWER_PER_USE)) {
					pushBlocks(currentPower);
					cooldown = 8;
					addPartialUpdate("cooldown", cooldown);
				}
			} else if ( (currentPower==0) && (lastPower>0) ) {
				lastPower = currentPower;
			}
		}
	}
	
	protected void pushBlocks(int range) {
		int firstAir = 0;
		int lastAir = 0;
		boolean foundBlock = false;
		for (int i = 1; i <= range; i++) {
			if (foundBlock) {
				if (isAir(i)) {
					firstAir = i;
					break;
				} else if (!isPushable(i)) {
					break;
				}
			} else {
				if ( (!isAir(i)) && (isPushable(i)) ) foundBlock = true;
				else if ( (!isAir(i)) && (!isPushable(i)) ) break;
				else if (isAir(i, false)) {
					lastAir = i;
				}
			}
		}
		for (int i = firstAir; i>0; i--) {
			moveBlock(i);
		}
		pushDone(lastAir);
	}
	
	protected void pushDone(int lastBlock) {
		
	}
	
	protected boolean isAir(int distance) {
		return isAir(distance, true);
	}
	
	protected boolean isAir(int distance, boolean replace) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		return isAir(x, y, z, replace);
	}
	
	protected boolean isAir(int x, int y, int z) {
		return isAir(x, y, z, true);
	}
	
	protected boolean isAir(int x, int y, int z, boolean replace) {
		Block testBlock = worldObj.getBlock(x, y, z);
		return ( (worldObj.isAirBlock(x, y, z)) 
				|| (FluidRegistry.lookupFluidForBlock(testBlock)!=null)
				|| ( (testBlock.isReplaceable(worldObj, x, y, z)) && (replace) )
				|| ( (testBlock.getMobilityFlag() == 1) && (replace) ) ) ;
	}
	
	protected boolean isPushable(int distance) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		Block moveBlock = worldObj.getBlock(x, y, z);
		return isPushable(moveBlock, x, y, z);
	}
	
	protected boolean isPushable(Block moveBlock, int x, int y, int z) {
		return ( (moveBlock.getMobilityFlag()==0) && (moveBlock != Blocks.obsidian) && (moveBlock.getBlockHardness(worldObj, x, y, z) >= 0) );
	}
	
	protected boolean isSpike(int distance) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		Block testBlock = worldObj.getBlock(x, y, z);
		return (testBlock instanceof BlockSpike);
	}
	
	protected boolean moveBlock(int distance) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		int dx = x + facing.offsetX;
		int dy = y + facing.offsetY;
		int dz = z + facing.offsetZ;
		Block moveBlock = worldObj.getBlock(x, y, z);
		int metaData = worldObj.getBlockMetadata(x, y, z);
		if (!isAir(x, y, z)) {
			if ( isPushable(moveBlock, x, y, z) ) {
				if (isAir(dx, dy, dz)) {
					if (moveBlock instanceof BlockSpike) {
						int bx = dx + facing.offsetX;
						int by = dy + facing.offsetY;
						int bz = dz + facing.offsetZ;
						if (isPushable(moveBlock, bx, by, bz)) {
							Block smashBlock = worldObj.getBlock(bx, by, bz);
							int metaSmashData = worldObj.getBlockMetadata(bx, by, bz);
							ItemHelper.dropBlockIntoWorld(worldObj, bx, by, bz, smashBlock, metaSmashData);
						}
					}
					
					worldObj.setBlock(dx, dy, dz, moveBlock);
					worldObj.setBlockMetadataWithNotify(dx, dy, dz, metaData, 3);
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile!=null) {
						NBTTagCompound tileData = new NBTTagCompound();
						tile.writeToNBT(tileData);
						TileEntity newTile = worldObj.getTileEntity(dx, dy, dz);
						if (newTile!=null) {
							newTile.readFromNBT(tileData);
							worldObj.setTileEntity(dx, dy, dz, newTile);
						}
					}

					worldObj.removeTileEntity(x, y, z);
					worldObj.setBlockToAir(x, y, z);
					//worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(moveBlock) + (metaData << 12));
					
					if (isSpike(distance+2)) {
						ItemHelper.dropBlockIntoWorld(worldObj, dx, dy, dz, moveBlock, metaData);
					}
				}
			}
		} else if (isAir(x, y, z, false)) {
			if ( (moveBlock.getMobilityFlag()==1) || (moveBlock.isReplaceable(worldObj, x, y, z)) ) {
				if (FluidRegistry.lookupFluidForBlock(moveBlock)==null) {
					worldObj.setBlockToAir(x, y, z);
					worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(moveBlock) + (metaData << 12));
				}
			}
		}
		return false;
	}
}
