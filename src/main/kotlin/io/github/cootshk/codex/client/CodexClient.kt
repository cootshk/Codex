package io.github.cootshk.codex.client

import io.github.cootshk.codex.CodexLogger
import io.github.cootshk.codex.ui.CodexQuickSearchScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW

class CodexClient : ClientModInitializer {
    val key: KeyBinding = KeyBinding("key.codex.search", GLFW.GLFW_KEY_N, KeyBinding.Category.MISC)

    val logger: Logger get() = CodexLogger.getLogger()

    override fun onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(key)
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (key.wasPressed()) {
                logger.info("Opening search screen!")
                client.setScreen(CodexQuickSearchScreen())
            }
        }
        logger.info("Loaded Codex Client!")
    }
}
