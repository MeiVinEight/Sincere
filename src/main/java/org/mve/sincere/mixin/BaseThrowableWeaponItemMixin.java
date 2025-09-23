package org.mve.sincere.mixin;

import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mve.sincere.item.ThrowableWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BaseThrowableWeaponItem.class)
public class BaseThrowableWeaponItemMixin implements ThrowableWeapon
{
	@Unique
	@Override
	public Entity drop(ItemStack stack, LivingEntity entity)
	{
		if (entity == null)
			return null;
		if (stack == null)
			return null;
		Level level = entity.level();
		BaseThrowableWeaponItem item = (BaseThrowableWeaponItem) (Object) this;
		int slot = 0;
		AbstractArrow proj = item.getProjectile(entity.level(), entity, stack, slot);
		proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 0.5F, 1.0F);
		level.addFreshEntity(proj);
		level.playSound(null, proj, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
		return proj;
	}
}
