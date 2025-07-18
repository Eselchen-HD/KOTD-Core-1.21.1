package de.eselgamerhd.kotd.common.items.item.dormantBlackHole;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class DormantBlackHoleModel extends DefaultedGeoModel<DormantBlackHole> {

    public DormantBlackHoleModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(DormantBlackHole object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/dormant_black_hole.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DormantBlackHole object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/dbh.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DormantBlackHole animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/dbh.animation.json");
    }
}

