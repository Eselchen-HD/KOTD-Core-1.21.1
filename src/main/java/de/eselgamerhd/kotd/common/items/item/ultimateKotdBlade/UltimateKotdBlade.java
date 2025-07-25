package de.eselgamerhd.kotd.common.items.item.ultimateKotdBlade;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.entity.laser_beam.LaserBeam;
import de.eselgamerhd.kotd.common.init.KotdEntities;
import de.eselgamerhd.kotd.common.init.KotdTiers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static de.eselgamerhd.kotd.common.init.KotdTiers.StateTier.ULTIMATE;
import static net.minecraft.network.chat.Component.translatable;

public class UltimateKotdBlade extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public UltimateKotdBlade(Tier tier, Properties properties) {
        super(tier, properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    @Override
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
        stack.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        super.onCraftedBy(stack, level, player);
    }
    private KotdTiers.StateTier getCurrentTier(ItemStack ignoredStack) {
        return ULTIMATE;
    }
    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(EquipmentSlot.MAINHAND);

        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();

        addAttribute(builder, group, Attributes.ATTACK_DAMAGE, tag, "attack_damage", 140.0);
        addAttribute(builder, group, Attributes.ATTACK_SPEED, tag, "attack_speed", 5.0);
        addAttribute(builder, group, Attributes.ENTITY_INTERACTION_RANGE, tag, "range", 8.0);

        return builder.build();
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
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel){
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return null;
            });
            triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "attack_controller", "attack");

            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(stack);
            }

            player.getCooldowns().addCooldown(this, 120);
            try {
                future.get();
                Vec3 eyePos = player.getEyePosition(1.0F);
                Vec3 lookVec = player.getLookAngle();
                double maxDistanceFromBeam = 1.0D; // Radius des Strahls
                double beamLength = 20.0D;
                Vec3 end = eyePos.add(lookVec.scale(beamLength));
                AABB beamBox = new AABB(eyePos, end).inflate(maxDistanceFromBeam);

                List<Entity> entities = level.getEntities(player, beamBox, e -> {
                    if (!(e instanceof LivingEntity living)) return false;
                    if (!living.isAlive()) return false;

                    // Vektor vom Startpunkt zur Entity
                    Vec3 toEntity = e.getBoundingBox().getCenter().subtract(eyePos);
                    double projLen = toEntity.dot(lookVec);

                    if (projLen < 0 || projLen > beamLength) return false;

                    // Abstand zur Strahlachse (senkrechter Abstand)
                    Vec3 closestPointOnBeam = eyePos.add(lookVec.scale(projLen));
                    double distanceToBeam = e.getBoundingBox().getCenter().distanceTo(closestPointOnBeam);

                    return distanceToBeam <= maxDistanceFromBeam;
                });

                for (Entity entity : entities) {
                    entity.hurt(level.damageSources().playerAttack(player), 200.0F * (float) getCurrentTier(stack).attributeMultiplier);
                }

                double x = player.getX() + lookVec.x;
                double y = player.getY() + lookVec.y + 1.0D;
                double z = player.getZ() + lookVec.z;

                // Erstelle den Beam
                LaserBeam beam = new LaserBeam(KotdEntities.LASER_BEAM.get(), level);
                beam.setPos(x, y, z);
                beam.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
                level.addFreshEntity(beam);

                player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            } catch (InterruptedException | ExecutionException e) {
                System.err.printf("Error during async operation: %s%n", e.getMessage());
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ukb_controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("attack")));
    }
    private PlayState predicate(@NotNull AnimationState<UltimateKotdBlade> AnimationState) {
        AnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }
    public int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000;
        Green = (Green << 8) & 0x0000FF00;
        Blue = Blue & 0x000000FF;

        return 0xFF000000 | Red | Green | Blue;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if(Screen.hasShiftDown()) {
        tooltip.add(translatable("tooltip.kotd.ultimate_kotd_blade.shift_down"));
        } else {
            tooltip.add(translatable("tooltip.kotd.ultimate_kotd_blade").withColor(getIntFromColor(50,170,225)));
        }
    }

    public static class UKBladeRenderer extends GeoItemRenderer<UltimateKotdBlade> { public UKBladeRenderer() {super(new UltimateKotdBladeModel());}}

    @Override
    public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private UKBladeRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new UKBladeRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}
}
