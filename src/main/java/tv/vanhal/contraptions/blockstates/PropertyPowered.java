package tv.vanhal.contraptions.blockstates;

import java.util.Collection;

import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyEnum;

public class PropertyPowered extends PropertyEnum<EnumPowered> {

	protected PropertyPowered(String name, Collection<EnumPowered> allowedValues) {
		super(name, EnumPowered.class, allowedValues);
	}
	
	public static PropertyPowered create(String name) {
		return new PropertyPowered(name, Lists.newArrayList(EnumPowered.values()));
	}
	
}
