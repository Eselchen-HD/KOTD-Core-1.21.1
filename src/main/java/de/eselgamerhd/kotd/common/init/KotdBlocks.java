package de.eselgamerhd.kotd.common.init;

import de.eselgamerhd.kotd.common.blocks.skull.MagicalSkullBlock;
import de.eselgamerhd.kotd.common.blocks.skull.MagicalWallSkullBlock;
import de.eselgamerhd.kotd.common.blocks.AngelBlock;
import de.eselgamerhd.kotd.common.blocks.flowerPotPack.FlowerPotPack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static de.eselgamerhd.kotd.Kotd.MODID;

public class KotdBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredBlock<MagicalSkullBlock> MAGICAL_SKULL = BLOCKS.register("magical_skull", MagicalSkullBlock::new);
    public static final DeferredBlock<MagicalWallSkullBlock> MAGICAL_WALL_SKULL = BLOCKS.register("magical_wall_skull", MagicalWallSkullBlock::new);
    public static final DeferredBlock<Block> KOTD_BLOCK = registerBlock("kotd_block", () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<FlowerPotPack> FLOWER_POT_PACK = registerBlock("flower_pot_pack", () -> new FlowerPotPack(BlockBehaviour.Properties.of().strength(0.3f)));
    public static final DeferredBlock<AngelBlock> ANGEL_BLOCK = BLOCKS.register("angel_block", () -> new AngelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instabreak().explosionResistance(0).noOcclusion().sound(SoundType.MUD).noLootTable()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        KotdItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}