package org.mve.sincere.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.Configuration;

public interface ThrowingWeapon
{
	boolean whole();
	void whole(boolean v);
	void origin(ItemStack stack);

	public static int loyalty(AbstractArrow arrow, EntityDataAccessor<Boolean> sincereID, int value)
	{
		ThrowingType type = ((SincereArrow) arrow).type();
		if (type == ThrowingType.THROW && !Configuration.SINCERE_THROW.get())
			return value;
		if (type == ThrowingType.DEAD && !Configuration.SINCERE_DEAD.get())
			return value;
		if (type == ThrowingType.DROP && !Configuration.SINCERE_DROP.get())
			return value;
		if (!arrow.getEntityData().get(sincereID))
			return value;
		if (!((SincereArrow) arrow).callback())
			return 0;
		if (value == 0)
			value = 1;
		return value;
	}
}
