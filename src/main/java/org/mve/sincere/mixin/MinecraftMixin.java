package org.mve.sincere.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.InputEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
	@Inject(
		method = "startUseItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraftforge/common/ForgeHooks;onEmptyClick(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)V"
		),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void startUseItem(CallbackInfo ci, InteractionHand[] var1, int var2, int var3, InteractionHand interactionhand, InputEvent.InteractionKeyMappingTriggered inputEvent, ItemStack itemstack)
	{
		Minecraft mc = (Minecraft) (Object) this;
		if (mc.player == null)
			return;
		if (mc.gameMode == null)
			return;

		BlockHitResult resule;
		if (mc.hitResult instanceof BlockHitResult)
			resule = (BlockHitResult) mc.hitResult;
		else
			resule = BlockHitResult.miss(mc.player.getEyePosition(), mc.player.getDirection(), BlockPos.containing(mc.player.getEyePosition()));
		mc.gameMode.useItemOn(mc.player, interactionhand, resule);
	}
}
