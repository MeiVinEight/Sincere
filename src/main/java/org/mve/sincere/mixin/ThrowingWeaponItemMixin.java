package org.mve.sincere.mixin;

import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.entity.ThrowingWeapon;
import org.mve.sincere.item.ThrowableWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ThrowingWeaponItem.class)
public abstract class ThrowingWeaponItemMixin implements ThrowableWeapon
{
	@Shadow(remap = false)
	protected abstract SoundEvent getThrowingSound();

	@Unique
	@Override
	public Entity drop(ItemStack stack, LivingEntity entity)
	{
		if (!(entity instanceof Player player))
			return null;
		ThrowingWeaponItem item = (ThrowingWeaponItem) stack.getItem();
		ThrowingWeaponEntity thrown = item.createThrowingWeaponEntity(entity.level(), player, stack, item.getMaxChargeTicks(stack));
		if (thrown == null)
			return null;

		((ThrowingWeapon) thrown).whole(true);
		((ThrowingWeapon) thrown).origin(stack);
		thrown.setWeapon(stack);
		thrown.shootFromRotation(player, player.xRotO, player.yRotO, 0, 0.2F, 0.5F);
		item.getAllWeaponTraits()
			.forEach(trait -> trait
				.getThrowingCallback()
				.ifPresent(callback -> callback.onThrowingProjectileSpawn(item.getMaterial(), thrown))
			);
		if (player.getAbilities().instabuild)
			thrown.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

		if (thrown.isValidThrowingWeapon())
		{
			player.level().playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				this.getThrowingSound(),
				SoundSource.PLAYERS,
				0.5F,
				0.4F / (player.level().random.nextFloat() * 0.4F + 0.8F)
			);
			player.level().addFreshEntity(thrown);
		}
		return thrown;
	}
}
