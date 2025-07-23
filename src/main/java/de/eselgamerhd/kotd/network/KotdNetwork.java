package de.eselgamerhd.kotd.network;

import de.eselgamerhd.kotd.Kotd;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KotdNetwork {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(Kotd.MODID)
                .versioned("1.0")
                .playBidirectional(UpdateArmorAttributesPacket.TYPE,
                        UpdateArmorAttributesPacket.CODEC,
                        UpdateArmorAttributesPacket::handle);
    }
}
