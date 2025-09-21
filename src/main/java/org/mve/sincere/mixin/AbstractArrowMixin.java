package org.mve.sincere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.mve.sincere.Sincere;
import org.mve.sincere.entity.SincereArrow;
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

	@Override
	public void callback(boolean value)
	{
		this.callback = value;
	}

	@Override
	public boolean callback()
	{
		return this.callback;
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo info)
	{
		this.callback = tag.getBoolean(Sincere.id("callback"));
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag p_37582_, CallbackInfo ci)
	{
		p_37582_.putBoolean(Sincere.id("callback"), this.callback);
	}
}
