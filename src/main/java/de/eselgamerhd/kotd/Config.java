package de.eselgamerhd.kotd;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Kotd.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_SKYBLOCK_DIMENSION = BUILDER
            .comment("Ob die Skyblock-Dimension aktiviert werden soll")
            .define("enableSkyblockDimension", true);

    private static final ModConfigSpec.BooleanValue LOG_PLACEHOLDER = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("placeholder", true);

    public static final ModConfigSpec.IntValue MORNING_TIME = BUILDER
            .comment("The time in ticks when the morning starts (default is 1000, which is 6:00 AM)")
            .defineInRange("morningTime", 1000, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MORNING_TIME_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the morning time")
            .define("morningTimeIntroduction", "Guten Morgen! Die Sonne geht auf.");

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableSkyblockDimension;
    public static boolean placeholder;
    public static int morningTime;
    public static String morningTimeIntroduction;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableSkyblockDimension = ENABLE_SKYBLOCK_DIMENSION.get();
        placeholder = LOG_PLACEHOLDER.get();
        morningTime = MORNING_TIME.get();
        morningTimeIntroduction = MORNING_TIME_INTRODUCTION.get();
    }
}
