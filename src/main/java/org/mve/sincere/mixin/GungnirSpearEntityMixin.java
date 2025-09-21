package org.mve.sincere.mixin;

import com.mega.revelationfix.common.entity.projectile.GungnirSpearEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mve.sincere.enchantment.SincereEnchantments;
import org.mve.sincere.entity.SincereArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GungnirSpearEntity.class)
public class GungnirSpearEntityMixin
{
	@Unique
	private static final EntityDataAccessor<Boolean> ID_SINCERE = SynchedEntityData.defineId(GungnirSpearEntity.class, EntityDataSerializers.BOOLEAN);

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V",
		at = @At("RETURN"),
		remap = false
	)
	public void init(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_, CallbackInfo ci)
	{
		GungnirSpearEntity trident = (GungnirSpearEntity) (Object) this;
		trident.getEntityData().set(ID_SINCERE, p_37571_.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}

	@ModifyVariable(
		method = "tick",
		at = @At(value = "STORE"),
		ordinal = 0
	)
	public int tickLoyalty(int value)
	{
		GungnirSpearEntity trident = (GungnirSpearEntity) (Object) this;
		if (!trident.getEntityData().get(ID_SINCERE))
			return value;
		if (!((SincereArrow) trident).callback())
			return 0;
		if (value == 0)
			value = 1;
		return value;
	}

	@Inject(method = "defineSynchedData", at = @At("RETURN"))
	private void defineSynchedData(CallbackInfo ci)
	{
		GungnirSpearEntity trident = (GungnirSpearEntity) (Object) this;
		trident.getEntityData().define(ID_SINCERE, false);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		GungnirSpearEntity trident = (GungnirSpearEntity) (Object) this;
		ItemStack item = ((GungnirSpearEntityAccessor) trident).item();
		trident.getEntityData().set(ID_SINCERE, item.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}
}
