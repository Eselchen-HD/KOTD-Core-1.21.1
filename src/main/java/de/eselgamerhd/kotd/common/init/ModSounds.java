package de.eselgamerhd.kotd.common.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static de.eselgamerhd.kotd.Kotd.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);

    public static final Supplier<SoundEvent> KOTD_ARMOR_SOUND = registerSoundEvent("item.armor.equip_kotd");
    public static final Supplier<SoundEvent> KOTD_STEP = registerSoundEvent("entity.walk.kotd");
    public static final Supplier<SoundEvent> SCYTHE_HIT = registerSoundEvent("item.scythe.hit");

    @SuppressWarnings("unused")
    public static final Supplier<SoundEvent> KOTD_SONG = registerSoundEvent("song.kotd");
    public static final ResourceKey<JukeboxSong> KOTD_SONG_KEY = createSong("song.kotd");

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
    public static Holder<SoundEvent> getHolder(Supplier<SoundEvent> sound) {
        return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(
                ResourceKey.create(Registries.SOUND_EVENT, sound.get().getLocation())
        );
    }
    public static void register(IEventBus eventBus) {SOUND_EVENTS.register(eventBus);}
}