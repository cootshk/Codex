package io.github.cootshk.quicksearch.compat

import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.impl.Searchable
import net.minecraft.client.MinecraftClient

object VanillaCompat : QuickSearchHandler() {
    override fun initialize() {
        register(Regex(".*"), priority = Int.MIN_VALUE) { s: Searchable ->
            // If all else fails, just copy the item name
            MinecraftClient.getInstance().keyboard.clipboard = s.searchString
        }
    }

}