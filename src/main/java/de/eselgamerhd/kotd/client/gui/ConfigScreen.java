package de.eselgamerhd.kotd.client.gui;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor.KotdCrystalArmorItem;
import de.eselgamerhd.kotd.common.items.item.ultimateKotdBlade.UltimateKotdBlade;
import de.eselgamerhd.kotd.network.UpdateArmorAttributesPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class ConfigScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/gui/backgrounds/config_screen_background.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;

    private enum PopupType { NONE, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SWORD }
    private PopupType currentPopup = PopupType.NONE;
    private int popupAnimation = 0;
    private static final int POPUP_ANIMATION_TIME = 5;
    private ItemStack pendingChanges;

    public ConfigScreen() {
        super(Component.translatable("gui.kotd.attribute_menu"));
    }

    public record Attribute(
            Component displayName,
            Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
            double baseValue,
            EquipmentSlot slot,
            String configKey
    ) {}
    private final Map<EquipmentSlot, List<Attribute>> slotAttributes = new HashMap<>();
    private void initSlotAttributes() {
        slotAttributes.put(
                EquipmentSlot.HEAD, List.of(
                        new Attribute(
                                Component.translatable("attribute.kotd.efficiency"),
                                Attributes.MINING_EFFICIENCY,
                                50.0,
                                EquipmentSlot.HEAD,
                                "efficiency"
                        )
                ));

        slotAttributes.put(
                EquipmentSlot.CHEST, List.of(
                        new Attribute(
                                Component.translatable("attribute.kotd.health"),
                                Attributes.MAX_HEALTH,
                                50.0,
                                EquipmentSlot.CHEST,
                                "health_boost"
                        ),
                        new Attribute(
                                Component.translatable("attribute.kotd.interaction_range"),
                                Attributes.BLOCK_INTERACTION_RANGE,
                                100.0,
                                EquipmentSlot.CHEST,
                                "interaction_range"
                        )
                ));

        slotAttributes.put(
                EquipmentSlot.LEGS, List.of(
                        new Attribute(
                                Component.translatable("attribute.kotd.movement"),
                                Attributes.MOVEMENT_SPEED,
                                1.0,
                                EquipmentSlot.LEGS,
                                "movement_speed"
                        ),
                        new Attribute(
                                Component.translatable("attribute.kotd.swim_movement"),
                                NeoForgeMod.SWIM_SPEED,
                                10.0,
                                EquipmentSlot.LEGS,
                                "swim_speed"
                        )
                ));
        slotAttributes.put(
                EquipmentSlot.FEET, List.of(
                        new Attribute(
                                Component.translatable("attribute.kotd.step"),
                                Attributes.STEP_HEIGHT,
                                10.0,
                                EquipmentSlot.FEET,
                                "step_height"
                        ),
                        new Attribute(
                                Component.translatable("attribute.kotd.jump_strength"),
                                Attributes.JUMP_STRENGTH,
                                1.0,
                                EquipmentSlot.FEET,
                                "jump_strength"
                        )
                ));
    }
    private final List<Attribute> swordAttributes = List.of(
            new Attribute(
                    Component.translatable("attribute.kotd.attack_damage"),
                    Attributes.ATTACK_DAMAGE,
                    145.0,
                    EquipmentSlot.MAINHAND,
                    "attack_damage"
            )
    );

    @Override
    protected void init() {
        super.init();
        clearWidgets();

        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;

        initSlotAttributes();

        if (currentPopup == PopupType.SWORD) {
            initMainHandPopup();
        } else if (currentPopup != PopupType.NONE) {
            initArmorPopup();
        }

        addMainButtons(left, top);
    }
    private void addMainButtons(int left, int top) {
        if (currentPopup == PopupType.NONE) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            // Helmet Button
            ConfigButton helmetButton = ConfigButton.builder(
                            Component.translatable("config.kotd.helmet"),
                            button -> openPopup(PopupType.HELMET))
                    .bounds(left + 20, top + 40, 59, 15)
                    .build();
            helmetButton.active = isArmorEquipped(EquipmentSlot.HEAD);
            if (!helmetButton.active) {
                helmetButton.setTooltip(Tooltip.create(Component.translatable("tooltip.kotd.equip_helmet")));
            }
            this.addRenderableWidget(helmetButton);

            // Chestplate Button
            ConfigButton chestplateButton = ConfigButton.builder(
                            Component.translatable("config.kotd.chestplate"),
                            button -> openPopup(PopupType.CHESTPLATE))
                    .bounds(left + 20, top + 60, 59, 15)
                    .build();
            chestplateButton.active = isArmorEquipped(EquipmentSlot.CHEST);
            if (!chestplateButton.active) {
                chestplateButton.setTooltip(Tooltip.create(Component.translatable("tooltip.kotd.equip_chestplate")));
            }
            this.addRenderableWidget(chestplateButton);

            // Leggings Button
            ConfigButton leggingsButton = ConfigButton.builder(
                            Component.translatable("config.kotd.leggings"),
                            button -> openPopup(PopupType.LEGGINGS))
                    .bounds(left + 20, top + 80, 59, 15)
                    .build();
            leggingsButton.active = isArmorEquipped(EquipmentSlot.LEGS);
            if (!leggingsButton.active) {
                leggingsButton.setTooltip(Tooltip.create(Component.translatable("tooltip.kotd.equip_leggings")));
            }
            this.addRenderableWidget(leggingsButton);

            // Boots Button
            ConfigButton bootsButton = ConfigButton.builder(
                            Component.translatable("config.kotd.boots"),
                            button -> openPopup(PopupType.BOOTS))
                    .bounds(left + 20, top + 100, 59, 15)
                    .build();
            bootsButton.active = isArmorEquipped(EquipmentSlot.FEET);
            if (!bootsButton.active) {
                bootsButton.setTooltip(Tooltip.create(Component.translatable("tooltip.kotd.equip_boots")));
            }
            this.addRenderableWidget(bootsButton);

            // Sword Button
            ConfigButton swordButton = ConfigButton.builder(
                            Component.translatable("config.kotd.sword"),
                            button -> openPopup(PopupType.SWORD))
                    .bounds(left + 175, top + 40, 59, 15)
                    .build();
            swordButton.active = hasSwordEquipped();
            if (!swordButton.active) {
                swordButton.setTooltip(Tooltip.create(Component.translatable("tooltip.kotd.equip_sword")));
            }
            this.addRenderableWidget(swordButton);

            this.addRenderableWidget(Button.builder(
                            Component.translatable("gui.kotd.close"),
                            button -> this.onClose())
                    .bounds(left + 230, top + 10, 12, 12)
                    .build());
        }
    }
    private void initArmorPopup() {
        pendingChanges = getArmorPiece(currentPopup).copy();
        ItemStack armorPiece = getArmorPiece(currentPopup);
        if (armorPiece.isEmpty() || !(armorPiece.getItem() instanceof KotdCrystalArmorItem)) {
            closePopup();
            return;
        }

        int popupX = width / 2 - 100;
        int popupY = height / 2 - 85;


        List<Attribute> attributes = slotAttributes.get(getSlotForPopupType(currentPopup));
        if (attributes != null) {
            int yOffset = 30;
            for (Attribute attr : attributes) {
                double currentValue = getCurrentAttributeValue(armorPiece, attr);
                this.addRenderableWidget(new AttributeSlider(
                        popupX + 20, popupY + yOffset, 160, 20,
                        currentValue, attr
                ));
                yOffset += 25;
            }
        }
        this.addRenderableWidget(new SaveButton(
                popupX + 140, popupY + 150, 40, 15,
                Component.translatable("gui.kotd.save"),
                button -> saveChanges()
        ));
        this.addRenderableWidget(new CloseButton(
                popupX + 170, popupY + 10, 12, 12,
                Component.translatable("gui.kotd.close")));

        this.addRenderableWidget(new PopupTitle(
                popupX + 20, popupY + 10, 160, 10,
                Component.translatable("config.kotd.adjust_%s".formatted(currentPopup.name().toLowerCase()))
        ));
        this.addRenderableWidget(new BlockingOverlay());
        this.addRenderableWidget(new PopupBackground(popupX, popupY, 200, 180));
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
    private void initMainHandPopup() {
        pendingChanges = Objects.requireNonNull(Minecraft.getInstance().player).getMainHandItem().copy();
        ItemStack sword = pendingChanges;

        if (sword.isEmpty() || !(sword.getItem() instanceof UltimateKotdBlade)) {
            closePopup();
            return;
        }

        int popupX = width / 2 - 100;
        int popupY = height / 2 - 85;

        int yOffset = 30;
        for (Attribute attr : swordAttributes) {
            double currentValue = getCurrentAttributeValue(sword, attr);
            this.addRenderableWidget(new AttributeSlider(
                    popupX + 20, popupY + yOffset, 160, 20,
                    currentValue, attr
            ));
            yOffset += 25;
        }

        this.addRenderableWidget(new SaveButton(
                popupX + 140, popupY + 150, 40, 15,
                Component.translatable("gui.kotd.save"),
                button -> saveChanges()
        ));
        this.addRenderableWidget(new CloseButton(
                popupX + 170, popupY + 10, 12, 12,
                Component.translatable("gui.kotd.close")));

        this.addRenderableWidget(new PopupTitle(
                popupX + 20, popupY + 10, 160, 10,
                Component.translatable("config.kotd.adjust_sword")));

        this.addRenderableWidget(new BlockingOverlay());
        this.addRenderableWidget(new PopupBackground(popupX, popupY, 200, 180));
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
    private class AttributeSlider extends AbstractSliderButton {
        private final Attribute attribute;

        public AttributeSlider(int x, int y, int width, int height,
                               double normalizedValue, Attribute attribute) {
            super(x, y, width, height, Component.empty(), normalizedValue);
            this.attribute = attribute;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            double realValue = this.value * attribute.baseValue(); // Skalierter Wert
            this.setMessage(Component.literal("%s: %.2f".formatted(
                    attribute.displayName().getString(), realValue
            )));
        }

        @Override
        protected void applyValue() {
            if (pendingChanges == null) return;

            CustomData data = pendingChanges.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = data.copyTag();
            double scaledValue = this.value * attribute.baseValue();
            String key = "attr_%s".formatted(attribute.configKey());
            tag.putDouble(key, scaledValue);

            pendingChanges.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            updateMessage();
        }
    }
    private class SaveButton extends Button {
        public SaveButton(int x, int y, int width, int height, Component message, OnPress onPress) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // Custom Rendering
            int color = this.isHovered ? 0xFF55FF55 : 0xFF33DD33;
            guiGraphics.fill(this.getX(), this.getY(),
                    this.getX() + this.width,
                    this.getY() + this.height,
                    color);

            guiGraphics.drawCenteredString(font, getMessage(),
                    this.getX() + this.width / 2,
                    this.getY() + (this.height - 8) / 2,
                    0xFFFFFF);
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
        Minecraft mc = Minecraft.getInstance();
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && currentPopup != PopupType.NONE) {
            closePopup();
            return true;
        }
        if (keyCode == mc.options.keyInventory.getKey().getValue()) {
            if (currentPopup != PopupType.NONE) {
                closePopup();
            } else {
                onClose();
            }
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

    private EquipmentSlot getSlotForPopupType(PopupType type) {
        return switch (type) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
            default -> null;
        };
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
    private double getCurrentAttributeValue(ItemStack armorPiece, Attribute attribute) {
        CustomData data = armorPiece.get(DataComponents.CUSTOM_DATA);
        if (data == null) return 0.0;

        CompoundTag tag = data.copyTag();
        String key = "attr_%s".formatted(attribute.configKey());

        double stored = tag.contains(key) ? tag.getDouble(key) : 0.0;
        return stored / attribute.baseValue(); // Normiert auf 0–1 für den Slider
    }
    private boolean hasSwordEquipped() {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;

        return player.getMainHandItem().getItem() instanceof UltimateKotdBlade ||
                player.getOffhandItem().getItem() instanceof UltimateKotdBlade;
    }
    private boolean isArmorEquipped(EquipmentSlot slot) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;

        ItemStack armorStack = player.getItemBySlot(slot);
        return armorStack.getItem() instanceof KotdCrystalArmorItem;
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
    private void saveChanges() {
        Player player = Minecraft.getInstance().player;
        if (pendingChanges == null || player == null) return;

        if (currentPopup == PopupType.SWORD) {
            PacketDistributor.sendToServer(new UpdateArmorAttributesPacket(null, pendingChanges, true));
            closePopup();
            return;
        }

        EquipmentSlot slot = getSlotForPopupType(currentPopup);
        PacketDistributor.sendToServer(new UpdateArmorAttributesPacket(slot, pendingChanges, false));
        closePopup();
    }
}