package de.eselgamerhd.kotd.common.init;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.blocks.flowerPotPack.FlowerPotPackEntity;
import de.eselgamerhd.kotd.common.blocks.skull.MagicalSkullBlockEntity;
import de.eselgamerhd.kotd.common.entity.kotd.KnightOfTheDark;
import de.eselgamerhd.kotd.common.entity.laser_beam.LaserBeam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class KotdEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Kotd.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Kotd.MODID);

    @SuppressWarnings("DataFlowIssue")
    public static final Supplier<BlockEntityType<FlowerPotPackEntity>> FLOWER_POT_PACK_BE = BLOCK_ENTITIES.register("flower_pot_pack_be",
            () -> BlockEntityType.Builder.of(FlowerPotPackEntity::new, KotdBlocks.FLOWER_POT_PACK.get()).build(null));
    @SuppressWarnings("DataFlowIssue")
    public static final Supplier<BlockEntityType<MagicalSkullBlockEntity>> MAGICAL_SKULL_BE = BLOCK_ENTITIES.register("magical_skull_be",
            () -> BlockEntityType.Builder.of(MagicalSkullBlockEntity::new, KotdBlocks.MAGICAL_SKULL_BLOCK.get(), KotdBlocks.MAGICAL_WALL_SKULL.get()).build(null));

    public static final DeferredHolder<EntityType<?>, EntityType<LaserBeam>> LASER_BEAM = ENTITY_TYPES.register("black_hole",
            () -> EntityType.Builder.of(LaserBeam::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "black_hole").toString()));

    public static final Supplier<EntityType<KnightOfTheDark>> KNIGHT_OF_THE_DARK = ENTITY_TYPES.register("knight_of_the_dark",
            () -> EntityType.Builder.of(KnightOfTheDark::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8)
                    .updateInterval(3).build("knight_of_the_dark"));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        ENTITY_TYPES.register(eventBus);
    }
}
