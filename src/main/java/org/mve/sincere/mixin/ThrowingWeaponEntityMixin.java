package org.mve.sincere.mixin;

import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.enchantment.SincereEnchantments;
import org.mve.sincere.entity.ThrowingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowingWeaponEntity.class)
public class ThrowingWeaponEntityMixin implements ThrowingWeapon
{
	@Unique
	private static final String TAG_WHOLE = "sincere:whole";
	@Unique
	private static final String TAG_ORIGIN = "sincere:origin";
	@Unique
	private static final EntityDataAccessor<Boolean> ID_SINCERE = SynchedEntityData.defineId(ThrowingWeaponEntity.class, EntityDataSerializers.BOOLEAN);
	@Unique
	private boolean whole = false;
	@Unique
	private ItemStack origin = ItemStack.EMPTY;

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
		return ThrowingWeapon.loyalty((AbstractArrow) (Object) this, ID_SINCERE, value);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		ThrowingWeaponEntity trident = (ThrowingWeaponEntity) (Object) this;
		ItemStack item = trident.getEntityData().get(ThrowingWeaponEntityAccessor.weapon());
		if (item == null)
			return;
		trident.getEntityData().set(ID_SINCERE, item.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
		this.whole = tag.getBoolean(TAG_WHOLE);
		this.origin = ItemStack.of(tag.getCompound(TAG_ORIGIN));
	}

	@Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
	public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci)
	{
		compound.putBoolean(TAG_WHOLE, this.whole);
		compound.put(TAG_ORIGIN, this.origin.save(new CompoundTag()));
	}

	@Inject(method = "setWeapon", at = @At("RETURN"), remap = false)
	public void setWeapon(ItemStack weaponStack, CallbackInfo ci)
	{
		ThrowingWeaponEntity trident = (ThrowingWeaponEntity) (Object) this;
		trident.getEntityData().set(ID_SINCERE, weaponStack.getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0);
	}

	@Inject(method = "attemptCatch", at = @At("HEAD"), remap = false, cancellable = true)
	public void attemptCatch(Player player, CallbackInfoReturnable<Boolean> cir)
	{
		if (player == null)
			return;
		if (player.level().isClientSide)
			return;
		ThrowingWeaponEntity entity = (ThrowingWeaponEntity)  (Object) this;
		boolean canBePickedUp = entity.pickup == AbstractArrow.Pickup.ALLOWED || entity.pickup == AbstractArrow.Pickup.CREATIVE_ONLY && player.getAbilities().instabuild;
		if (!this.whole)
			return;
		if (canBePickedUp)
			canBePickedUp = player.getInventory().add(this.origin.copy());
		if (canBePickedUp)
		{
			player.take(entity, 1);
			entity.discard();
		}
		cir.setReturnValue(canBePickedUp);
	}

	@Unique
	@Override
	public boolean whole()
	{
		return this.whole;
	}

	@Unique
	@Override
	public void whole(boolean v)
	{
		this.whole = v;
	}

	@Unique
	@Override
	public void origin(ItemStack stack)
	{
		this.origin = stack;
	}
}
