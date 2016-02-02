package tv.vanhal.contraptions.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.blocks.BlockSpike;
import tv.vanhal.contraptions.util.ItemHelper;

public class TilePoweredPiston extends BaseTile {
	protected int lastPower = 0;
	protected int cooldown = 0;
	
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
			int currentPower = this.isPoweredLevel();
			if ( (currentPower>0) && (lastPower==0) && (cooldown <= 0) ) {
				lastPower = currentPower;
				pushBlocks(currentPower);
				cooldown = 8;
				addPartialUpdate("cooldown", cooldown);
			} else if ( (currentPower==0) && (lastPower>0) ) {
				lastPower = currentPower;
			}
		}
	}
	
	protected void pushBlocks(int range) {
		int firstAir = 0;
		boolean foundBlock = false;
		for (int i = 1; i <= range; i++) {
			if (foundBlock) {
				if (isAir(i)) {
					firstAir = i;
					break;
				}
			} else {
				if ( (!isAir(i)) && (isPushable(i)) ) foundBlock = true;
			}
		}
		for (int i = firstAir; i>0; i--) {
			moveBlock(i);
		}
	}
	
	protected boolean isAir(int distance) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		return (worldObj.isAirBlock(x, y, z)) ;
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
		if (!worldObj.isAirBlock(x, y, z)) {
			Block moveBlock = worldObj.getBlock(x, y, z);
			int metaData = worldObj.getBlockMetadata(x, y, z);
			if ( isPushable(moveBlock, x, y, z) ) {
				if ( (worldObj.isAirBlock(dx, dy, dz)) || (worldObj.getBlock(dx, dy, dz).isReplaceable(worldObj, dx, dy, dz)) ) {
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
					worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(moveBlock) + (metaData << 12));
					
					if (isSpike(distance+2)) {
						ItemHelper.dropBlockIntoWorld(worldObj, dx, dy, dz, moveBlock, metaData);
					}
				}
			}
		}
		return false;
	}
}
