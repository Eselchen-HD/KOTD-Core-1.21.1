package de.eselgamerhd.kotd.common.items.items;

import de.eselgamerhd.kotd.common.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class MagicalSkullItem extends BlockItem {
    public MagicalSkullItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        if (clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
            BlockState wallState = ModBlocks.MAGICAL_WALL_SKULL.get()
                    .getStateForPlacement(context);

            if (wallState.canSurvive(context.getLevel(), context.getClickedPos())) {
                return wallState;
            }
        }
        return super.getPlacementState(context);
    }

    @Override
    protected @NotNull SoundEvent getPlaceSound(@NotNull BlockState state) {return SoundType.STONE.getPlaceSound();}
}
