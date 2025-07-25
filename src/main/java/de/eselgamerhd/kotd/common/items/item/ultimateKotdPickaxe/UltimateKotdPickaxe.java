package de.eselgamerhd.kotd.common.items.item.ultimateKotdPickaxe;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.network.chat.Component.translatable;

public class UltimateKotdPickaxe extends PickaxeItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public UltimateKotdPickaxe(Tier p_42961_, Properties p_42964_) {
        super(p_42961_, p_42964_);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        float baseSpeed = super.getDestroySpeed(stack, state);

        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        double multiplier = data.copyTag().getDouble("dig_speed_multiplier");
        if (multiplier <= 0) multiplier = 1.0;

        return (float) (baseSpeed * multiplier);
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
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
        stack.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        super.onCraftedBy(stack, level, player);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if(Screen.hasShiftDown()) {
            tooltip.add(translatable("tooltip.kotd.ultimate_kotd_pickaxe.shift_down").withColor(getIntFromColor(50,170,225)));
        } else {
            tooltip.add(translatable("tooltip.kotd.ultimate_kotd_pickaxe").withColor(getIntFromColor(50,170,225)));
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ukpickaxe_controller", 0, this::predicate));
    }
    private PlayState predicate(AnimationState<UltimateKotdPickaxe> UKPickaxeAnimationState) {
        UKPickaxeAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }
    @Override
    public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private UKPickaxeRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new UKPickaxeRenderer();
                return this.renderer;
            }
        });
    }
    public static class UKPickaxeRenderer extends GeoItemRenderer<UltimateKotdPickaxe> { public UKPickaxeRenderer() {super(new UltimateKotdPickaxeModel());}}
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
