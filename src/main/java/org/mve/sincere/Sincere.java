package org.mve.sincere;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mve.sincere.enchantment.SincereEnchantments;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Mod(Sincere.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Sincere
{
	public static final String MODID = "sincere";
	public static final String VERSION;

	public Sincere(FMLJavaModLoadingContext context)
	{
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus modEventBus = context.getModEventBus();
		SincereEnchantments.ENCHANTMENTS.register(modEventBus);
	}

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event)
	{
	}

	public static String id(String name)
	{
		return MODID + ':' + name;
	}

	static
	{
		try (InputStream toml = Sincere.class.getResourceAsStream("../../../META-INF/mods.toml"))
		{
			if (toml == null)
				throw new NullPointerException("META-INF/mods.toml");
			CommentedConfig config = new TomlParser().parse(new InputStreamReader(toml));
			VERSION = ((CommentedConfig) ((ArrayList<?>) config.get("mods")).get(0)).get("version");
		}
		catch (Throwable t)
		{
			throw new RuntimeException(t);
		}
	}
}
