package org.mve.sincere.mixin;

import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseThrownWeaponEntity.class)
public interface BaseThrownWeaponEntityAccessor
{
	@Accessor("item")
	ItemStack item();
}
