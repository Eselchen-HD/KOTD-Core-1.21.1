package de.eselgamerhd.kotd.worldgen.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CustomDimensionEffects extends DimensionSpecialEffects {
    public CustomDimensionEffects() {super(256.0F, false, SkyType.NORMAL, false, false);}
    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor.multiply(daylight * 1.04F + 0.06F, daylight * 0.94F + 0.06F, daylight * 0.91F + 0.09F);}
    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }
}