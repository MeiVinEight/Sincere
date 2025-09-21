package org.mve.sincere.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.mve.sincere.item.ThrowableWeapon;

public class SincereEnchantment extends Enchantment
{
	public static final String ID = "sincere";

	public SincereEnchantment()
	{
		super(
			Rarity.VERY_RARE,
			EnchantmentCategory.create("sincere:throwable", ThrowableWeapon::throwable),
			new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}
		);
	}

	@Override
	public int getMinCost(int p_45102_)
	{
		return p_45102_ * 25;
	}

	@Override
	public int getMaxCost(int p_45105_)
	{
		return this.getMinCost(p_45105_) + 50;
	}
}
