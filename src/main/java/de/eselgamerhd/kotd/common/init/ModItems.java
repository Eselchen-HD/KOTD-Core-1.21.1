package de.eselgamerhd.kotd.common.init;

import de.eselgamerhd.kotd.common.items.armor.kotdCrystalArmor.KotdCrystalArmorItem;
import de.eselgamerhd.kotd.common.items.items.*;
import de.eselgamerhd.kotd.common.items.items.Scythe.MertScythe;
import de.eselgamerhd.kotd.common.items.items.dormantBlackHole.DormantBlackHole;
import de.eselgamerhd.kotd.common.items.items.offHandCrossBow.OffHandCrossbow;
import de.eselgamerhd.kotd.common.items.items.ultimateKotdBlade.UltimateKotdBlade;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static de.eselgamerhd.kotd.common.init.ModBlocks.ANGEL_BLOCK;
import static de.eselgamerhd.kotd.common.init.ModTiers.*;
import static de.eselgamerhd.kotd.Kotd.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> KOTD_CRYSTAL = ITEMS.registerItem("kotd_crystal", (properties) -> new Item(properties.fireResistant().stacksTo(16).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> KOTD_CRYSTAL_SHARD = ITEMS.registerItem("kotd_crystal_shard", (properties) -> new Item(properties.fireResistant().stacksTo(64).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> KOTD_ITEM = ITEMS.registerItem("kotd_item", (properties) -> new KOTDItem(properties.fireResistant().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> KOTD_MUSIC_DISC = ITEMS.registerItem("kotd_music_disc", (properties) -> new Item(properties.jukeboxPlayable(ModSounds.KOTD_SONG_KEY).stacksTo(1).fireResistant()));

    public static final DeferredItem<DormantBlackHole> DORMANT_BLACK_HOLE = ITEMS.registerItem("dormant_black_hole", (properties) -> new DormantBlackHole(properties.fireResistant().stacksTo(8).rarity(Rarity.EPIC)));
    public static final DeferredItem<AngelBlockItem> ANGEL_BLOCK_ITEM = ITEMS.register("angel_block", () -> new AngelBlockItem(ANGEL_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<MagicalSkullItem> MAGICAL_SKULL = ITEMS.register("magical_skull", () -> new MagicalSkullItem(ModBlocks.MAGICAL_SKULL.get(), new Item.Properties()));


    public static final DeferredItem<UltimateKotdBlade> ULTIMATE_KOTD_BLADE = ITEMS.registerItem("ultimate_kotd_blade", (properties) -> new UltimateKotdBlade(KOTD_TIER, properties.fireResistant().attributes(SwordItem.createAttributes(KOTD_TIER, 132, -1.0f))));
    public static final DeferredItem<OffHandCrossbow> OFF_HAND_CROSSBOW = ITEMS.register("off_hand_crossbow", () -> new OffHandCrossbow(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 5.0f,  30));
    public static final DeferredItem<KotdCrystalSword> KOTD_CRYSTAL_SWORD = ITEMS.registerItem("kotd_crystal_sword", (properties) -> new KotdCrystalSword(KOTD_TIER, properties.fireResistant().attributes(SwordItem.createAttributes(KOTD_TIER,57,-0.6f))));
    public static final DeferredItem<MertScythe> SCYTHE = ITEMS.registerItem("mert_scythe", (properties) -> new MertScythe(KOTD_TIER, properties.fireResistant().attributes(SwordItem.createAttributes(KOTD_TIER, 32, -2.4f)), 39 , 53));

    public static final DeferredItem<ShovelItem> KOTD_CRYSTAL_SHOVEL = ITEMS.registerItem("kotd_crystal_shovel", (properties) -> new ShovelItem(KOTD_TIER, properties.fireResistant().attributes(ShovelItem.createAttributes(KOTD_TIER, 1.5f, -1.1f))));
    public static final DeferredItem<PickaxeItem> KOTD_CRYSTAL_PICKAXE = ITEMS.registerItem("kotd_crystal_pickaxe", (properties) -> new PickaxeItem(KOTD_TIER, properties.fireResistant().attributes(PickaxeItem.createAttributes(KOTD_TIER, 1.0f, -1.2f))));
    public static final DeferredItem<AxeItem> KOTD_CRYSTAL_AXE = ITEMS.registerItem("kotd_crystal_axe", (properties) -> new AxeItem(KOTD_TIER, properties.fireResistant().attributes(AxeItem.createAttributes(KOTD_TIER, 62.0f, -2.8f))));
    public static final DeferredItem<HoeItem> KOTD_CRYSTAL_HOE = ITEMS.registerItem("kotd_crystal_hoe", (properties) -> new HoeItem(KOTD_TIER, properties.fireResistant().attributes(HoeItem.createAttributes(KOTD_TIER, 1.0f, -1.2f))));

    public static final DeferredItem<KotdCrystalArmorItem> KOTD_HELMET = ITEMS.register("kotd_helmet", () -> new KotdCrystalArmorItem(ArmorItem.Type.HELMET, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_CHESTPLATE = ITEMS.register("kotd_chestplate", () -> new KotdCrystalArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_LEGGINGS = ITEMS.register("kotd_leggings", () -> new KotdCrystalArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<KotdCrystalArmorItem> KOTD_BOOTS = ITEMS.register("kotd_boots", () -> new KotdCrystalArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().fireResistant().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}