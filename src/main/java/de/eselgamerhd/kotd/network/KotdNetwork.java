package de.eselgamerhd.kotd.network;

import codechicken.lib.packet.PacketCustomChannel;
import de.eselgamerhd.kotd.Kotd;
import net.covers1624.quack.util.CrashLock;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class KotdNetwork {
    private static final CrashLock LOCK = new CrashLock("Already Initialized.");

    public static final ResourceLocation CHANNEL_NAME = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "network");
    public static final PacketCustomChannel CHANNEL = new PacketCustomChannel(CHANNEL_NAME)
            .optional()
            .client(() -> ClientPacketHandler::new)
            .server(() -> ServerPacketHandler::new);


    //@formatter:off
    //Client to server
//    public static final int S_TOGGLE_DISLOCATORS =      1;
//    public static final int S_TOOL_PROFILE =            2;
//    public static final int S_CYCLE_DIG_AOE =           3;

  //Server to client
//    public static final int C_CRYSTAL_UPDATE =          1;
//    public static final int C_EXPLOSION_EFFECT =        2;
//    public static final int C_IMPACT_EFFECT =           3;

    //@formatter:on

    public static void register(IEventBus modEventBus) {
        CHANNEL.init(modEventBus);
        LOCK.lock();
    }
}
