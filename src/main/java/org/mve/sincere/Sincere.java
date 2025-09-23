package org.mve.sincere;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mve.sincere.enchantment.SincereEnchantments;

@Mod(Sincere.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Sincere
{
	public static final String MODID = "sincere";

	public Sincere(FMLJavaModLoadingContext context)
	{
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus modEventBus = context.getModEventBus();
		SincereEnchantments.ENCHANTMENTS.register(modEventBus);
		context.registerConfig(ModConfig.Type.COMMON, Configuration.SPECIFICATION);
	}

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event)
	{
		MinecraftForge.registerConfigScreen(new ConfigurationFactory());
	}

	public static String id(String name)
	{
		return MODID + ':' + name;
	}
}
