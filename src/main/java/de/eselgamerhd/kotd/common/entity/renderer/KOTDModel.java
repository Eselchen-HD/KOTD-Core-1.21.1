package de.eselgamerhd.kotd.common.entity.renderer;

import com.google.common.collect.Maps;
import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.entity.KOTDVariant;
import de.eselgamerhd.kotd.common.entity.KnightOfTheDark;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class KOTDModel extends GeoModel<KnightOfTheDark> {
    public KOTDModel() {super();}

    private static final Map<KOTDVariant, ResourceLocation> TEXTURE_VARIANT =
            Util.make(Maps.newEnumMap(KOTDVariant.class), map -> {
                map.put(KOTDVariant.NORMAL,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/entity/kotd/kotd_crystal.png"));
                map.put(KOTDVariant.PINK,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/entity/kotd/kotd_crystal_pink.png"));
                map.put(KOTDVariant.GREEN,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/entity/kotd/kotd_crystal_green.png"));
            });
    private static final Map<KOTDVariant, ResourceLocation> GEO_MODEL_VARIANT =
            Util.make(Maps.newEnumMap(KOTDVariant.class), map -> {
                map.put(KOTDVariant.NORMAL,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/entity/knight_of_the_dark.geo.json"));
                map.put(KOTDVariant.PINK,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/entity/knight_of_the_dark.geo.json"));
                map.put(KOTDVariant.GREEN,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/entity/knight_of_the_dark_green.geo.json"));
            });
    private static final Map<KOTDVariant, ResourceLocation> ANIMATION_VARIANT =
            Util.make(Maps.newEnumMap(KOTDVariant.class), map -> {
                map.put(KOTDVariant.NORMAL,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/entity/knight_of_the_dark.animation.json"));
                map.put(KOTDVariant.PINK,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/entity/knight_of_the_dark.animation.json"));
                map.put(KOTDVariant.GREEN,
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/entity/knight_of_the_dark_green.animation.json"));
            });

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
