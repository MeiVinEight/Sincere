package org.mve.sincere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
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

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin
{
	@Unique
	private static final EntityDataAccessor<Boolean> ID_SINCERE = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.BOOLEAN);

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V",
		at = @At("RETURN")
	)
	public void init(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_, CallbackInfo ci)
	{
		ThrownTrident trident = (ThrownTrident) (Object) this;
		trident.getEntityData().set(ID_SINCERE, p_37571_.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
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
		ThrownTrident trident = (ThrownTrident) (Object) this;
		trident.getEntityData().define(ID_SINCERE, false);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		ThrownTrident trident = (ThrownTrident) (Object) this;
		ItemStack item = ((ThrownTridentAccessor) trident).item();
		trident.getEntityData().set(ID_SINCERE, item.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}
}
