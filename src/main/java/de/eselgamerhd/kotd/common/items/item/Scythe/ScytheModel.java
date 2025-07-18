package de.eselgamerhd.kotd.common.items.item.Scythe;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class ScytheModel extends DefaultedGeoModel<MertScythe> {

    public ScytheModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(MertScythe object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/dormant_black_hole.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MertScythe object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/dbh.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MertScythe animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/dbh.animation.json");
    }
}