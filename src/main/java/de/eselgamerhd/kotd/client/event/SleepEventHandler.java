package de.eselgamerhd.kotd.client.event;

import de.eselgamerhd.kotd.Config;
import de.eselgamerhd.kotd.Kotd;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import static net.minecraft.network.chat.Component.literal;

@EventBusSubscriber(modid = Kotd.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SleepEventHandler {
    public static final ResourceLocation SKYBLOCK_DIM = ResourceLocation.fromNamespaceAndPath("kotd", "skyblock");

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        ResourceLocation dimensionId = level.dimension().location();

        if (!level.isClientSide() && dimensionId.equals(SKYBLOCK_DIM) && player.level() instanceof ServerLevel serverLevel) {
            int wakeTime = Config.MORNING_TIME.get();
                serverLevel.setDayTime(wakeTime);
                serverLevel.setWeatherParameters(0, 0, false, false);

                String morningTimeIntroduction = Config.MORNING_TIME_INTRODUCTION.get();
                player.displayClientMessage(literal(morningTimeIntroduction), true);
        } else {
            Kotd.LOGGER.info("Event triggered in dimension: {}", dimensionId);
        }
    }
}