package de.eselgamerhd.kotd.datagen;

import de.eselgamerhd.kotd.Kotd;
import de.eselgamerhd.kotd.common.init.KotdBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class KotdBlockTagProvider extends BlockTagsProvider {
    public KotdBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Kotd.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(KotdBlocks.KOTD_BLOCK.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(KotdBlocks.KOTD_BLOCK.get());
    }
}
