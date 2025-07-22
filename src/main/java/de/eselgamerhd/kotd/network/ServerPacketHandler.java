package de.eselgamerhd.kotd.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ServerPacketHandler implements ICustomPacketHandler.IServerPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, @NotNull ServerPlayer sender) {
        //noinspection StatementWithEmptyBody
        switch (packet.getType()) {
        }
    }
}