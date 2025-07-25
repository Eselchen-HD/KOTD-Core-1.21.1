package de.eselgamerhd.kotd.common.items.item.ultimateKotdPickaxe;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class UltimateKotdPickaxeModel extends DefaultedGeoModel<UltimateKotdPickaxe> {

    public UltimateKotdPickaxeModel() {super(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, ""));}

    @Override
    protected String subtype() {return "";}

    @Override
    public ResourceLocation getModelResource(UltimateKotdPickaxe object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "geo/item/ultimate_kotd_pickaxe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UltimateKotdPickaxe object) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/item/ultimate_kotd_pickaxe_3d.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UltimateKotdPickaxe animatable) {
        return ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "animations/item/ultimate_kotd_pickaxe.animation.json");
    }
}