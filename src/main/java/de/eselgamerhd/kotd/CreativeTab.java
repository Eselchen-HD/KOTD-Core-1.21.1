package de.eselgamerhd.kotd;

import de.eselgamerhd.kotd.common.init.KotdBlocks;
import de.eselgamerhd.kotd.common.init.KotdItems;
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
                .icon(() -> new ItemStack(KotdItems.KOTD_CRYSTAL.get()))
                .displayItems((ignoreparams, output) -> {
                    output.accept(KotdBlocks.FLOWER_POT_PACK.get());
                    output.accept(KotdBlocks.ANGEL_BLOCK.get());
                    output.accept(KotdBlocks.KOTD_BLOCK.get());
                    output.accept(KotdItems.MAGICAL_SKULL.get());
                    output.accept(KotdItems.KOTD_CRYSTAL.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_SHARD.get());
                    output.accept(KotdItems.DORMANT_BLACK_HOLE.get());
                    output.accept(KotdItems.ULTIMATE_KOTD_BLADE.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_SWORD.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_SHOVEL.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_PICKAXE.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_AXE.get());
                    output.accept(KotdItems.KOTD_CRYSTAL_HOE.get());
                    output.accept(KotdItems.KOTD_HELMET.get());
                    output.accept(KotdItems.KOTD_CHESTPLATE.get());
                    output.accept(KotdItems.KOTD_LEGGINGS.get());
                    output.accept(KotdItems.KOTD_BOOTS.get());
                }).build()
        );
    }
}
