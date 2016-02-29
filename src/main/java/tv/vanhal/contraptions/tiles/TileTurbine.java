package tv.vanhal.contraptions.tiles;

import tv.vanhal.contraptions.ContConfig;
import tv.vanhal.contraptions.Contraptions;
import tv.vanhal.contraptions.fluids.ContFluids;
import tv.vanhal.contraptions.interfaces.ITorqueBlock;
import tv.vanhal.contraptions.util.Point3I;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

public class TileTurbine extends BaseTile {	
	public float currentSpin = 0;
	protected int steamStorage = 0;
	protected int maxSteam = 1000;
	
	protected int steamConsumed = 0;
	protected int currentTorque = 0;
	protected int prevTorque = 0;

	public TileTurbine() {
		super();
	}
	
	
	public void doUpdate() {
		if (!worldObj.isRemote) {
			//torque only lasts for a tick
			prevTorque = currentTorque;
			currentTorque = 0;
			
			//pull in more steam if there is space
			if (steamStorage < maxSteam) {
				Point3I testPoint = getPoint().offset(0, -1, 0);
				Block testBlock = testPoint.getBlock(worldObj);
				if (testBlock instanceof IFluidBlock) {
					IFluidBlock fluidBlock = (IFluidBlock)testBlock;
					if (fluidBlock.getFluid() == ContFluids.steamFluid) {
						FluidStack fluidStack = fluidBlock.drain(worldObj, testPoint.getPos(), false);
						if (fluidStack.amount <= (maxSteam - steamStorage)) {
							steamStorage += fluidStack.amount;
							fluidStack = fluidBlock.drain(worldObj, testPoint.getPos(), true);
						}
					}
					
				}
			}
			//turn some steam into rotation
			if (steamStorage >= ContConfig.STEAM_CONSUMED_PER_TICK) {
				steamConsumed += ContConfig.STEAM_CONSUMED_PER_TICK;
				steamStorage -= ContConfig.STEAM_CONSUMED_PER_TICK;
			}
			if (steamConsumed >= ContConfig.STEAM_NEEDED_PER_ROTATION) {
				currentTorque = (int)Math.floor(steamConsumed / (float) ContConfig.STEAM_NEEDED_PER_ROTATION);
				steamConsumed -= ContConfig.STEAM_NEEDED_PER_ROTATION * currentTorque;
			}
			if (prevTorque != currentTorque) {
				addPartialUpdate("currentTorque", currentTorque, true);
			}
		}
	}
	
	public boolean isRunning() {
		Block thisBlock = getPoint().getBlock(worldObj);
		if (thisBlock instanceof ITorqueBlock) {
			int amountOfTorque = currentTorque;
			amountOfTorque += ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj, getPoint(), facing.ordinal());
			amountOfTorque += ((ITorqueBlock)thisBlock).getTorqueTransfering(worldObj,  getPoint(), facing.getOpposite().ordinal());
			return (amountOfTorque > 0);
		}
		return false;
	}

	
	@Override
	public void writeCommonNBT(NBTTagCompound nbt) {
		super.writeCommonNBT(nbt);
		nbt.setInteger("steamStorage", steamStorage);
		nbt.setInteger("steamConsumed", steamConsumed);
		nbt.setInteger("currentTorque", currentTorque);

	}
	
	@Override
	public void readCommonNBT(NBTTagCompound nbt) {
		super.readCommonNBT(nbt);
		if (nbt.hasKey("steamStorage")) steamStorage = nbt.getInteger("steamStorage");
		if (nbt.hasKey("steamConsumed")) steamConsumed = nbt.getInteger("steamConsumed");
		if (nbt.hasKey("currentTorque")) currentTorque = nbt.getInteger("currentTorque");

	}


	public int getTorque() {
		return currentTorque;
	}
}
