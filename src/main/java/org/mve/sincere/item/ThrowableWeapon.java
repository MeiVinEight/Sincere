package org.mve.sincere.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.entity.ThrowingType;

public interface ThrowableWeapon
{
	public static boolean throwable(Item item)
	{
		return item instanceof ThrowableWeapon;
	}

	Entity drop(ItemStack stack, LivingEntity entity);
}
