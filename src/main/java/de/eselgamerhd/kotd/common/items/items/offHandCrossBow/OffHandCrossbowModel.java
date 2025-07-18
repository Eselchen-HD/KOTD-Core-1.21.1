package de.eselgamerhd.kotd.common.items.items.offHandCrossBow;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class OffHandCrossbowModel extends DefaultedGeoModel<OffHandCrossbow> {

    public OffHandCrossbowModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(OffHandCrossbow object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/dormant_black_hole.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OffHandCrossbow object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/dbh.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OffHandCrossbow animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/dbh.animation.json");
    }
}
