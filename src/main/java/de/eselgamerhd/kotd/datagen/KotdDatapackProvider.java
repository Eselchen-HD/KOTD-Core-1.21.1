package de.eselgamerhd.kotd.datagen;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.worldgen.KotdBiomeModifiers;
import de.eselgamerhd.kotd.worldgen.dimension.KotdSkyblockDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class KotdDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, KotdSkyblockDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, KotdSkyblockDimensions::bootstrapStem)
            //.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, KotdBiomeModifiers::bootstrap);


    public KotdDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Kotd.MODID));
    }
}
