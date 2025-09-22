package org.mve.sincere.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.mve.sincere.item.ThrowableWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TridentItem.class)
public class TridentItemMixin implements ThrowableWeapon
{
	@Unique
	public Entity drop(ItemStack stack, LivingEntity entity)
	{
		ThrownTrident trident = new ThrownTrident(entity.level(), entity, stack);
		trident.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.2F, 1);
		if ((entity instanceof Player player) && player.getAbilities().instabuild)
			trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

		entity.level().addFreshEntity(trident);
		entity.level().playSound(null, trident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
		return trident;
	}
}
