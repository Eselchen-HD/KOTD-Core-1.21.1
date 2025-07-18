package de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class KotdCrystalArmorModel extends DefaultedGeoModel<KotdCrystalArmorItem> {

    public KotdCrystalArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));
    }

    @Override
    protected String subtype() {
        return "";
    }

    @Override
    public ResourceLocation getModelResource(KotdCrystalArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/armor/kotd_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KotdCrystalArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/armor/kotd_crystal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KotdCrystalArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/armor/kotd_armor.animation.json");
    }
}
