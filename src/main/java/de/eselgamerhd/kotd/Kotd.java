package de.eselgamerhd.kotd;

import de.eselgamerhd.kotd.common.init.ModBlocks;
import de.eselgamerhd.kotd.common.init.ModEntities;
import de.eselgamerhd.kotd.common.init.ModItems;
import de.eselgamerhd.kotd.client.sound.KOTDSounds;
import com.mojang.logging.LogUtils;
import de.eselgamerhd.kotd.worldgen.dimension.CustomDimensionEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static de.eselgamerhd.kotd.CreativeTab.CREATIVE_MODE_TABS;
import static de.eselgamerhd.kotd.common.init.ModBlocks.*;
import static de.eselgamerhd.kotd.common.init.ModItems.*;

@Mod(Kotd.MODID)
public class Kotd {
    public static final String MODID = "kotd";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Kotd(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        KOTDSounds.register(modEventBus);

        ModEntities.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerDimensionEffects);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartingEvent event) {
        LOGGER.info("Initializing KOTD");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("KOTD Mod common setup initialized");
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
            event.accept(OFF_HAND_CROSSBOW.get());
            event.accept(KOTD_MUSIC_DISC.get());
            event.accept(KOTD_ITEM.get());
            event.accept(SCYTHE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModItems.MAGICAL_SKULL.get());
            event.accept(FLOWER_POT_PACK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(KOTD_BLOCK.get());
            event.accept(ANGEL_BLOCK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(KOTD_CRYSTAL.get());
            event.accept(KOTD_CRYSTAL_SHARD.get());
            event.accept(DORMANT_BLACK_HOLE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(KOTD_CRYSTAL_SHOVEL.get());
            event.accept(KOTD_CRYSTAL_PICKAXE.get());
            event.accept(KOTD_CRYSTAL_AXE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(KOTD_CRYSTAL_SWORD.get());
            event.accept(KOTD_HELMET.get());
            event.accept(KOTD_CHESTPLATE.get());
            event.accept(KOTD_LEGGINGS.get());
            event.accept(KOTD_BOOTS.get());
        }
    }

    private void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath("kotd", "skyblock_effects"),
                new CustomDimensionEffects()
        );
    }
}