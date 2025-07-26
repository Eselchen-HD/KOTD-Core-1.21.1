package de.eselgamerhd.kotd.client.models.flowerpotpack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class FlowerPotPackModel extends Model {
    private final ModelPart block1;
    private final ModelPart block2;
    private final ModelPart block3;
    private final ModelPart block4;

    public FlowerPotPackModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.block1 = root.getChild("block1");
        this.block2 = root.getChild("block2");
        this.block3 = root.getChild("block3");
        this.block4 = root.getChild("block4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("block1",
                CubeListBuilder.create()
                        .texOffs(0, 22)
                        .addBox(-4.0F, -24.0F, -4.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("block2",
                CubeListBuilder.create()
                        .texOffs(16, 22)
                        .addBox(0.0F, -24.0F, -4.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("block3",
                CubeListBuilder.create()
                        .texOffs(0, 30)
                        .addBox(-4.0F, -24.0F, 0.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("block4",
                CubeListBuilder.create()
                        .texOffs(16, 30)
                        .addBox(0.0F, -24.0F, 0.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int i1, int i2) {
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        block1.render(poseStack, vertexConsumer, i, i1);
        block2.render(poseStack, vertexConsumer, i, i1);
        block3.render(poseStack, vertexConsumer, i, i1);
        block4.render(poseStack, vertexConsumer, i, i1);

        poseStack.popPose();
    }
}