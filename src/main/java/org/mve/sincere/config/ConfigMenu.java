package org.mve.sincere.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.mve.sincere.Configuration;

public class ConfigMenu extends Screen
{
	private final Screen parent;
	public final BooleanValue SINCERE_THROW;
	public final BooleanValue SINCERE_DROP;
	public final BooleanValue SINCERE_DEAD;

	public ConfigMenu(Screen parent)
	{
		super(Component.translatable("sincere.config.title"));
		this.parent = parent;
		Minecraft mc = parent.getMinecraft();
		SINCERE_THROW = new BooleanValue(parent, "sincere.config.throw.name", mc.font);
		SINCERE_THROW.value = Configuration.SINCERE_THROW.get();
		SINCERE_THROW.resetValue = val -> ((BooleanValue) val).value = Configuration.SINCERE_THROW.getDefault();
		SINCERE_THROW.tooltip = Component.translatable("sincere.config.throw.tooltip");
		SINCERE_DROP = new BooleanValue(parent, "sincere.config.drop.name", mc.font);
		SINCERE_DROP.value = Configuration.SINCERE_DROP.get();
		SINCERE_DROP.resetValue = val -> ((BooleanValue) val).value = Configuration.SINCERE_DROP.getDefault();
		SINCERE_DROP.tooltip = Component.translatable("sincere.config.drop.tooltip");
		SINCERE_DEAD = new BooleanValue(parent, "sincere.config.dead.name", mc.font);
		SINCERE_DEAD.value = Configuration.SINCERE_DEAD.get();
		SINCERE_DEAD.resetValue = val -> ((BooleanValue) val).value = Configuration.SINCERE_DEAD.getDefault();
		SINCERE_DEAD.tooltip = Component.translatable("sincere.config.dead.tooltip");
	}

	@Override
	public void init()
	{
		this.clearWidgets();

		int containerWidth = this.width - (2 * ConfigValue.PADDING);
		int containerHeight = this.height - (3 * ConfigValue.PADDING) - 20;

		int y = this.height - 20 - ConfigValue.PADDING;
		int doneButtonWidth = Math.min(200, (this.width - (ConfigValue.PADDING * 3)) / 2);
		Button saveButton = new Button.Builder(Component.translatable("sincere.config.save"), b -> this.save())
			.bounds((width - (doneButtonWidth * 2) - ConfigValue.PADDING) / 2, y, doneButtonWidth, 20)
			.build();
		Button doneButton = new Button.Builder(Component.translatable("sincere.config.done"), (button1) -> this.close())
			.bounds((width - ConfigValue.PADDING) / 2 + ConfigValue.PADDING, y, doneButtonWidth, 20)
			.build();
		this.addRenderableWidget(saveButton);
		this.addRenderableWidget(doneButton);

		ConfigArray array = new ConfigArray(this.getMinecraft(), containerWidth, containerHeight, ConfigValue.PADDING, ConfigValue.PADDING);
		array.push(this.SINCERE_THROW);
		array.push(this.SINCERE_DROP);
		array.push(this.SINCERE_DEAD);
		this.addRenderableWidget(array);
	}

	@Override
	public void onClose()
	{
		if (this.minecraft == null)
			super.onClose();
		else
			this.minecraft.setScreen(this.parent);
	}

	@Override
	public void render(GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_)
	{
		this.renderBackground(p_281549_);
		super.render(p_281549_, p_281550_, p_282878_, p_282465_);
	}

	@Override
	public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_)
	{
		return super.keyPressed(p_96552_, p_96553_, p_96554_);
	}

	public void save()
	{
		Configuration.SINCERE_THROW.set(this.SINCERE_THROW.value);
		Configuration.SINCERE_THROW.save();
		Configuration.SINCERE_DROP.set(this.SINCERE_DROP.value);
		Configuration.SINCERE_DROP.save();
		Configuration.SINCERE_DEAD.set(this.SINCERE_DEAD.value);
		Configuration.SINCERE_DEAD.save();
	}

	public void close()
	{
		this.save();
		this.onClose();
	}
}
