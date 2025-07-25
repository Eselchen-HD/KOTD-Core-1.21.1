package de.eselgamerhd.kotd.common.init;

import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.SimpleTier;

import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL;
import static net.minecraft.tags.BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class KotdTiers {
    public static final Tier KOTD_TIER = new SimpleTier(INCORRECT_FOR_NETHERITE_TOOL, 2147483647, 9.0F, 7.0F, 25, () -> of(KOTD_CRYSTAL.get()));

    @SuppressWarnings("unused")
    public enum StateTier {
        KOTD(1.0, 1.0),
        ULTIMATE(1.5, 1.3),
        CHAOS(2.5, 2.0),
        BROOD_MOTHER(4.0, 3.0);

        public final double attributeMultiplier;
        public final double specialAbilityMultiplier;

        StateTier(double attributeMultiplier, double specialAbilityMultiplier) {
            this.attributeMultiplier = attributeMultiplier;
            this.specialAbilityMultiplier = specialAbilityMultiplier;
        }
    }

}