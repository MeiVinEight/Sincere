package org.mve.sincere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.mve.sincere.config.ConfigMenu;

import java.util.function.BiFunction;

public class ConfigurationFactory implements BiFunction<Minecraft, Screen, Screen>
{
	@Override
	public Screen apply(Minecraft minecraft, Screen screen)
	{
		ConfigMenu menu = new ConfigMenu(screen);
		minecraft.pushGuiLayer(menu);
		return menu;
	}
}
