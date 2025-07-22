package de.eselgamerhd.kotd.common.blocks.flowerPotPack;

import de.eselgamerhd.kotd.common.init.KotdEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FlowerPotPackEntity extends BlockEntity {
    private final NonNullList<ItemStack> flowerItems = NonNullList.withSize(4, ItemStack.EMPTY);
    public FlowerPotPackEntity(BlockPos pos, BlockState blockState) {super(KotdEntities.FLOWER_POT_PACK_BE.get(), pos, blockState);}
    public ItemStack getFlower(int slot) {
        return flowerItems.get(slot);
    }

    public void setFlower(int slot, ItemStack stack) {
        this.flowerItems.set(slot, stack);
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition,
                    getBlockState(),
                    getBlockState(),
                    Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        CompoundTag flowersTag = new CompoundTag();
        ContainerHelper.saveAllItems(flowersTag, flowerItems, provider);
        tag.put("Flowers", flowersTag);
    }
    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("Flowers")) {
            ContainerHelper.loadAllItems(tag.getCompound("Flowers"), flowerItems, provider);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {return KotdEntities.FLOWER_POT_PACK_BE.get();}

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {return ClientboundBlockEntityDataPacket.create(this);}

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket pkt, HolderLookup.@NotNull Provider provider) {
        super.onDataPacket(net, pkt, provider);
        handleUpdateTag(pkt.getTag(), provider);
    }
}