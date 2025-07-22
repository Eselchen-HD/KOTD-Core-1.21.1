package de.eselgamerhd.kotd.client.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {

    public static KeyMapping toolConfig;


    public static final KeyMapping OPEN_ATTRIBUTE_MENU = new KeyMapping(
            "key.kotd.open_attribute_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.kotd.keybinds"
    );

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ATTRIBUTE_MENU);
    }
}
