package de.eselgamerhd.kotd.network;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record UpdateArmorAttributesPacket(EquipmentSlot slot, ItemStack stack) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateArmorAttributesPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "update_armor_attributes"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateArmorAttributesPacket> CODEC =
            StreamCodec.of(
                    UpdateArmorAttributesPacket::encode,
                    UpdateArmorAttributesPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, UpdateArmorAttributesPacket packet) {
        buf.writeEnum(packet.slot());
        ItemStack.STREAM_CODEC.encode(buf, packet.stack());
    }

    private static UpdateArmorAttributesPacket decode(RegistryFriendlyByteBuf buf) {
        return new UpdateArmorAttributesPacket(
                buf.readEnum(EquipmentSlot.class),
                ItemStack.STREAM_CODEC.decode(buf)
        );
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateArmorAttributesPacket packet, IPayloadContext context) {
        context.player().setItemSlot(packet.slot(), packet.stack());
    }
}