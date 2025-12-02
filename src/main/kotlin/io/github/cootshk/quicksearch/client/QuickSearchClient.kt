package io.github.cootshk.quicksearch.client

import io.github.cootshk.quicksearch.QuickSearch
import io.github.cootshk.quicksearch.ui.QuickSearchScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW

class QuickSearchClient : ClientModInitializer {
    //? if >1.21 {
    val category: KeyBinding.Category = KeyBinding.Category.MISC
    //?} else {
     /*val category: String = KeyBinding.MISC_CATEGORY
    *///?}
    val key: KeyBinding = KeyBinding("key.quicksearch.search", GLFW.GLFW_KEY_N, category)

   private val logger = QuickSearch.logger

    override fun onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(key)
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (key.wasPressed()) {
                logger.info("Opening search screen!")
                client.setScreen(QuickSearchScreen())
            }
        }
        logger.info("Loaded QuickSearch!")
    }
}
