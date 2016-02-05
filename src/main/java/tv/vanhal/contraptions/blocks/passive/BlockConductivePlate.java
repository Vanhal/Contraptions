package tv.vanhal.contraptions.blocks.passive;

import tv.vanhal.contraptions.blocks.BaseBlock;

public class BlockConductivePlate extends BaseBlock {

	public BlockConductivePlate() {
		super("conductivePlate");
		this.setFaceTexture(1, "conductivePlate_top");
		this.setSidesTexture("conductivePlate_side");
		setRotationType(null);
	}
}
