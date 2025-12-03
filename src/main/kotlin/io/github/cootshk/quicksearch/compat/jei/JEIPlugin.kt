package io.github.cootshk.quicksearch.compat.jei

import io.github.cootshk.quicksearch.QuickSearch
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.recipe.IFocusFactory
import mezz.jei.api.runtime.IIngredientManager
import mezz.jei.api.runtime.IJeiRuntime
import mezz.jei.library.runtime.JeiRuntime
import net.minecraft.resources.ResourceLocation


@JeiPlugin
class JEIPlugin() : IModPlugin {
    private lateinit var runtime: IJeiRuntime

    init {
        QuickSearch.logger.info("JEI Plugin instantiated")
        instance = this
    }
    override fun getPluginUid(): ResourceLocation {
        QuickSearch.logger.info("Loading JEI Plugin")
        //? >1.21 {
        return ResourceLocation.fromNamespaceAndPath("quicksearch", "jei_plugin")
        //?} else
        // return ResourceLocation("quicksearch", "jei_plugin")
    }

    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime) {
        super.onRuntimeAvailable(jeiRuntime)
        this.runtime = jeiRuntime
        QuickSearch.logger.info("JEI Runtime available")
    }
    internal companion object {
        lateinit var instance: JEIPlugin
        val factory: IFocusFactory
            get() = jeiRuntime.jeiHelpers.focusFactory
        val jeiRuntime: IJeiRuntime
            get() = instance.runtime
    }
}