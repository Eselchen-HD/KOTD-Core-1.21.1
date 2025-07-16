package de.eselgamerhd.kotd.datagen;

import de.eselgamerhd.kotd.common.init.ModBlocks;
import de.eselgamerhd.kotd.common.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider  extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ANGEL_BLOCK.get())
                .pattern("BCB")
                .pattern("CDC")
                .pattern("ECE")
                .define('B', Items.FEATHER).define('C', Items.OBSIDIAN).define('D', Items.DIAMOND).define('E', Items.GOLD_INGOT).group("kotd")
                .unlockedBy("has_item", has(Items.FEATHER)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.KOTD_CRYSTAL.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.KOTD_CRYSTAL_SHARD).group("kotd")
                .unlockedBy("has_kotd_crystal_shard", has(ModItems.KOTD_CRYSTAL_SHARD)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.KOTD_BLOCK.get())
                .pattern("BBB")
                .pattern("BCB")
                .pattern("BBB")
                .define('B', ModItems.KOTD_CRYSTAL).define('C', ModItems.DORMANT_BLACK_HOLE).group("kotd")
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.KOTD_CRYSTAL_AXE.get())
                .pattern("CC")
                .pattern("CB")
                .pattern(" B")
                .define('B', Items.BLAZE_ROD).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.KOTD_CRYSTAL_PICKAXE.get())
                .pattern("CCC")
                .pattern(" B ")
                .pattern(" B ")
                .define('B', Items.BLAZE_ROD).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.KOTD_CRYSTAL_SHOVEL.get())
                .pattern("C")
                .pattern("B")
                .pattern("B")
                .define('B', Items.BLAZE_ROD).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KOTD_CRYSTAL_SWORD.get())
                .pattern("C")
                .pattern("C")
                .pattern("B")
                .define('B', Items.BLAZE_ROD).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KOTD_HELMET.get())
                .pattern("BCB")
                .pattern("C C")
                .define('B', ModBlocks.KOTD_BLOCK).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_block", has(ModBlocks.KOTD_BLOCK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KOTD_CHESTPLATE.get())
                .pattern("B B")
                .pattern("CBC")
                .pattern("CCC")
                .define('B', ModBlocks.KOTD_BLOCK).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_block", has(ModBlocks.KOTD_BLOCK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KOTD_LEGGINGS.get())
                .pattern("BCB")
                .pattern("C C")
                .pattern("C C")
                .define('B', ModBlocks.KOTD_BLOCK).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_block", has(ModBlocks.KOTD_BLOCK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KOTD_BOOTS.get())
                .pattern("B B")
                .pattern("C C")
                .define('B', ModBlocks.KOTD_BLOCK).define('C', ModItems.KOTD_CRYSTAL).group("kotd")
                .unlockedBy("has_kotd_block", has(ModBlocks.KOTD_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.KOTD_CRYSTAL.get(), 8)
                .requires(ModBlocks.KOTD_BLOCK.get())
                .unlockedBy("has_kotd_block", has(ModBlocks.KOTD_BLOCK)).group("kotd")
                .save(recipeOutput, "kotd_crystal_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.KOTD_CRYSTAL_SHARD.get(), 9)
                .requires(ModItems.KOTD_CRYSTAL.get())
                .unlockedBy("has_kotd_crystal", has(ModItems.KOTD_CRYSTAL)).group("kotd")
                .save(recipeOutput, "kotd_crystal_shard_from_crystal");
    }
}
