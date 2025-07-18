package de.eselgamerhd.kotd.common.items.items.ultimateKotdBlade;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class UltimateKotdBladeModel extends DefaultedGeoModel<UltimateKotdBlade> {

    public UltimateKotdBladeModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(UltimateKotdBlade object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/ultimate_kotd_blade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UltimateKotdBlade object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/ultimate_kotd_blade_3d.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UltimateKotdBlade animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/ultimate_kotd_blade.animation.json");
    }
}

