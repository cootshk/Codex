package io.github.cootshk.quicksearch.client

import io.github.cootshk.quicksearch.QuickSearchLogger
import io.github.cootshk.quicksearch.ui.QuickSearchScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW

class QuickSearchClient : ClientModInitializer {
    val key: KeyBinding = KeyBinding("key.quicksearch.search", GLFW.GLFW_KEY_N, KeyBinding.Category.MISC)

    val logger: Logger get() = QuickSearchLogger.getLogger()

    override fun onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(key)
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (key.wasPressed()) {
                logger.info("Opening search screen!")
                client.setScreen(QuickSearchScreen())
            }
        }
        logger.info("Loaded QuickSearchClient!")
    }
}
