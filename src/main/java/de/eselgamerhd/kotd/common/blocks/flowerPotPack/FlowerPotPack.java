package de.eselgamerhd.kotd.common.blocks.flowerPotPack;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlowerPotPack extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.join(
            Block.box(4, 0, 4, 12, 2, 12),
            Shapes.or(
                    Block.box(4, 0, 4, 8, 4, 8),
                    Block.box(8, 0, 4, 12, 4, 8),
                    Block.box(4, 0, 8, 8, 4, 12),
                    Block.box(8, 0, 8, 12, 4, 12)
            ),
            BooleanOp.OR
    );

    public static final MapCodec<FlowerPotPack> CODEC = simpleCodec(FlowerPotPack::new);

    public FlowerPotPack(Properties properties) {super(properties.noOcclusion());}
    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return SHAPE;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {return new FlowerPotPackEntity(blockPos, blockState);}

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof FlowerPotPackEntity potEntity)) {
            return InteractionResult.PASS;
        }

        Vec3 hitLoc = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        int slot = getSlotFromHit(hitLoc);
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.isEmpty()) {
            return removeFlower(potEntity, slot, player);
        } else if (isPlantable(heldItem)) {
            return addFlower(potEntity, slot, heldItem, player);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult removeFlower(FlowerPotPackEntity potEntity, int slot, Player player) {
        ItemStack flower = potEntity.getFlower(slot);
        if (!flower.isEmpty()) {
            player.getInventory().placeItemBackInInventory(flower.copy());
            potEntity.setFlower(slot, ItemStack.EMPTY);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult addFlower(FlowerPotPackEntity potEntity, int slot, ItemStack heldItem, Player player) {
        if (potEntity.getFlower(slot).isEmpty() && isPlantable(heldItem)) {
            ItemStack toPlant = heldItem.copyWithCount(1);
            potEntity.setFlower(slot, toPlant);

            if (!player.getAbilities().instabuild) {
                heldItem.shrink(1);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private int getSlotFromHit(Vec3 hitPos) {
        boolean left = hitPos.x < 0.5;
        boolean front = hitPos.z < 0.5;

        if (left && front) return 0;
        if (!left && front) return 1;
        if (left) return 2;
        return 3;
    }

    private boolean isPlantable(ItemStack stack) {
        return stack.is(ItemTags.FLOWERS) ||
                stack.is(ItemTags.SAPLINGS) ||
                stack.is(Items.FERN) ||
                stack.is(Items.AZALEA);
    }
}
