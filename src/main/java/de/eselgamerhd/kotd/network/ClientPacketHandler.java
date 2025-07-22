package de.eselgamerhd.kotd.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class ClientPacketHandler implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, @NotNull Minecraft mc) {
        //noinspection StatementWithEmptyBody
        switch (packet.getType()) {
        }
    }
}























