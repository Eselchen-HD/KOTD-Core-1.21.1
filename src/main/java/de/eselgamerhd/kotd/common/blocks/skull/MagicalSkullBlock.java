package de.eselgamerhd.kotd.common.blocks.skull;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MagicalSkullBlock extends SkullBlock {
    public static final MapCodec<MagicalSkullBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Type.CODEC.fieldOf("kind").forGetter(AbstractSkullBlock::getType), propertiesCodec())
                    .apply(instance, MagicalSkullBlock::new)
    );
    public MagicalSkullBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @NotNull
    public MapCodec<? extends SkullBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {return new MagicalSkullBlockEntity(pos, state);}

}
