package de.eselgamerhd.kotd.common.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import static net.minecraft.network.chat.Component.literal;

public class KotdCrystalSword extends SwordItem {
    public KotdCrystalSword(Tier tier, Properties properties) {super(tier, properties);}

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
