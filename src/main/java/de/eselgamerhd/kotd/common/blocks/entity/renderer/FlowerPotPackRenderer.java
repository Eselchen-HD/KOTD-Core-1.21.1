package de.eselgamerhd.kotd.common.blocks.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.eselgamerhd.kotd.common.blocks.entity.FlowerPotPackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FlowerPotPackRenderer implements BlockEntityRenderer<FlowerPotPackEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("kotd", "flower_pot_pack"), "main");

    @SuppressWarnings("unused")
    public FlowerPotPackRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull FlowerPotPackEntity blockEntity, float partialTick,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.popPose();

        for (int i = 0; i < 4; i++) {renderFlower(blockEntity, i, poseStack, bufferSource, packedLight, packedOverlay);}
    }

    private void renderFlower(FlowerPotPackEntity blockEntity, int slot,
                              PoseStack poseStack, MultiBufferSource bufferSource,
                              int light, int overlay) {
        ItemStack flower = blockEntity.getFlower(slot);
        if (flower.isEmpty()) {return;}

        poseStack.pushPose();
        double x = (slot % 2 == 0) ? 0.36 : 0.63;
        double z = (slot < 2) ? 0.36 : 0.63;
        poseStack.translate(x, 0.35, z);
        poseStack.scale(0.3F, 0.3F, 0.3F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                flower,
                ItemDisplayContext.FIXED,
                light,
                overlay,
                poseStack,
                bufferSource,
                blockEntity.getLevel(),
                0);
        poseStack.popPose();
    }
}