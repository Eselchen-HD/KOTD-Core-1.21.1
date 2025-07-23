package de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor;

import com.google.common.collect.ImmutableMap;
import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.init.IEnergyContainer;
import de.eselgamerhd.kotd.common.init.KotdArmorMaterials;
import de.eselgamerhd.kotd.common.init.KotdItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class KotdCrystalArmorItem extends ArmorItem implements GeoItem, IEnergyContainer {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP = ImmutableMap.of(KotdArmorMaterials.KOTD_ARMOR_MATERIAL,
            List.of(
                    new MobEffectInstance(MobEffects.REGENERATION, 100, 1, false, false,false, null),
                    new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 1, false, false, false, null)
            ));

    @Override
    public boolean canWalkOnPowderedSnow(@NotNull ItemStack stack, @NotNull LivingEntity wearer){return stack.is(KotdItems.KOTD_BOOTS.get());}

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {return true;}

    @Override
    public boolean makesPiglinsNeutral(@NotNull ItemStack stack, @NotNull LivingEntity wearer) {return true;}

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public KotdCrystalArmorItem(Type type, Properties settings) {
        super(KotdArmorMaterials.KOTD_ARMOR_MATERIAL, type, settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    public static class KotdCrystalArmorRenderer extends GeoArmorRenderer<KotdCrystalArmorItem> {public KotdCrystalArmorRenderer() {super(new KotdCrystalArmorModel());}}

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private KotdCrystalArmorRenderer renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(
                    @Nullable T livingEntity,
                    ItemStack itemStack,
                    @Nullable EquipmentSlot equipmentSlot,
                    @Nullable HumanoidModel<T> original) {
                if (this.renderer == null) {
                    this.renderer = new KotdCrystalArmorRenderer();
                }
                return this.renderer;
            }
        });
    }
    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(this.type.getSlot());
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        addBaseAttributes(builder, group);
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();

        addAttribute(builder, group, Attributes.OXYGEN_BONUS, tag, "oxygen_bonus", 0.0);
        addAttribute(builder, group, Attributes.MAX_HEALTH, tag, "health_boost", 0.0);
        addAttribute(builder, group, Attributes.FLYING_SPEED, tag, "fly_speed", 0.0);
        addAttribute(builder, group, Attributes.MOVEMENT_SPEED, tag, "movement_speed", 0.0);
        addAttribute(builder, group, Attributes.SNEAKING_SPEED, tag, "movement_speed", 0.0);
        addAttribute(builder, group, NeoForgeMod.SWIM_SPEED, tag, "swim_speed", 0.0);
        addAttribute(builder, group, Attributes.JUMP_STRENGTH, tag, "jump_strength", 0.0);
        addAttribute(builder, group, Attributes.STEP_HEIGHT, tag, "step_height", 0.0);
        addAttribute(builder, group, Attributes.MINING_EFFICIENCY, tag, "efficiency", 0.0);
        addAttribute(builder, group, NeoForgeMod.CREATIVE_FLIGHT, tag, "fly", 0.25);

        return builder.build();
    }
    private void addBaseAttributes(ItemAttributeModifiers.Builder builder, EquipmentSlotGroup group) {
        int defense = this.getMaterial().value().getDefense(this.type);
        float toughness = this.getMaterial().value().toughness();
        float knockbackResistance = this.getMaterial().value().knockbackResistance();

        builder.add(Attributes.ARMOR, createModifier("armor." + this.type.getName(), defense), group);
        builder.add(Attributes.ARMOR_TOUGHNESS, createModifier("armor." + this.type.getName(), toughness), group);

        if (knockbackResistance > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, createModifier("armor." + this.type.getName(), knockbackResistance), group);
        }
    }

    private void addAttribute(ItemAttributeModifiers.Builder builder,
                              EquipmentSlotGroup slot,
                              Holder<Attribute> attribute,
                              CompoundTag tag,
                              String key,
                              double fallbackValue) {

        double value = tag.contains("attr_%s".formatted(key)) ?
                tag.getDouble("attr_%s".formatted(key)) :
                fallbackValue;
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, key);
        builder.add(attribute,
                new AttributeModifier(id, value, AttributeModifier.Operation.ADD_VALUE),
                slot);
    }
    private AttributeModifier createModifier(String name, double value) {
        return new AttributeModifier(
                ResourceLocation.withDefaultNamespace(name),
                value,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            } else {
                MATERIAL_TO_EFFECT_MAP.get(KotdArmorMaterials.KOTD_ARMOR_MATERIAL).forEach(effect -> player.removeEffect(effect.getEffect()));
            }
        }
    }

    private void evaluateArmorEffects(Player player) {
        MATERIAL_TO_EFFECT_MAP.forEach((material, effects) -> {
            if (hasPlayerCorrectArmorOn(material, player)) {
                addEffectToPlayer(player, effects);
            }
        });
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> effects) {
        effects.forEach(effect -> {
            if (!player.hasEffect(effect.getEffect())) {
                player.addEffect(new MobEffectInstance(effect));
            }
        });
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armorItem)) {
                return false;
            }
            if (!armorItem.getMaterial().equals(material)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "armor_controller", 20, state -> {
            Entity entity = state.getData(DataTickets.ENTITY);
            if (entity instanceof ArmorStand) {return PlayState.CONTINUE;}
            if (!(entity instanceof LivingEntity livingEntity)) {return PlayState.STOP;}

            int pieceCount = 0;
            boolean[] hasPieces = new boolean[4];

            for (ItemStack stack : livingEntity.getArmorSlots()) {
                if (stack.getItem() instanceof KotdCrystalArmorItem) {
                    pieceCount++;
                    EquipmentSlot slot = ((ArmorItem)stack.getItem()).getType().getSlot();
                    hasPieces[slot.getIndex()] = true;
                }
            }

            if (pieceCount == 4 && hasPieces[0] && hasPieces[1] && hasPieces[2] && hasPieces[3]) {
                state.setAnimation(RawAnimation.begin().thenLoop("idle"));
                return PlayState.CONTINUE;
            }else if (pieceCount > 0) {
                state.setAnimation(RawAnimation.begin().thenLoop("partial_set"));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && player.getItemBySlot(slot).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}
}