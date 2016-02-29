package tv.vanhal.contraptions.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.machines.BlockSpike;
import tv.vanhal.contraptions.util.ItemHelper;
import tv.vanhal.contraptions.util.Point3I;

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
	public void doUpdate() {
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
		return isAir(getPoint().offset(facing, distance), replace);
	}
	
	protected boolean isAir(Point3I testPoint) {
		return isAir(testPoint, true);
	}
	
	protected boolean isAir(Point3I testPoint, boolean replace) {
		Block testBlock = testPoint.getBlock(worldObj);
		return ( (worldObj.isAirBlock(testPoint.getPos())) 
				|| (FluidRegistry.lookupFluidForBlock(testBlock)!=null)
				|| ( (testBlock.isReplaceable(worldObj, testPoint.getPos())) && (replace) )
				|| ( (testBlock.getMobilityFlag() == 1) && (replace) ) ) ;
	}
	
	protected boolean isPushable(int distance) {
		Point3I testPoint = getPoint().offset(facing, distance);
		Block moveBlock = testPoint.getBlock(worldObj);
		return isPushable(moveBlock, testPoint);
	}
	
	protected boolean isPushable(Block moveBlock, Point3I testPoint) {
		return ( (moveBlock.getMobilityFlag()==0) && (moveBlock != Blocks.obsidian) && (moveBlock.getBlockHardness(worldObj, testPoint.getPos()) >= 0) );
	}
	
	protected boolean isSpike(int distance) {
		Block testBlock = getPoint().offset(facing, distance).getBlock(worldObj);
		return (testBlock instanceof BlockSpike);
	}
	
	protected boolean moveBlock(int distance) {
		Point3I point = getPoint().offset(facing, distance);
		Point3I dPoint = point.offset(facing);
		Block moveBlock = point.getBlock(worldObj);
		IBlockState moveState = point.getState(worldObj);
		if (!isAir(point)) {
			if ( isPushable(moveBlock, point) ) {
				if (isAir(dPoint)) {
					if (moveBlock instanceof BlockSpike) {
						Point3I bPoint = dPoint.offset(facing);
						if (isPushable(moveBlock, bPoint)) {
							Block smashBlock = bPoint.getBlock(worldObj);
							IBlockState smashState = bPoint.getState(worldObj);
							
							ItemHelper.dropBlockIntoWorld(worldObj, bPoint.getPos(), smashBlock, smashState);
						}
					}
					
					worldObj.setBlockState(dPoint.getPos(), moveState);
					TileEntity tile = point.getTileEntity(worldObj);
					if (tile!=null) {
						NBTTagCompound tileData = new NBTTagCompound();
						tile.writeToNBT(tileData);
						TileEntity newTile = dPoint.getTileEntity(worldObj);
						if (newTile!=null) {
							newTile.readFromNBT(tileData);
							worldObj.setTileEntity(dPoint.getPos(), newTile);
						}
					}

					worldObj.removeTileEntity(point.getPos());
					worldObj.setBlockToAir(point.getPos());
					//worldObj.playAuxSFX(2001, point.getPos(), moveBlock.getStateId(moveState));
					
					if (isSpike(distance+2)) {
						ItemHelper.dropBlockIntoWorld(worldObj, dPoint.getPos(), moveBlock, dPoint.getState(worldObj));
					}
				}
			}
		} else if (isAir(point, false)) {
			if ( (moveBlock.getMobilityFlag()==1) || (moveBlock.isReplaceable(worldObj, point.getPos())) ) {
				if (FluidRegistry.lookupFluidForBlock(moveBlock)==null) {
					worldObj.setBlockToAir(point.getPos());
					worldObj.playAuxSFX(2001, point.getPos(), moveBlock.getStateId(moveState));
				}
			}
		}
		return false;
	}
}
