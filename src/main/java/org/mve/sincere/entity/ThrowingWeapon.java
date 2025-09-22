package org.mve.sincere.entity;

import net.minecraft.world.item.ItemStack;

public interface ThrowingWeapon
{
	boolean whole();
	void whole(boolean v);
	void origin(ItemStack stack);
}
