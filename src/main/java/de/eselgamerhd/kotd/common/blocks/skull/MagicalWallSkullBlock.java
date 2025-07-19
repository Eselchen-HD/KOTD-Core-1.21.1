package de.eselgamerhd.kotd.common.blocks.skull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static de.eselgamerhd.kotd.common.blocks.skull.MagicalSkullBlock.MAGICAL;

public class MagicalWallSkullBlock extends WallSkullBlock {
    public MagicalWallSkullBlock() {
        super(MAGICAL, BlockBehaviour.Properties.ofFullCopy(Blocks.PLAYER_HEAD));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {return RenderShape.ENTITYBLOCK_ANIMATED;}

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {return new MagicalSkullBlockEntity(pos, state);}
}
