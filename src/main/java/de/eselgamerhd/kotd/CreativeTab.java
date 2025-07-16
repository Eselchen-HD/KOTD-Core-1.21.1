package de.eselgamerhd.kotd;

import de.eselgamerhd.kotd.common.init.ModBlocks;
import de.eselgamerhd.kotd.common.init.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.network.chat.Component.translatable;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Kotd.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KOTD_TAB;
    static {
        KOTD_TAB = CREATIVE_MODE_TABS.register("kotd", () -> CreativeModeTab.builder()
                .title(translatable("itemGroup.kotd"))
                .icon(() -> new ItemStack(ModItems.KOTD_CRYSTAL.get()))
                .displayItems((params, output) -> {
                    output.accept(ModBlocks.FLOWER_POT_PACK.get());
                    output.accept(ModBlocks.ANGEL_BLOCK.get());
                    output.accept(ModBlocks.KOTD_BLOCK.get());
                    output.accept(ModItems.MAGICAL_SKULL.get());
                    output.accept(ModItems.KOTD_CRYSTAL.get());
                    output.accept(ModItems.KOTD_CRYSTAL_SHARD.get());
                    output.accept(ModItems.DORMANT_BLACK_HOLE.get());
                    output.accept(ModItems.KOTD_CRYSTAL_SWORD.get());
                    output.accept(ModItems.KOTD_CRYSTAL_SHOVEL.get());
                    output.accept(ModItems.KOTD_CRYSTAL_PICKAXE.get());
                    output.accept(ModItems.KOTD_CRYSTAL_AXE.get());
                    output.accept(ModItems.KOTD_CRYSTAL_HOE.get());
                    output.accept(ModItems.KOTD_HELMET.get());
                    output.accept(ModItems.KOTD_CHESTPLATE.get());
                    output.accept(ModItems.KOTD_LEGGINGS.get());
                    output.accept(ModItems.KOTD_BOOTS.get());
                }).build()
        );
    }
}
