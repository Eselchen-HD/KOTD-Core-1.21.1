package de.eselgamerhd.kotd.common.items.renderer;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.items.kotdItems.MertScytheItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class ScytheModel extends DefaultedGeoModel<MertScytheItem> {

    public ScytheModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(MertScytheItem object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/dormant_black_hole.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MertScytheItem object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/dbh.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MertScytheItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/dbh.animation.json");
    }
}