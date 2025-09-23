package org.mve.sincere.mixin;

import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mve.sincere.enchantment.SincereEnchantments;
import org.mve.sincere.entity.ThrowingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseThrownWeaponEntity.class)
public class BaseThrownWeaponEntityMixin
{
	@Unique
	private static final EntityDataAccessor<Boolean> ID_SINCERE = SynchedEntityData.defineId(BaseThrownWeaponEntity.class, EntityDataSerializers.BOOLEAN);

	@Inject(
		method = "<init>" +
			"(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;I)V",
		at = @At("RETURN")
	)
	public void BaseThrownWeaponEntity(EntityType<?> type, Level pLevel, LivingEntity pShooter, ItemStack pStack, int slot, CallbackInfo ci)
	{
		((Entity) (Object) this).getEntityData().set(ID_SINCERE, pStack.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}

	@ModifyVariable(
		method = "tickEarlyReturn",
		at = @At("STORE"),
		ordinal = 0,
		remap = false
	)
	public int tickEarlyReturnLoyalty(int value)
	{
		return ThrowingWeapon.loyalty(((AbstractArrow) (Object) this), ID_SINCERE, value);
	}

	@ModifyVariable(
		method = "tick",
		at = @At(value = "STORE"),
		ordinal = 0
	)
	public int tickLoyalty(int value)
	{
		return ThrowingWeapon.loyalty((AbstractArrow) (Object) this, ID_SINCERE, value);
	}

	@Inject(method = "defineSynchedData", at = @At("RETURN"))
	private void defineSynchedData(CallbackInfo ci)
	{
		Entity trident = (Entity) (Object) this;
		trident.getEntityData().define(ID_SINCERE, false);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		Entity trident = (Entity) (Object) this;
		ItemStack item = ((BaseThrownWeaponEntityAccessor) trident).item();
		trident.getEntityData().set(ID_SINCERE, item.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}
}
