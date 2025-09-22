package org.mve.sincere;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration
{
	public static final ForgeConfigSpec.BooleanValue SINCERE_THROW;
	public static final ForgeConfigSpec.BooleanValue SINCERE_DROP;
	public static final ForgeConfigSpec.BooleanValue SINCERE_DEAD;
	public static final ForgeConfigSpec SPECIFICATION;

	static
	{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		SINCERE_THROW = builder
			.comment("Sincere effects while throw")
			.define("SINCERE_THROW", false);
		SINCERE_DROP = builder
			.comment("Sincere effects while drop")
			.define("SINCERE_DROP", false);
		SINCERE_DEAD = builder
			.comment("Sincere effects while dead")
			.define("SINCERE_DEAD", false);
		SPECIFICATION = builder.build();
	}
}
