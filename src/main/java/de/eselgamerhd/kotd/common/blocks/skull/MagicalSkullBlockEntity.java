package de.eselgamerhd.kotd.common.blocks.skull;

import de.eselgamerhd.kotd.common.init.KotdEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MagicalSkullBlockEntity extends SkullBlockEntity {
    public MagicalSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
    @Override
    public @NotNull BlockEntityType<?> getType() {return KotdEntities.MAGICAL_SKULL_BE.get();}
}