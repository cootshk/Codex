package io.github.cootshk.quicksearch.compat.jei

import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.recipe.IFocusFactory
import mezz.jei.api.runtime.IJeiRuntime
import net.minecraft.resources.ResourceLocation


@JeiPlugin
class JEIPlugin() : IModPlugin {
    private lateinit var runtime: IJeiRuntime

    init {
        instance = this
    }
    override fun getPluginUid(): ResourceLocation {
        //? >1.21 {
        return ResourceLocation.fromNamespaceAndPath("quicksearch", "jei_plugin")
        //?} else
        // return ResourceLocation("quicksearch", "jei_plugin")
    }

    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime) {
        super.onRuntimeAvailable(jeiRuntime)
        this.runtime = jeiRuntime
    }
    internal companion object {
        lateinit var instance: JEIPlugin
        val factory: IFocusFactory
            get() = jeiRuntime.jeiHelpers.focusFactory
        val jeiRuntime: IJeiRuntime
            get() = instance.runtime
    }
}