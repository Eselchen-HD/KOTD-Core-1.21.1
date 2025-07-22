package de.eselgamerhd.kotd.client.gui;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor.KotdCrystalArmorItem;
import de.eselgamerhd.kotd.common.items.item.ultimateKotdBlade.UltimateKotdBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class ConfigScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/gui/backgrounds/config_screen_background.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 222;

    private enum PopupType { NONE, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SWORD, PICKAXE }
    private PopupType currentPopup = PopupType.NONE;
    private int popupAnimation = 0;
    private static final int POPUP_ANIMATION_TIME = 5;

    public ConfigScreen() {
        super(Component.translatable("gui.kotd.attribute_menu"));
    }

    public record ArmorAttribute(
            Component displayName,
            Holder<Attribute> attribute,
            double baseValue,
            EquipmentSlot slot,
            String configKey
    ) {}
    private final Map<EquipmentSlot, List<ArmorAttribute>> slotAttributes = new HashMap<>();
    private void initSlotAttributes() {
        slotAttributes.put(
        EquipmentSlot.HEAD, List.of(
                new ArmorAttribute(
                        Component.translatable("attribute.kotd.oxygen"),
                        Attributes.OXYGEN_BONUS,
                        50.0,
                        EquipmentSlot.HEAD,
                        "oxygen_bonus"
                ),
                new ArmorAttribute(
                        Component.translatable("attribute.kotd.efficiency"),
                        Attributes.MINING_EFFICIENCY,
                        50.0,
                        EquipmentSlot.HEAD,
                        "efficiency"
                )
        ));

        slotAttributes.put(
                EquipmentSlot.CHEST, List.of(
                new ArmorAttribute(
                        Component.translatable("attribute.kotd.health"),
                        Attributes.MAX_HEALTH,
                        50.0,
                        EquipmentSlot.CHEST,
                        "health_boost"
                )
        ));

        slotAttributes.put(
                EquipmentSlot.LEGS, List.of(
                new ArmorAttribute(
                        Component.translatable("attribute.kotd.movement"),
                        Attributes.MOVEMENT_SPEED,
                        30.0,
                        EquipmentSlot.LEGS,
                        "movement_speed"
                )
        ));
        slotAttributes.put(
                EquipmentSlot.FEET, List.of(
                        new ArmorAttribute(
                                Component.translatable("attribute.kotd.step"),
                                Attributes.STEP_HEIGHT,
                                30.0,
                                EquipmentSlot.FEET,
                                "step_height"
                        ),
                        new ArmorAttribute(
                                Component.translatable("attribute.kotd.jump_strength"),
                                Attributes.JUMP_STRENGTH,
                                1.0,
                                EquipmentSlot.FEET,
                                "jump_strength"
                        )
        ));
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();

        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;

        initSlotAttributes();

        if (currentPopup != PopupType.NONE) {
            initArmorPopup();
        }

        addMainButtons(left, top);
    }

    @SuppressWarnings("unused")
    private void addMainButtons(int left, int top) {
        if (currentPopup == PopupType.NONE) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        boolean hasHelmet = helmet.getItem() instanceof KotdCrystalArmorItem;
        this.addRenderableWidget(ConfigButton.builder(
                            Component.translatable("config.kotd.helmet"),
                            Helmet -> openPopup(PopupType.HELMET))
                .bounds(left + 20, top + 20, 59, 15)
                .build());

        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        boolean hasChestplate = chestplate.getItem() instanceof KotdCrystalArmorItem;
        this.addRenderableWidget(ConfigButton.builder(
                        Component.translatable("config.kotd.chestplate"),
                        Chestplate -> openPopup(PopupType.CHESTPLATE))
                .bounds(left + 20, top + 35, 59, 15)
                .build());

        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        boolean hasLeggings = leggings.getItem() instanceof KotdCrystalArmorItem;
        this.addRenderableWidget(ConfigButton.builder(
                        Component.translatable("config.kotd.leggings"),
                        Leggings -> openPopup(PopupType.LEGGINGS))
                .bounds(left + 20, top + 50, 59, 15)
                .build());

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        boolean hasBoots = boots.getItem() instanceof KotdCrystalArmorItem;
        this.addRenderableWidget(ConfigButton.builder(
                        Component.translatable("config.kotd.boots"),
                        Boots -> openPopup(PopupType.BOOTS))
                .bounds(left + 20, top + 65, 59, 15)
                .build());

        ItemStack sword = player.getItemBySlot(EquipmentSlot.MAINHAND);
        boolean hasSword = boots.getItem() instanceof KotdCrystalArmorItem;
        this.addRenderableWidget(ConfigButton.builder(
                        Component.translatable("config.kotd.sword"),
                        Boots -> openPopup(PopupType.SWORD))
                .bounds(left + 90, top + 65, 59, 15)
                .build());

        // Close Button
        this.addRenderableWidget(Button.builder(
                        Component.translatable("gui.kotd.close"),
                        Exit -> this.onClose())
                .bounds(left + 220, top + 205, 33, 13)
                .build());
        }
    }
    private void initPopup() {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        int popupX = width / 2 - 100;
        int popupY = height / 2 - 50;
        //noinspection unused
        this.addRenderableWidget(new CloseButton(
                popupX + 150, popupY + 80, 40, 15,
                Component.translatable("gui.kotd.close")));

        this.addRenderableWidget(new BlockingOverlay());
        this.addRenderableWidget(new PopupBackground(popupX, popupY, 200, 100));

    }
    private void initArmorPopup() {
        ItemStack armorPiece = getArmorPiece(currentPopup);
        if (armorPiece.isEmpty() || !(armorPiece.getItem() instanceof KotdCrystalArmorItem)) {
            closePopup();
            return;
        }

        int popupX = width / 2 - 100;
        int popupY = height / 2 - 50;

        this.addRenderableWidget(new PopupTitle(
                popupX + 20, popupY + 10, 160, 10,
                Component.translatable("config.kotd.adjust_%s".formatted(currentPopup.name().toLowerCase()))
        ));

        // Slider für jedes Attribut des Rüstungsteils
        List<ArmorAttribute> attributes = slotAttributes.get(getSlotForPopupType(currentPopup));
        if (attributes != null) {
            int yOffset = 30;
            for (ArmorAttribute attr : attributes) {
                double currentValue = getCurrentAttributeValue(armorPiece, attr);
                this.addRenderableWidget(new ArmorSlider(
                        popupX + 20, popupY + yOffset, 160, 20,
                        currentValue, armorPiece, attr
                ));
                yOffset += 25;
            }
        }

        this.addRenderableWidget(new CloseButton(
                popupX + 150, popupY + 150, 40, 15,
                Component.translatable("gui.kotd.close")));

        this.addRenderableWidget(new BlockingOverlay());
        this.addRenderableWidget(new PopupBackground(popupX, popupY, 200, 180));
    }

    private EquipmentSlot getSlotForPopupType(PopupType type) {
        return switch (type) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
            default -> null;
        };
    }
    private void initAttributePopup() {
        // Implementierung für individuelle Attribut-Popups
    }
    private ItemStack getArmorPiece(PopupType type) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return ItemStack.EMPTY;

        return switch (type) {
            case HELMET -> player.getItemBySlot(EquipmentSlot.HEAD);
            case CHESTPLATE -> player.getItemBySlot(EquipmentSlot.CHEST);
            case LEGGINGS -> player.getItemBySlot(EquipmentSlot.LEGS);
            case BOOTS -> player.getItemBySlot(EquipmentSlot.FEET);
            default -> ItemStack.EMPTY;
        };
    }
    private ItemStack getMainHand() {
        Player player = Minecraft.getInstance().player;
        return player != null ? player.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY;
    }
    private double getCurrentAttributeValue(ItemStack armorPiece, ArmorAttribute attribute) {
        CustomData data = armorPiece.get(DataComponents.CUSTOM_DATA);
        if (data == null) return attribute.baseValue() / 100; // Umrechnung von % zu Faktor

        CompoundTag tag = data.copyTag();
        String key = "attr_%s".formatted(attribute.configKey());
        return tag.contains(key) ? tag.getDouble(key) : attribute.baseValue() / 100;
    }


    // Specialized widget classes for better organization
    private class BlockingOverlay extends AbstractWidget {
        public BlockingOverlay() {
            super(0, 0, ConfigScreen.this.width, ConfigScreen.this.height, Component.empty());
        }

        @Override
        public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.fill(0, 0, width, height, 0x80000000);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            // Keine Klicks blockieren, da Hauptbuttons bereits deaktiviert sind
            return false;
        }


        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

        }
    }
    private static class PopupBackground extends AbstractWidget {
        public PopupBackground(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty());
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // Render background
            guiGraphics.fill(this.getX(), this.getY(),
                    this.getX() + this.width,
                    this.getY() + this.height,
                    0xCC000000);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return false; // Allow clicks to pass through to underlying widgets
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

        }
    }
    private class PopupTitle extends AbstractWidget {
        public PopupTitle(int x, int y, int width, int height, Component message) {
            super(x, y, width, height, message);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            float progress = Math.min(1, popupAnimation / (float)POPUP_ANIMATION_TIME);
            int alpha = (int)(255 * progress);
            guiGraphics.drawCenteredString(font, this.getMessage(),
                    this.getX() + this.width / 2, this.getY(), 0xFFFFFF | (alpha << 24));
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        }
    }
    private static class ArmorSlider extends AbstractSliderButton {
        private final ItemStack armorPiece;
        private final ArmorAttribute attribute;

        public ArmorSlider(int x, int y, int width, int height,
                           double value, ItemStack armorPiece, ArmorAttribute attribute) {
            super(x, y, width, height, Component.empty(), value);
            this.armorPiece = armorPiece;
            this.attribute = attribute;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Component.literal("%s: %s".formatted(attribute.displayName().getString(), String.format("%.0f%%", this.value * 100))));
        }

        @Override
        protected void applyValue() {
            if (armorPiece.getItem() instanceof KotdCrystalArmorItem armorItem) {
                CustomData data = armorPiece.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = data.copyTag();
                tag.putDouble("attr_%s".formatted(attribute.configKey()), this.value);
                armorPiece.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
        }
    }
    private class CloseButton extends Button {
        public CloseButton(int x, int y, int width, int height, Component message) {
            //noinspection unused
            super(x, y, width, height, message, button -> closePopup(), DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 100); // Slight z-offset is enough

            // Custom button rendering
            int color = this.isHovered ? 0xFF5555FF : 0xFF3333DD;
            guiGraphics.fill(this.getX(), this.getY(),
                    this.getX() + this.width,
                    this.getY() + this.height,
                    color);
            guiGraphics.drawCenteredString(font, getMessage(),
                    this.getX() + this.width / 2,
                    this.getY() + (this.height - 8) / 2,
                    0xFFFFFF);

            guiGraphics.pose().popPose();
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    private void openPopup(PopupType type) {
        currentPopup = type;
        this.renderables.forEach(widget -> {
            if (widget instanceof AbstractWidget abstractWidget) {
                abstractWidget.visible = false;
                abstractWidget.active = false;
            }
        });
        this.rebuildWidgets();
    }
    private void closePopup() {
        currentPopup = PopupType.NONE;
        this.renderables.forEach(widget -> {
            if (widget instanceof AbstractWidget abstractWidget) {
                abstractWidget.visible = true;
                abstractWidget.active = true;
            }
        });
        this.rebuildWidgets();
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Completely disable the default blur effect
        if (Objects.requireNonNull(this.minecraft).level == null) {
            guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            guiGraphics.fillGradient(0, 0, this.width, this.height, 0x6B000000, 0x6B000000);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;
        guiGraphics.blit(BACKGROUND, left, top, 0, 0, WIDTH, HEIGHT);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, top + 10, 0x00FF00);

        List<Renderable> reversedWidgets = new ArrayList<>(this.renderables);
        Collections.reverse(reversedWidgets);
        reversedWidgets.forEach(widget -> {
            if (widget instanceof AbstractWidget abstractWidget) {
                abstractWidget.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        });
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && currentPopup != PopupType.NONE) {
            closePopup();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public void tick() {
        if (currentPopup != PopupType.NONE && popupAnimation < POPUP_ANIMATION_TIME) {
            popupAnimation++;
        } else if (currentPopup == PopupType.NONE && popupAnimation > 0) {
            popupAnimation--;
        }
    }
    public static boolean hasArmorPiece(Player player) {
        if (player == null) return false;

        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof KotdCrystalArmorItem) {
                return true;
            }
        }
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof UltimateKotdBlade) {
                return true;
            }
        }
        return false;
    }
}