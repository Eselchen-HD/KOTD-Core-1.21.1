package de.eselgamerhd.kotd.network;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record UpdateAttributesPacket(@Nullable EquipmentSlot slot, ItemStack stack, boolean isSword) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateAttributesPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "update_armor_attributes"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAttributesPacket> CODEC =
            StreamCodec.of(
                    UpdateAttributesPacket::encode,
                    UpdateAttributesPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, UpdateAttributesPacket packet) {
        buf.writeBoolean(packet.isSword());
        if (!packet.isSword()) {
            buf.writeEnum(Objects.requireNonNull(packet.slot()));
        }
        ItemStack.STREAM_CODEC.encode(buf, packet.stack());
    }

    private static UpdateAttributesPacket decode(RegistryFriendlyByteBuf buf) {
        boolean isSword = buf.readBoolean();
        EquipmentSlot slot = isSword ? null : buf.readEnum(EquipmentSlot.class);
        ItemStack stack = ItemStack.STREAM_CODEC.decode(buf);
        return new UpdateAttributesPacket(slot, stack, isSword);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateAttributesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (packet.isSword()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, packet.stack());
            } else {
                player.setItemSlot(Objects.requireNonNull(packet.slot()), packet.stack());
            }
        });
    }
}