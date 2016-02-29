package tv.vanhal.contraptions.blockstates;

import net.minecraft.util.IStringSerializable;

public enum EnumPowered implements IStringSerializable {
	unpowered("unpowered"),
	powered("powered"),
	full("full");
	
	private String name;
	
	private EnumPowered(String nameIn) {
		name = nameIn;
	}

	@Override
	public String getName() {
		return name;
	}
}