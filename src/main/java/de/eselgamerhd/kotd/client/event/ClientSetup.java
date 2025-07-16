package de.eselgamerhd.kotd.client.event;

import de.eselgamerhd.kotd.common.blocks.kotdBlocks.skull.CustomSkullModel;
import de.eselgamerhd.kotd.common.blocks.kotdBlocks.skull.MagicalSkullArmorLayer;
import de.eselgamerhd.kotd.common.blocks.kotdBlocks.FlowerPotPackModel;
import de.eselgamerhd.kotd.common.blocks.entity.renderer.FlowerPotPackRenderer;
import de.eselgamerhd.kotd.common.blocks.entity.renderer.MagicalSkullRenderer;
import de.eselgamerhd.kotd.common.entity.KnightOfTheDark;
import de.eselgamerhd.kotd.common.entity.renderer.KOTDRenderer;
import de.eselgamerhd.kotd.common.init.ModEntities;
import de.eselgamerhd.kotd.worldgen.dimension.CustomDimensionEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static de.eselgamerhd.kotd.Kotd.MODID;
import static de.eselgamerhd.kotd.common.blocks.kotdBlocks.skull.CustomSkullModel.MAGICAL_SKULL;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModEntities.MAGICAL_SKULL_BE.get(), MagicalSkullRenderer::new);
        BlockEntityRenderers.register(ModEntities.FLOWER_POT_PACK_BE.get(), FlowerPotPackRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FlowerPotPackRenderer.LAYER_LOCATION, FlowerPotPackModel::createBodyLayer);
        event.registerLayerDefinition(MAGICAL_SKULL, CustomSkullModel::createCustomHead);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.KNIGHT_OF_THE_DARK.get(), KOTDRenderer::new);
        event.registerBlockEntityRenderer(ModEntities.MAGICAL_SKULL_BE.get(), MagicalSkullRenderer::new);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.KNIGHT_OF_THE_DARK.get(), KnightOfTheDark.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.KNIGHT_OF_THE_DARK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
    @SubscribeEvent
    public static void onRegisterDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath("kotd", "skyblock_effects"),
                new CustomDimensionEffects()
        );
    }
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skin);
            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new MagicalSkullArmorLayer(playerRenderer));
            }
        }
    }
}