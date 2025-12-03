package io.github.cootshk.quicksearch.compat.jei

import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import net.minecraft.util.Identifier


@JeiPlugin
class JEIPlugin : IModPlugin {
    override fun getPluginUid(): Identifier {
        return Identifier.of("quicksearch", "jei_plugin")
    }
}