package org.mve.sincere.mixin;

import com.mega.revelationfix.common.entity.projectile.GungnirSpearEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GungnirSpearEntity.class)
public interface GungnirSpearEntityAccessor
{
	@Accessor(value = "spearItem", remap = false)
	public abstract ItemStack item();
}
