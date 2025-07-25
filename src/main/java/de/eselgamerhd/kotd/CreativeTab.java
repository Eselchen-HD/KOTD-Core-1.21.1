package de.eselgamerhd.kotd;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static de.eselgamerhd.kotd.common.init.KotdBlocks.*;
import static de.eselgamerhd.kotd.common.init.KotdItems.*;
import static net.minecraft.network.chat.Component.translatable;

public class CreativeTab {
    private static ItemStack makeUnbreakable(ItemStack stack) {
        stack.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        return stack;
    }
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Kotd.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KOTD_TAB;
    static {
        KOTD_TAB = CREATIVE_MODE_TABS.register("kotd", () -> CreativeModeTab.builder()
                .title(translatable("itemGroup.kotd"))
                .icon(() -> new ItemStack(KOTD_CRYSTAL.get()))
                .displayItems((ignoreparams, output) -> {
                    output.accept(FLOWER_POT_PACK.get());
                    output.accept(ANGEL_BLOCK.get());
                    output.accept(KOTD_BLOCK.get());
                    output.accept(MAGICAL_SKULL.get());
                    output.accept(KOTD_CRYSTAL.get());
                    output.accept(KOTD_CRYSTAL_SHARD.get());
                    output.accept(DORMANT_BLACK_HOLE.get());
                    output.accept(makeUnbreakable(new ItemStack(ULTIMATE_KOTD_BLADE.get())));
                    output.accept(makeUnbreakable(new ItemStack(ULTIMATE_KOTD_PICKAXE.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CRYSTAL_SWORD.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CRYSTAL_SHOVEL.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CRYSTAL_PICKAXE.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CRYSTAL_AXE.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CRYSTAL_HOE.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_HELMET.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_CHESTPLATE.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_LEGGINGS.get())));
                    output.accept(makeUnbreakable(new ItemStack(KOTD_BOOTS.get())));
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
            event.accept(MAGICAL_SKULL.get());
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
