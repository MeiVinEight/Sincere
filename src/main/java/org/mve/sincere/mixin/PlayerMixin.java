package org.mve.sincere.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.Configuration;
import org.mve.sincere.item.ThrowableWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin
{
	@Inject(
		method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
		at = @At("HEAD"),
		cancellable = true
	)
	public void drop(ItemStack p_36179_, boolean p_36180_, boolean p_36181_, CallbackInfoReturnable<ItemEntity> cir)
	{
		if (!p_36181_ && !p_36180_)
			return;
		if (p_36181_ && !Configuration.SINCERE_DROP.get())
			return;
		if (p_36180_ && !Configuration.SINCERE_DEAD.get())
			return;
		if (p_36179_ == null)
			return;
		Item item = p_36179_.getItem();
		if (!ThrowableWeapon.throwable(item))
			return;
		Player player = (Player) (Object) this;
		if (player.level().isClientSide)
			player.swing(InteractionHand.MAIN_HAND);
		int count = p_36179_.getCount();
		ItemStack copy = p_36179_.copy();
		copy.setCount(1);
		while (count --> 0)
		{
			((ThrowableWeapon) item).drop(copy, player);
		}
		cir.setReturnValue(null);
	}
}
