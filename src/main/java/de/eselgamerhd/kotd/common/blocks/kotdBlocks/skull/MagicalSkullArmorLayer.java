package de.eselgamerhd.kotd.common.blocks.kotdBlocks.skull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.eselgamerhd.kotd.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MagicalSkullArmorLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation SKULL_TEXTURE = ResourceLocation.fromNamespaceAndPath("kotd", "textures/block/magical_skull.png");
    private final CustomSkullModel skullModel;

    public MagicalSkullArmorLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
        this.skullModel = new CustomSkullModel(Minecraft.getInstance().getEntityModels().bakeLayer(CustomSkullModel.MAGICAL_SKULL));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer,
                       int light, @NotNull AbstractClientPlayer player, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks,
                       float netHeadYaw, float headPitch) {

        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
        if (itemstack.isEmpty() || itemstack.getItem() != ModItems.MAGICAL_SKULL.get()) {
            return;
        }

        ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
        if(stack.getItem() == ModItems.MAGICAL_SKULL.get()) {
            poseStack.pushPose();
            this.getParentModel().head.translateAndRotate(poseStack);
            poseStack.scale(0.825F, 0.825F, 0.825F);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(SKULL_TEXTURE));
            this.skullModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        poseStack.pushPose();
        getParentModel().head.translateAndRotate(poseStack);
        poseStack.scale(1.22F, 1.22F, 1.22F);
        VertexConsumer vertexconsumer = buffer.getBuffer(
                RenderType.entityCutoutNoCull(SKULL_TEXTURE)
        );
        skullModel.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}