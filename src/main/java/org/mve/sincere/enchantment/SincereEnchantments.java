package org.mve.sincere.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mve.sincere.Sincere;

@Mod.EventBusSubscriber(
	modid = Sincere.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD
)
public class SincereEnchantments
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS;
	public static final RegistryObject<Enchantment> SINCERE;

	static
	{
		ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Sincere.MODID);
		SINCERE = ENCHANTMENTS.register(SincereEnchantment.ID, SincereEnchantment::new);
	}
}
