package de.eselgamerhd.kotd.common.items.kotdItems;

import de.eselgamerhd.kotd.Kotd;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import static net.minecraft.network.chat.Component.literal;

public class KotdCrystalSwordItem extends SwordItem {
    public KotdCrystalSwordItem(Tier tier, Properties properties) {super(tier, properties);}


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        builder.add(Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(Kotd.MODID, "kotd_crystal_sword_attack_speed"),
                        50,
                        AttributeModifier.Operation.ADD_VALUE
                ), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(literal("§cAn KOTD Sword with the power of Infinit Energy").withStyle(ChatFormatting.GRAY));
    }
}
