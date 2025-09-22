package org.mve.sincere.mixin;

import com.mega.revelationfix.common.entity.projectile.GungnirSpearEntity;
import com.mega.revelationfix.common.item.tool.combat.trident.GungnirItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.mve.sincere.item.ThrowableWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GungnirItem.class)
public class GungnirItemMixin implements ThrowableWeapon
{
	@Unique
	@Override
	public Entity drop(ItemStack stack, LivingEntity entity)
	{
		GungnirSpearEntity spear = new GungnirSpearEntity(entity.level(), entity, stack);
		spear.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.2F, 1);
		int rand = entity.getRandom().nextInt(0, 3);
		SoundEvent soundEvent;
		if (rand == 0)
			soundEvent = SoundEvents.TRIDENT_RIPTIDE_3;
		else if (rand == 1)
			soundEvent = SoundEvents.TRIDENT_RIPTIDE_2;
		else
			soundEvent = SoundEvents.TRIDENT_RIPTIDE_1;
		entity.level().playSound(null, entity, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);

		CompoundTag itemTag = stack.getOrCreateTag();
		if (itemTag.hasUUID("TargetID") && entity.level().isClientSide)
		{
			ServerLevel sl = (ServerLevel) entity.level();
			Entity target = sl.getEntity(itemTag.getInt("TargetID"));
			if (target != null && !target.isRemoved())
				spear.setTarget(target);
		}
		entity.level().addFreshEntity(spear);
		return spear;
	}
}
