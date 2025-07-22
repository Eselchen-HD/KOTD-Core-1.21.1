package de.eselgamerhd.kotd.worldgen;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.init.KotdEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class KotdBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_KOTD = registerKey("spawn_kotd");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        @SuppressWarnings("unused")
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_KOTD, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP), biomes.getOrThrow(Biomes.PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(KotdEntities.KNIGHT_OF_THE_DARK.get(), 2, 4, 6))));
    }

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Kotd.MODID, name));
    }
}
