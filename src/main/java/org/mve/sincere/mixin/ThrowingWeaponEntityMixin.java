package org.mve.sincere.mixin;

import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.enchantment.SincereEnchantments;
import org.mve.sincere.entity.SincereArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrowingWeaponEntity.class)
public class ThrowingWeaponEntityMixin
{
	@Unique
	private static final EntityDataAccessor<Boolean> ID_SINCERE = SynchedEntityData.defineId(ThrowingWeaponEntity.class, EntityDataSerializers.BOOLEAN);

	@Inject(method = "defineSynchedData", at = @At("RETURN"))
	public void defineSynchedData(CallbackInfo ci)
	{
		ThrowingWeaponEntity entity = (ThrowingWeaponEntity) (Object) this;
		entity.getEntityData().define(ID_SINCERE, false);
	}

	@ModifyVariable(
		method = "tick",
		at = @At(value = "STORE"),
		ordinal = 0
	)
	public int tickLoyalty(int value)
	{
		ThrowingWeaponEntity entity = (ThrowingWeaponEntity) (Object) this;
		if (!entity.getEntityData().get(ID_SINCERE))
			return value;
		if (!((SincereArrow) entity).callback())
			return 0;
		if (value == 0)
			value = 1;
		return value;
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		ThrowingWeaponEntity trident = (ThrowingWeaponEntity) (Object) this;
		ItemStack item = trident.getEntityData().get(ThrowingWeaponEntityAccessor.weapon());
		if (item == null)
			return;
		trident.getEntityData().set(ID_SINCERE, item.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}

	@Inject(method = "setWeapon", at = @At("RETURN"), remap = false)
	public void setWeapon(ItemStack weaponStack, CallbackInfo ci)
	{
		ThrowingWeaponEntity trident = (ThrowingWeaponEntity) (Object) this;
		trident.getEntityData().set(ID_SINCERE, weaponStack.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}
}
