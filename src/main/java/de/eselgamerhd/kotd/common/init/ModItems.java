package de.eselgamerhd.kotd.common.init;

import de.eselgamerhd.kotd.client.sound.KOTDSounds;
import de.eselgamerhd.kotd.common.items.armor.KotdCrystalArmorItem;
import de.eselgamerhd.kotd.common.items.kotdItems.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static de.eselgamerhd.kotd.common.init.ModBlocks.ANGEL_BLOCK;
import static de.eselgamerhd.kotd.common.init.ModTiers.*;
import static de.eselgamerhd.kotd.Kotd.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<Item> KOTD_CRYSTAL = ITEMS.register("kotd_crystal", () -> new Item(new Item.Properties().fireResistant().stacksTo(16).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> KOTD_CRYSTAL_SHARD = ITEMS.register("kotd_crystal_shard", () -> new Item(new Item.Properties().fireResistant().stacksTo(64).rarity(Rarity.EPIC)));
    public static final DeferredItem<DormantBlackHole> DORMANT_BLACK_HOLE = ITEMS.register("dormant_black_hole", () -> new DormantBlackHole(new Item.Properties().fireResistant().stacksTo(8).rarity(Rarity.EPIC)));
    public static final DeferredItem<AngelBlockItem> ANGEL_BLOCK_ITEM = ITEMS.register("angel_block", () -> new AngelBlockItem(ANGEL_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<MagicalSkullItem> MAGICAL_SKULL = ITEMS.register("magical_skull", () -> new MagicalSkullItem(ModBlocks.MAGICAL_SKULL.get(), new Item.Properties()));
    public static final DeferredItem<Item> KOTD_ITEM = ITEMS.register("kotd_item", () -> new KOTDItem(new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<OffHandCrossbowItem> OFF_HAND_CROSSBOW = ITEMS.register("off_hand_crossbow", () -> new OffHandCrossbowItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 5.0f,  30));
    public static final DeferredItem<KotdCrystalSwordItem> KOTD_CRYSTAL_SWORD = ITEMS.register("kotd_crystal_sword", () -> new KotdCrystalSwordItem(KOTD_TIER, new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(KOTD_TIER,6,-1.0f))));
    public static final DeferredItem<MertScytheItem> SCYTHE = ITEMS.register("mert_scythe", () -> new MertScytheItem(KOTD_TIER, new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(KOTD_TIER, 7, -2.4f))));
    public static final DeferredItem<ShovelItem> KOTD_CRYSTAL_SHOVEL = ITEMS.register("kotd_crystal_shovel", () -> new ShovelItem(KOTD_TIER, new Item.Properties().fireResistant().attributes(ShovelItem.createAttributes(KOTD_TIER, 1.5f, -1.1f))));
    public static final DeferredItem<AxeItem> KOTD_CRYSTAL_AXE = ITEMS.register("kotd_crystal_axe", () -> new AxeItem(KOTD_TIER, new Item.Properties().fireResistant().attributes(AxeItem.createAttributes(KOTD_TIER, 5.0f, -2.4f))));
    public static final DeferredItem<PickaxeItem> KOTD_CRYSTAL_PICKAXE = ITEMS.register("kotd_crystal_pickaxe", () -> new PickaxeItem(KOTD_TIER, new Item.Properties().fireResistant().attributes(PickaxeItem.createAttributes(KOTD_TIER, 1.0f, -1.2f))));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_HELMET = ITEMS.register("kotd_helmet", () -> new KotdCrystalArmorItem(ArmorItem.Type.HELMET, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_CHESTPLATE = ITEMS.register("kotd_chestplate", () -> new KotdCrystalArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_LEGGINGS = ITEMS.register("kotd_leggings", () -> new KotdCrystalArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_BOOTS = ITEMS.register("kotd_boots", () -> new KotdCrystalArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<Item> KOTD_MUSIC_DISC = ITEMS.registerItem("kotd_music_disc", (properties) -> new Item(properties.jukeboxPlayable(KOTDSounds.KOTD_SONG_KEY).stacksTo(1).fireResistant()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}