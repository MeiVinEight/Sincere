package org.mve.sincere.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;

public class ThrowableWeapon
{
	public static boolean throwable(Item item)
	{
		boolean ret = item instanceof TridentItem;
		try
		{
			ret |= Class.forName("com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem").isInstance(item);
		}
		catch (ClassNotFoundException ignored)
		{
		}
		return ret;
	}
}
