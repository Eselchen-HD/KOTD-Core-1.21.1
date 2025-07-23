package de.eselgamerhd.kotd.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.eselgamerhd.kotd.Kotd;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ConfigButton extends AbstractConfigButton {
    protected static final ConfigButton.CreateNarration DEFAULT_NARRATION = Supplier::get;
    protected final ConfigButton.OnPress onPress;
    protected final ConfigButton.CreateNarration createNarration;

    public static ConfigButton.Builder builder(Component message, ConfigButton.OnPress onPress) {
        return new ConfigButton.Builder(message, onPress);
    }

    protected ConfigButton(int x, int y, int width, int height, Component message, ConfigButton.OnPress onPress, ConfigButton.CreateNarration createNarration) {
        super(x, y, width, height, message);
        this.onPress = onPress;
        this.createNarration = createNarration;
    }
    protected ConfigButton(ConfigButton.Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.createNarration);
        if (builder.tooltip != null) {setTooltip(builder.tooltip);}
    }
    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected @NotNull MutableComponent createNarrationMessage() {
        return this.createNarration.createNarrationMessage(super::createNarrationMessage);
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        private final ConfigButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private ConfigButton.CreateNarration createNarration = ConfigButton.DEFAULT_NARRATION;

        public Builder(Component message, ConfigButton.OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public ConfigButton.Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        @SuppressWarnings("unused")
        public ConfigButton.Builder width(int width) {
            this.width = width;
            return this;
        }

        public ConfigButton.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public ConfigButton.Builder bounds(int x, int y, int width, int height) {
            return this.pos(x, y).size(width, height);
        }

        @SuppressWarnings("unused")
        public ConfigButton.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        @SuppressWarnings("unused")
        public ConfigButton.Builder createNarration(ConfigButton.CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public ConfigButton build() {
            return build(ConfigButton::new);
        }

        public ConfigButton build(java.util.function.Function<ConfigButton.Builder, ConfigButton> builder) {
            return builder.apply(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> messageSupplier);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(ConfigButton ConfigButton);
    }
}

abstract class AbstractConfigButton extends AbstractButton {
    protected static final WidgetSprites SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "widget/button"),
            ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "widget/button_disabled"),
            ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "widget/button_hovered")
    );

    public AbstractConfigButton(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public void onPress() {}

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = getFGColor();
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }
}
