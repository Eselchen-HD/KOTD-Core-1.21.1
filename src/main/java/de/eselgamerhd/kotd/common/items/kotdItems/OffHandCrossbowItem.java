package de.eselgamerhd.kotd.common.items.kotdItems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static net.minecraft.network.chat.Component.*;

public class OffHandCrossbowItem extends CrossbowItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final float damage;
    private final int cooldownTicks;

    public OffHandCrossbowItem(Properties properties, float damage, int cooldownTicks) {
        super(properties.durability(465));
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.kotd.OffHandCrossbow"));
        tooltip.add(empty());
        tooltip.add(literal(damage + " Attack Damage").withStyle(ChatFormatting.DARK_GREEN));
        tooltip.add(literal((cooldownTicks / 20.0) + "s Use time").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player && player.getOffhandItem() == stack) {
            super.releaseUsing(stack, level, entity, timeLeft);
            player.getCooldowns().addCooldown(this, cooldownTicks);
        }
    }

    @SubscribeEvent
    public static void onUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack usedItem = event.getItem();
        if (usedItem.getItem() instanceof OffHandCrossbowItem &&
                player.getMainHandItem() == usedItem) {
            event.setCanceled(true);
            player.sendSystemMessage(literal("This crossbow must be used in your off-hand!").withStyle(ChatFormatting.RED));
            player.displayClientMessage(literal("This crossbow must be used in your off-hand!").withStyle(ChatFormatting.UNDERLINE, ChatFormatting.RED), true);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Hier Animationen registrieren (z.B. für Laden/Schießen)
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}