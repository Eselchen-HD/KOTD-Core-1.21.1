package de.eselgamerhd.kotd.common.blocks.skull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.eselgamerhd.kotd.client.models.CustomSkullModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static de.eselgamerhd.kotd.client.models.CustomSkullModel.MAGICAL_SKULL;

public class MagicalSkullRenderer implements BlockEntityRenderer<MagicalSkullBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("kotd", "textures/block/magical_skull.png");
    private final SkullModel model;

    public MagicalSkullRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new CustomSkullModel(ctx.bakeLayer(MAGICAL_SKULL));
    }

    @Override
    public void render(MagicalSkullBlockEntity blockEntity, float partialTick,
                       PoseStack poseStack, @NotNull MultiBufferSource buffer,
                       int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        poseStack.pushPose();

        if (blockState.getBlock() instanceof WallSkullBlock) {
            Direction direction = blockState.getValue(WallSkullBlock.FACING);

            poseStack.translate(
                    0.5F - direction.getStepX() * 0.2499F,
                    0.25F,
                    0.5F - direction.getStepZ() * 0.2499F
            );
            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        } else {
            poseStack.translate(0.5F, 0.0F, 0.5F);
            float rotation = -blockState.getValue(SkullBlock.ROTATION) * 22.5F;
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(180F));
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}