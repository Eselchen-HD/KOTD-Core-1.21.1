package de.eselgamerhd.kotd.common.entity.laser_beam;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.eselgamerhd.kotd.Kotd;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class LaserBeamRenderer extends EntityRenderer<LaserBeam> {
    public LaserBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    private static final ResourceLocation BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "textures/entity/laser_beam/laser_beam.png");

    @Override
    public void render(LaserBeam entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        // Länge und Richtung
        Vec3 direction = entity.getLookAngle().normalize();
        float length = 20.0F;
        float beamRadius = 0.25F;

        // Positionierung des Beams
        poseStack.translate(0, entity.getBbHeight() / 2, 0); // Vom Entity-Zentrum aus
        poseStack.mulPose(Axis.YP.rotationDegrees(((float) Math.toDegrees(Math.atan2(direction.z, direction.x)) - 180)));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-(float) Math.toDegrees(Math.asin(direction.y))));

        // Animation (leichtes UV-Scrollen)
        float animation = (entity.tickCount + partialTicks) * 0.05f;
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.energySwirl(BEAM_TEXTURE, animation, 0));

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();

        drawCone(consumer, matrix, normal, beamRadius);
        drawCylindricalBeam(consumer, matrix, normal, length, beamRadius);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
    private void drawCylindricalBeam(VertexConsumer consumer, Matrix4f matrix, Matrix3f ignoredNormal, float length, float radius) {
        int r = 255, g = 0, b = 255, a = 180;
        int segments = 128;
        float angleStep = (float) (2 * Math.PI / segments);

        float prevY = radius * (float) Math.cos(0);
        float prevZ = radius * (float) Math.sin(0);
        float prevU = 0f;

        for (int i = 1; i <= segments; i++) {
            float angle = i * angleStep;
            float y = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);
            float u = (float) i / segments;

            // Normale berechnen (Zylinder-seitig)
            float ny = (prevY + y) * 0.5f;
            float nz = (prevZ + z) * 0.5f;
            float len = (float) Math.sqrt(ny * ny + nz * nz);
            float normY = ny / len;
            float normZ = nz / len;

            // Zwei Dreiecke pro Segment = ein Rechteck (seitenfläche)
            consumer.addVertex(matrix, 0, prevY, prevZ)
                    .setColor(r, g, b, a)
                    .setUv(prevU, 1f)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(LightTexture.FULL_BRIGHT)
                    .setNormal(0, normY, normZ);

            consumer.addVertex(matrix, 0, y, z)
                    .setColor(r, g, b, a)
                    .setUv(u, 1f)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(LightTexture.FULL_BRIGHT)
                    .setNormal(0, normY, normZ);

            consumer.addVertex(matrix, length, y, z)
                    .setColor(r, g, b, a)
                    .setUv(u, 0f)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(LightTexture.FULL_BRIGHT)
                    .setNormal(0, normY, normZ);

            consumer.addVertex(matrix, length, prevY, prevZ)
                    .setColor(r, g, b, a)
                    .setUv(prevU, 0f)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(LightTexture.FULL_BRIGHT)
                    .setNormal(0, normY, normZ);

            prevY = y;
            prevZ = z;
            prevU = u;
        }
    }
    private void drawCone(VertexConsumer consumer, Matrix4f matrix, Matrix3f ignoredNormal, float radius) {
        int r = 255, g = 0, b = 255, a = 200;
        int segments = 128;
        float angleStep = (float) (2 * Math.PI / segments);

        float tipX = -0.5f;
        float tipY = 0f;
        float tipZ = 0f;

        float prevY = radius * (float) Math.cos(0);
        float prevZ = radius * (float) Math.sin(0);

        for (int i = 1; i <= segments; i++) {
            float angle = i * angleStep;
            float y = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);

            float nx = 1;
            float ny = (prevY + y) * 0.5f;
            float nz = (prevZ + z) * 0.5f;
            float length = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
            nx /= length;
            ny /= length;
            nz /= length;

            consumer.addVertex(matrix, tipX, tipY, tipZ).setColor(r, g, b, a).setUv(0.5f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(nx, ny, nz);
            consumer.addVertex(matrix, 0, prevY, prevZ).setColor(r, g, b, a).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(nx, ny, nz);
            consumer.addVertex(matrix, 0, y, z).setColor(r, g, b, a).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(nx, ny, nz);

            prevY = y;
            prevZ = z;
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LaserBeam pEntity) {
        return ResourceLocation.fromNamespaceAndPath("kotd", "textures/entity/laser_beam/laser_beam.png");
    }
}