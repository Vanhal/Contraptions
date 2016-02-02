package tv.vanhal.contraptions.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraftforge.common.util.ForgeDirection;
import tv.vanhal.contraptions.Contraptions;

public class TilePoweredPiston extends BaseTile {
	protected int lastPower = 0;
	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("lastPower", lastPower);
	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("lastPower")) lastPower = nbt.getInteger("lastPower");
	}
	
	
	public void blockUpdated() {
		int currentPower = this.isPoweredLevel();
		if ( (currentPower>0) && (lastPower==0) ) {
			lastPower = currentPower;
			pushBlocks(currentPower);
		} else if ( (currentPower==0) && (lastPower>0) ) {
			lastPower = currentPower;
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
				if ( (!isAir(i)) && (isPushible(i)) ) foundBlock = true;
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
	
	protected boolean isPushible(int distance) {
		int x = xCoord + (facing.offsetX*distance);
		int y = yCoord + (facing.offsetY*distance);
		int z = zCoord + (facing.offsetZ*distance);
		return isPushible(x, y, z);
	}
	
	protected boolean isPushible(int x, int y, int z) {
		Block moveBlock = worldObj.getBlock(x, y, z);
		return ( (moveBlock.getMobilityFlag()==0) && (moveBlock != Blocks.obsidian) );
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
			if ( (moveBlock.getMobilityFlag()==0) && (moveBlock != Blocks.obsidian) ) {
				if ( (worldObj.isAirBlock(dx, dy, dz)) || (worldObj.getBlock(dx, dy, dz).isReplaceable(worldObj, dx, dy, dz)) ) {
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
				}
			}
		}
		return false;
	}

}
