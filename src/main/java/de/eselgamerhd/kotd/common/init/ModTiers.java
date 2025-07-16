package de.eselgamerhd.kotd.common.init;

import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.SimpleTier;

import static de.eselgamerhd.kotd.common.init.ModItems.KOTD_CRYSTAL;
import static net.minecraft.tags.BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class ModTiers {
    public static final Tier KOTD_TIER = new SimpleTier(INCORRECT_FOR_NETHERITE_TOOL, 2147483647, 9.0F, 7.0F, 25, () -> of(KOTD_CRYSTAL.get()));
}