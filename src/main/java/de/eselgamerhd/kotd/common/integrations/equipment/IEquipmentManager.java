package de.eselgamerhd.kotd.common.integrations.equipment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface IEquipmentManager {

    Optional<IItemHandlerModifiable> getInventory(LivingEntity entity);

    ItemStack findMatchingItem(Item item, LivingEntity entity);

    ItemStack findMatchingItem(Predicate<ItemStack> predicate, LivingEntity entity);

    List<ResourceLocation> getSlotIcons(LivingEntity entity);

    void registerCap(RegisterCapabilitiesEvent event, Item item);
}
