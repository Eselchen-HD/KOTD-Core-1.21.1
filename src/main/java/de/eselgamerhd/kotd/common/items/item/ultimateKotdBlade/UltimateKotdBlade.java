package de.eselgamerhd.kotd.common.items.item.ultimateKotdBlade;

import de.eselgamerhd.kotd.common.entity.laser_beam.LaserBeam;
import de.eselgamerhd.kotd.common.init.ModEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
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

import static net.minecraft.network.chat.Component.translatable;

public class UltimateKotdBlade extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public UltimateKotdBlade(Tier tier, Properties properties) {
        super(tier, properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel){
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return null;
            });
            triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "attack_controller", "attack");


            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(stack);
            }

            try {
                future.get();
                Vec3 eyePos = player.getEyePosition(1.0F);
                Vec3 lookVec = player.getLookAngle();
                double maxDistanceFromBeam = 1.75D; // Radius des Strahls
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
                    entity.hurt(level.damageSources().playerAttack(player), 180.0F);
                }

                double x = player.getX();
                double y = player.getY() - player.getEyeHeight() - 2.75D;
                double z = player.getZ();

                // Erstelle den Beam
                LaserBeam beam = new LaserBeam(ModEntities.LASER_BEAM.get(), level);
                beam.setPos(x, y, z);
                beam.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
                level.addFreshEntity(beam);

                player.getCooldowns().addCooldown(this, 100); // 5 Sekunden Cooldown
                player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error during async operation: " + e.getMessage());
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ukb_controller", 4, this::predicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 4, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("attack")));
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if(Screen.hasShiftDown()) {
        tooltip.add(translatable("tooltip.kotd.ultimate_kotd_blade.shift_down"));
        } else {
            tooltip.add(translatable("tooltip.kotd.ultimate_kotd_blade"));
        }
    }

    private PlayState predicate(@NotNull AnimationState<UltimateKotdBlade> AnimationState) {
        AnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }

    public static class UKBRenderer extends GeoItemRenderer<UltimateKotdBlade> { public UKBRenderer() {super(new UltimateKotdBladeModel());}}

    @Override
    public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private UKBRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new UKBRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}
}
