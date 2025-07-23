package de.eselgamerhd.kotd;

import de.eselgamerhd.kotd.common.init.KotdBlocks;
import de.eselgamerhd.kotd.common.init.KotdItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static de.eselgamerhd.kotd.common.init.KotdBlocks.*;
import static de.eselgamerhd.kotd.common.init.KotdItems.*;
import static de.eselgamerhd.kotd.common.init.KotdItems.DORMANT_BLACK_HOLE;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_BOOTS;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CHESTPLATE;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL_AXE;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL_PICKAXE;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL_SHARD;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL_SHOVEL;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_CRYSTAL_SWORD;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_HELMET;
import static de.eselgamerhd.kotd.common.init.KotdItems.KOTD_LEGGINGS;
import static de.eselgamerhd.kotd.common.init.KotdItems.SCYTHE;
import static de.eselgamerhd.kotd.common.init.KotdItems.ULTIMATE_KOTD_BLADE;
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
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
            event.accept(OFF_HAND_CROSSBOW.get());
            event.accept(KOTD_MUSIC_DISC.get());
            event.accept(KOTD_ITEM.get());
            event.accept(SCYTHE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(KotdItems.MAGICAL_SKULL.get());
            event.accept(FLOWER_POT_PACK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(KOTD_BLOCK.get());
            event.accept(ANGEL_BLOCK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(KOTD_CRYSTAL.get());
            event.accept(KOTD_CRYSTAL_SHARD.get());
            event.accept(DORMANT_BLACK_HOLE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(KOTD_CRYSTAL_SHOVEL.get());
            event.accept(KOTD_CRYSTAL_PICKAXE.get());
            event.accept(KOTD_CRYSTAL_AXE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ULTIMATE_KOTD_BLADE.get());
            event.accept(KOTD_CRYSTAL_SWORD.get());
            event.accept(KOTD_HELMET.get());
            event.accept(KOTD_CHESTPLATE.get());
            event.accept(KOTD_LEGGINGS.get());
            event.accept(KOTD_BOOTS.get());
        }
    }
}
