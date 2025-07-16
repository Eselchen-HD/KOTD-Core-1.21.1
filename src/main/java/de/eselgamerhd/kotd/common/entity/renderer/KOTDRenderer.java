package de.eselgamerhd.kotd.common.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.eselgamerhd.kotd.common.entity.KnightOfTheDark;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KOTDRenderer extends GeoEntityRenderer<KnightOfTheDark> {
    public KOTDRenderer(EntityRendererProvider.Context context) {
        super(context, new KOTDModel());
        this.shadowRadius = 0.5f;
    }
    private int currentTick = -1;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void render(@NotNull KnightOfTheDark entity, float entityYaw, float partialTicks, PoseStack stack,
                       @NotNull MultiBufferSource bufferIn, int packedLight) {
        stack.scale(1.0F, 1.0F, 1.0F);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLight);
    }

    @Override
    public void renderFinal(PoseStack poseStack, KnightOfTheDark animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (this.currentTick < 0 || this.currentTick != animatable.tickCount) {
            this.currentTick = animatable.tickCount;

            // Find the earbone and use it as the point of reference
            this.model.getBone("lefter").ifPresent(ear -> {
                RandomSource rand = animatable.getRandom();
                Vector3d earPos = ear.getWorldPosition();

                animatable.getCommandSenderWorld().addParticle(ParticleTypes.PORTAL,
                        earPos.x(),
                        earPos.y(),
                        earPos.z(),
                        rand.nextDouble() - 0.5D,
                        -rand.nextDouble(),
                        rand.nextDouble() - 0.5D);
            });
        }
        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, colour);
    }
}
