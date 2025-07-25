package de.eselgamerhd.kotd.common.items.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import static net.minecraft.network.chat.Component.translatable;

public class KotdCrystalSword extends SwordItem {
    public KotdCrystalSword(Tier tier, Properties properties) {super(tier, properties);}
    @Override
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
        stack.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        super.onCraftedBy(stack, level, player);
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(translatable("tooltip.kotd.kotd_crystal_sword"));
    }
}
