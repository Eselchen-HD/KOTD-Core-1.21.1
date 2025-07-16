package de.eselgamerhd.kotd.worldgen.dimension;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> SKYBLOCK_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "skyblock"));
    public static final ResourceKey<DimensionType> SKYBLOCK_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "skyblock_type"));
    public static final ResourceLocation SKYBLOCK_EFFECTS = ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "skyblock_effects");

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        context.register(SKYBLOCK_DIM_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                SKYBLOCK_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 8)));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        Optional<HolderSet<StructureSet>> structureOverrides = Optional.of(HolderSet.direct(List.of()));

        FlatLevelGeneratorSettings flatSettings = new FlatLevelGeneratorSettings(
                structureOverrides,
                biomeRegistry.getOrThrow(Biomes.PLAINS),
                List.of()
        );

        LevelStem stem = new LevelStem(
                dimTypes.getOrThrow(ModDimensions.SKYBLOCK_DIM_TYPE),
                new FlatLevelSource(flatSettings)
        );

        context.register(SKYBLOCK_KEY, stem);
    }
}