package de.eselgamerhd.kotd.common.blocks;

import de.eselgamerhd.kotd.common.init.KotdItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class AngelBlock extends Block {
    public AngelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onDestroyedByPlayer(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, boolean willHarvest, @NotNull FluidState fluid) {
        if (!player.isCreative()) {
            player.getInventory().placeItemBackInInventory(KotdItems.ANGEL_BLOCK_ITEM.get().getDefaultInstance(), true);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
