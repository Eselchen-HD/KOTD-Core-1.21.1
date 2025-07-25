package de.eselgamerhd.kotd;

import de.eselgamerhd.kotd.client.event.ClientSetup;
import de.eselgamerhd.kotd.client.gui.ConfigScreen;
import de.eselgamerhd.kotd.client.keybinding.KeyBinds;
import de.eselgamerhd.kotd.common.init.KotdBlocks;
import de.eselgamerhd.kotd.common.init.KotdEntities;
import de.eselgamerhd.kotd.common.init.KotdItems;
import de.eselgamerhd.kotd.common.init.KotdSounds;
import com.mojang.logging.LogUtils;
import de.eselgamerhd.kotd.worldgen.dimension.CustomDimensionEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static de.eselgamerhd.kotd.CreativeTab.CREATIVE_MODE_TABS;

@Mod(Kotd.MODID)
public class Kotd {
    public static final String MODID = "kotd";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Kotd(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        //modEventBus.register(PickaxeEventHandler.class);
        KotdBlocks.register(modEventBus);
        KotdItems.register(modEventBus);
        KotdSounds.register(modEventBus);
        KotdEntities.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(ClientSetup::commonSetup);
        modEventBus.addListener(CreativeTab::addCreative);
        modEventBus.addListener(CustomDimensionEffects::registerDimensionEffects);
        modEventBus.addListener(KeyBinds::registerKeybinds);
    }
    @SubscribeEvent
    public void onServerStarted(ServerStartingEvent event) {
        LOGGER.info("Initializing KOTD");
    }
    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        if (KeyBinds.OPEN_ATTRIBUTE_MENU.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (ConfigScreen.hasArmorPiece(player)) {
                Minecraft.getInstance().setScreen(new ConfigScreen());
            }
        }
    }
    /*
     *todo Darkness Boss 9phasen (Von den Knight of the dark's)
     *todo Knight of The Dark Boss 8x
     *todo Darkness Core/shards/balbla
     *todo Holy Magical Armor
     *todo Storyboard
     *todo ?Eigener Crafter?
     *todo
     *todo
    */
}