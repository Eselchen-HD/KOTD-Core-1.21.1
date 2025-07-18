package de.eselgamerhd.kotd.common.blocks.skull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MagicalWallSkullBlock extends WallSkullBlock {
    public MagicalWallSkullBlock(SkullBlock.Type type, Properties properties) {
        super( type, properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {return RenderShape.ENTITYBLOCK_ANIMATED;}

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {return new MagicalSkullBlockEntity(pos, state);}
}
