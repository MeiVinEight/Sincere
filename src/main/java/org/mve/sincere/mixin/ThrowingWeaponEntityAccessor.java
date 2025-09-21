package org.mve.sincere.mixin;

import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ThrowingWeaponEntity.class)
public interface ThrowingWeaponEntityAccessor
{
	@Accessor(value = "DATA_WEAPON", remap = false)
	public static EntityDataAccessor<ItemStack> weapon() {throw new RuntimeException();}
}
