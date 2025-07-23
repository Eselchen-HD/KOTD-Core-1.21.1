package de.eselgamerhd.kotd.client.event;

import de.eselgamerhd.kotd.common.blocks.skull.CustomSkullModel;
import de.eselgamerhd.kotd.common.blocks.flowerPotPack.FlowerPotPackModel;
import de.eselgamerhd.kotd.common.blocks.flowerPotPack.FlowerPotPackRenderer;
import de.eselgamerhd.kotd.common.blocks.skull.MagicalSkullRenderer;
import de.eselgamerhd.kotd.common.entity.kotd.KnightOfTheDark;
import de.eselgamerhd.kotd.common.entity.kotd.KOTDRenderer;
import de.eselgamerhd.kotd.common.entity.laser_beam.LaserBeamRenderer;
import de.eselgamerhd.kotd.common.init.KotdEntities;
import de.eselgamerhd.kotd.worldgen.dimension.CustomDimensionEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static com.mojang.text2speech.Narrator.LOGGER;
import static de.eselgamerhd.kotd.Kotd.MODID;
import static de.eselgamerhd.kotd.common.blocks.skull.CustomSkullModel.MAGICAL_SKULL;
import static de.eselgamerhd.kotd.common.blocks.skull.MagicalSkullBlock.MAGICAL;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    public static void commonSetup(FMLCommonSetupEvent ignoredEvent) {LOGGER.info("KOTD Mod common setup initialized");}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SkullBlockRenderer.SKIN_BY_TYPE.put(MAGICAL, ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/magical_skull.png"));
        BlockEntityRenderers.register(KotdEntities.MAGICAL_SKULL_BE.get(), MagicalSkullRenderer::new);
        BlockEntityRenderers.register(KotdEntities.FLOWER_POT_PACK_BE.get(), FlowerPotPackRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FlowerPotPackRenderer.LAYER_LOCATION, FlowerPotPackModel::createBodyLayer);
        event.registerLayerDefinition(MAGICAL_SKULL, CustomSkullModel::createCustomHead);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(KotdEntities.MAGICAL_SKULL_BE.get(), MagicalSkullRenderer::new);
        event.registerEntityRenderer(KotdEntities.KNIGHT_OF_THE_DARK.get(), KOTDRenderer::new);
        event.registerEntityRenderer(KotdEntities.LASER_BEAM.get(), LaserBeamRenderer::new);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(KotdEntities.KNIGHT_OF_THE_DARK.get(), KnightOfTheDark.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(KotdEntities.KNIGHT_OF_THE_DARK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
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
    public static void onCreateSkullModel(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(MAGICAL, new CustomSkullModel(event.getEntityModelSet().bakeLayer(CustomSkullModel.MAGICAL_SKULL)));
    }
}