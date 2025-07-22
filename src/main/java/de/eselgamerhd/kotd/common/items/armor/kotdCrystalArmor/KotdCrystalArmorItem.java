package de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor;

import com.google.common.collect.ImmutableMap;
import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.init.IEnergyContainer;
import de.eselgamerhd.kotd.common.init.KotdArmorMaterials;
import de.eselgamerhd.kotd.common.init.KotdItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
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

    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;
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
        this.capacity = 1000000;
        this.maxReceive = 10000;
        this.maxExtract = 10000;
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

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public int getCapacity() {
        return capacity;
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
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(this.type.getSlot());
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        addBaseAttributes(builder, group);

        if (this.type == Type.HELMET) {
            addAttribute(builder, group, Attributes.OXYGEN_BONUS, 10.0, "oxygen_bonus");
        }
        if (this.type == Type.CHESTPLATE) {
            addAttribute(builder, group, Attributes.MAX_HEALTH, 30.0, "health_boost");
            addAttribute(builder, group, Attributes.MINING_EFFICIENCY, 15.0*10, "mining_efficiency_boost");
        }
        if (this.type == Type.LEGGINGS) {
            addAttribute(builder, group, Attributes.MOVEMENT_SPEED, 0.1*0.5, "movement_speed_boost");
            addAttribute(builder, group, Attributes.SNEAKING_SPEED, 0.2, "sneaking_speed_boost");
            addAttribute(builder, group, Attributes.WATER_MOVEMENT_EFFICIENCY, 2.0, "water_movement_boost");
        }
        if (this.type == Type.BOOTS) {
            addAttribute(builder, group, Attributes.SAFE_FALL_DISTANCE, 999.0, "safe_fall_distance");
            addAttribute(builder, group, Attributes.JUMP_STRENGTH, 0.2, "jump_strength_boost");
            addAttribute(builder, group, Attributes.STEP_HEIGHT, 1.0, "step_height");
        }

        addAttribute(builder, group, NeoForgeMod.CREATIVE_FLIGHT, 0.25, "creative_flight");
        addAttribute(builder, group, Attributes.ATTACK_DAMAGE, 3.3, "damage_boost");
        addAttribute(builder, group, Attributes.ATTACK_SPEED, 3.3, "attack_speed_boost");
        addAttribute(builder, group, Attributes.LUCK, 10.0, "luck_boost");

        return builder.build();
    }

    @SuppressWarnings("StringTemplateMigration")
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
    private void addAttribute(ItemAttributeModifiers.Builder builder, EquipmentSlotGroup group, Holder<Attribute> attribute, double value, String name) {
        builder.add(attribute,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, name),
                        value,
                        AttributeModifier.Operation.ADD_VALUE
                ), group);
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