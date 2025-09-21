package org.mve.sincere.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.mve.sincere.entity.SincereArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.function.Predicate;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin
{
	@Inject(method = "useItemOn", at = @At("HEAD"))
	public void useItemOn(
		ServerPlayer p_9266_,
		Level p_9267_,
		ItemStack p_9268_,
		InteractionHand p_9269_,
		BlockHitResult p_9270_,
		CallbackInfoReturnable<InteractionResult> cir
	)
	{
		if (p_9266_.isSpectator())
			return;
		if (p_9269_ != InteractionHand.MAIN_HAND)
			return;
		if (p_9270_.getType() != HitResult.Type.MISS)
			return;

		System.out.println("Callback " + p_9266_.getUUID());
		ServerLevel sl = (ServerLevel) p_9266_.level();
		UUID uuid = p_9266_.getUUID();
		Predicate<Projectile> pred = (proj) -> uuid.equals(((ProjectileAccessor) proj).owner());
		sl.getEntities(EntityTypeTest.forClass(AbstractArrow.class), pred)
			.forEach(arrow -> ((SincereArrow) arrow).callback(true));
	}
}
