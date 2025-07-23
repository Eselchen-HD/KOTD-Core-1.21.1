package de.eselgamerhd.kotd.common.items.item.Scythe;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.network.chat.Component.*;

public class MertScythe extends HoeItem implements GeoItem {
    private final float minDamage;
    private final float maxDamage;
    private final Random random = new Random();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MertScythe(Tier tier, Properties properties, float minDamage, float maxDamage) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(translatable("tooltip.kotd.Scythe"));
        tooltip.add(empty());
    }

    public final float radius = 1.5f;  // Radius of the half-circle
    public final float width = 3.0f;   // Width of the attack area
    public final float height = 3.0f;  // Height of the attack area
    public final float depth = 3.0f;   // Depth of the attack area
    public final float distance = 0.5f; // Distance in front of the player

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(stack, entity) - timeLeft;
            if (i < 20) return; // Require a minimum charge time

            if (!level.isClientSide) {
                Vec3 lookVec = player.getLookAngle();
                Vec3 playerPos = player.position().add(0, player.getEyeHeight(), 0);
                Vec3 attackCenter = playerPos.add(lookVec.scale(distance + depth / 2));
                AABB boundingBox = new AABB(
                        attackCenter.x - width / 2, attackCenter.y - height / 2, attackCenter.z - width / 2,
                        attackCenter.x + width / 2, attackCenter.y + height / 2, attackCenter.z + width / 2
                );

                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, boundingBox,
                        e -> e != player && e.isPickable());

                player.swing(InteractionHand.MAIN_HAND, true);
                int entitiesHit = 0;
                for (LivingEntity target : entities) {
                    Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
                    Vec3 toTarget = targetPos.subtract(playerPos);

                    // Check if the entity is within the half-circle area
                    if (isInAttackArea(toTarget, lookVec)) {
                        BlockHitResult blockHit = level.clip(new ClipContext(playerPos, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

                        if (blockHit.getType() == HitResult.Type.MISS) {
                            scytheHits(player, target);
                            entitiesHit++;
                        }
                    }
                }

                if (entitiesHit > 0) {
                    int durabilityDamage = entitiesHit * 2;
                    stack.hurtAndBreak(durabilityDamage, player, EquipmentSlot.MAINHAND);
                    player.getCooldowns().addCooldown(this, 40);

                } else {
                    player.getCooldowns().addCooldown(this, 10);
                }
            }
        }
    }

    public void scytheHits(Player player, LivingEntity target) {
        float damage = minDamage + random.nextFloat() * (maxDamage - minDamage);
        damage = Math.round(damage * 10.0f) / 10.0f;
        target.hurt(player.damageSources().playerAttack(player), damage);
    }

    public boolean isInAttackArea(Vec3 toTarget, Vec3 lookVec) {
        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = lookVec.cross(up).normalize();
        Vec3 adjustedUp = right.cross(lookVec).normalize();

        double forwardProject = toTarget.dot(lookVec);
        double rightProject = toTarget.dot(right);
        double upProject = toTarget.dot(adjustedUp);

        boolean inRadius = Math.sqrt(rightProject * rightProject + upProject * upProject) <= radius;
        boolean inFront = forwardProject >= distance && forwardProject <= distance + depth;
        boolean inHeight = Math.abs(upProject) <= height / 2;

        return inRadius && inFront && inHeight;
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack, @NotNull LivingEntity pEntity) {
        return 72000;
    }
    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static class ScytheRenderer extends GeoItemRenderer<MertScythe> { public ScytheRenderer() {super(new ScytheModel());}}

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private ScytheRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new ScytheRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
