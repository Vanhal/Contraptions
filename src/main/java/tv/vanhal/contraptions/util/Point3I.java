package tv.vanhal.contraptions.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/* A Point case to allow for an x, y, z point */
public class Point3I {
	private int x, y, z;
	
	public Point3I() {
		setLocation(0, 0, 0);
	}
	
	public Point3I(NBTTagCompound nbt) {
		setNBT(nbt);
	}
	
	public Point3I(Point3I point) {
		setLocation(point.getX(), point.getY(), point.getZ());
	}
	
	public Point3I(int x, int y, int z) {
		setLocation(x, y, z);
	}
	
	public void setLocation(int x1, int y1, int z1) {
		this.x = x1;
		this.y = y1;
		this.z = z1;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public String toString() {
		return ""+x+", "+y+", "+z;
	}
	
	public Point3I offset(int _x, int _y, int _z) {
		Point3I result = new Point3I();
		result.setX(x + _x);
		result.setY(y + _y);
		result.setZ(z + _z);
		return result;
	}
	
	public Point3I offset(ForgeDirection dir) {
		return this.offset(dir, 1);
	}
	
	public Point3I offset(ForgeDirection dir, int times) {
		return this.offset(dir.offsetX*times, dir.offsetY*times, dir.offsetZ*times);
	}
	
	@Override
	public boolean equals(Object test) {
		if (test instanceof Point3I) {
			Point3I testPoint = (Point3I) test;
			return ( (x == testPoint.getX()) && (y == testPoint.getY()) && (z == testPoint.getZ()) );
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		String hash = x+"|"+y+"|"+z;
		return hash.hashCode();
	}
	
	public Point3I getAdjacentPoint(int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return getAdjacentPoint(dir);
	}
	
	public Point3I getAdjacentPoint(ForgeDirection dir) {
		return this.offset(dir);
	}
	
	public boolean blockExists(World world) {
		return world.blockExists(getX(), getY(), getZ());
	}
	
	public Block getBlock(IBlockAccess world) {
		return world.getBlock(getX(), getY(), getZ());
	}
	
	public TileEntity getTileEntity(IBlockAccess world) {
		return world.getTileEntity(getX(), getY(), getZ());
	}
	
	public int getMetaData(IBlockAccess world) {
		return world.getBlockMetadata(getX(), getY(), getZ());
	}
	
	public NBTTagCompound getNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", getX());
		tag.setInteger("y", getY());
		tag.setInteger("z", getZ());
		return tag;
	}
	
	public void setNBT(NBTTagCompound nbt) {
		setX(nbt.getInteger("x"));
		setY(nbt.getInteger("y"));
		setZ(nbt.getInteger("z"));
	}
}
