package com.example.fastclick;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class FastClickClient implements ClientModInitializer {
	public static final String MOD_ID = "fastclick";

	/** Whether the mod is currently active. Toggled with the keybind. */
	public static boolean enabled = true;

	/** Ticks to wait between clicks. Vanilla is 10 (attack) / 5 (block breaking). 0 = no delay. */
	public static int delay = 0;

	private static KeyMapping toggleKey;

	@Override
	public void onInitializeClient() {
		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.fastclick.toggle",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_J,
				KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"))
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.consumeClick()) {
				enabled = !enabled;
				if (client.player != null) {
					client.player.displayClientMessage(
							Component.literal("FastClick: " + (enabled ? "ON" : "OFF")), true);
				}
			}
		});
	}
}
