package de.eselgamerhd.kotd.client.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CustomDimensionEffects extends DimensionSpecialEffects {
    public CustomDimensionEffects() {super(256.0F, false, SkyType.NORMAL, false, false);}
    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor.multiply(daylight * 1.04F + 0.06F, daylight * 0.94F + 0.06F, daylight * 0.91F + 0.09F);}
    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }

    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath("kotd", "skyblock_effects"),
                new CustomDimensionEffects()
        );
    }
}