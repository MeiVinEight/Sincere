package org.mve.sincere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.mve.sincere.Sincere;
import org.mve.sincere.entity.SincereArrow;
import org.mve.sincere.entity.ThrowingType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin implements SincereArrow
{
	@Unique
	private static final EntityDataAccessor<Byte> ID_TYPE = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
	@Unique
	public boolean callback;

	@ModifyVariable(
		method = "tick",
		at = @At(value = "STORE"),
		ordinal = 0
	)
	public float tick(float value)
	{
		/*
		AbstractArrow arrow = (AbstractArrow) (Object) this;
		if ((arrow instanceof ThrownTrident) && (((ThrownTridentAccessor) arrow).item().getEnchantmentLevel(SincereEnchantments.SINCERE.get()) > 0))
			value = 0;
		 */
		return value;
	}

	@Inject(method = "defineSynchedData", at = @At("RETURN"))
	public void defineSynchedData(CallbackInfo ci)
	{
		AbstractArrow entity = (AbstractArrow) (Object) this;
		entity.getEntityData().define(ID_TYPE, (byte) 0);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		this.callback = tag.getBoolean(Sincere.id("callback"));
		this.type(ThrowingType.values()[tag.getByte("sincere:throwingType")]);
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag p_37582_, CallbackInfo ci)
	{
		p_37582_.putBoolean(Sincere.id("callback"), this.callback);
		p_37582_.putByte("sincere:throwingType", (byte) this.type().ordinal());
	}

	@Unique
	@Override
	public void callback(boolean value)
	{
		this.callback = value;
	}

	@Unique
	@Override
	public boolean callback()
	{
		return this.callback;
	}

	@Unique
	@Override
	public void type(ThrowingType type)
	{
		if (type == null)
			return;
		((Entity) (Object) this).getEntityData().set(ID_TYPE, (byte) type.ordinal());
	}

	@Unique
	@Override
	public ThrowingType type()
	{
		return ThrowingType.values()[((Entity) (Object) this).getEntityData().get(ID_TYPE)];
	}
}
