package de.eselgamerhd.kotd.common.blocks.kotdBlocks.skull;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CustomSkullModel extends SkullModel {
    public static final ModelLayerLocation MAGICAL_SKULL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("kotd", "magical_skull"), "main");
    private final ModelPart head;

    public CustomSkullModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
    }

    public static LayerDefinition createCustomHead() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F,
                                new CubeDeformation(0.0F),
                                0.5F, 0.5F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(float animationProgress, float yaw, float pitch) {
        this.head.yRot = yaw * ((float)Math.PI / 180F);
        this.head.xRot = pitch * ((float)Math.PI / 180F);
    }

}