package de.eselgamerhd.kotd.common.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.network.chat.Component.empty;
import static net.minecraft.network.chat.Component.literal;

public class KotdItem extends Item {
    public KotdItem(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(literal("").withStyle(ChatFormatting.GRAY));
        tooltip.add(empty());
    }
}
