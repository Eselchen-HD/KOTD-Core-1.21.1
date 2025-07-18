package de.eselgamerhd.kotd.common.entity.kotd;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import java.util.EnumMap;
import java.util.Map;

public class KOTDModel extends GeoModel<KnightOfTheDark> {
    private static final Map<KOTDVariant, ResourceLocation> TEXTURE_VARIANT = createVariantMap(
            "textures/entity/kotd/kotd_crystal.png",
            "textures/entity/kotd/kotd_crystal_pink.png",
            "textures/entity/kotd/kotd_crystal_green.png"
    );

    private static final Map<KOTDVariant, ResourceLocation> GEO_MODEL_VARIANT = createVariantMap(
            "geo/entity/knight_of_the_dark.geo.json",
            "geo/entity/knight_of_the_dark.geo.json",
            "geo/entity/knight_of_the_dark_green.geo.json"
    );

    private static final Map<KOTDVariant, ResourceLocation> ANIMATION_VARIANT = createVariantMap(
            "animations/entity/knight_of_the_dark.animation.json",
            "animations/entity/knight_of_the_dark.animation.json",
            "animations/entity/knight_of_the_dark_green.animation.json"
    );

    private static Map<KOTDVariant, ResourceLocation> createVariantMap(String normal, String pink, String green) {
        Map<KOTDVariant, ResourceLocation> map = new EnumMap<>(KOTDVariant.class);
        map.put(KOTDVariant.NORMAL, ResourceLocation.fromNamespaceAndPath(Kotd.MODID, normal));
        map.put(KOTDVariant.PINK, ResourceLocation.fromNamespaceAndPath(Kotd.MODID, pink));
        map.put(KOTDVariant.GREEN, ResourceLocation.fromNamespaceAndPath(Kotd.MODID, green));
        return Map.copyOf(map);
    }

    @Override
    public ResourceLocation getModelResource(KnightOfTheDark object) {
        return GEO_MODEL_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getTextureResource(KnightOfTheDark object) {
        return TEXTURE_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(KnightOfTheDark animatable) {
        return ANIMATION_VARIANT.get(animatable.getVariant());
    }
}