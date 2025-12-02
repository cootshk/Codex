package io.github.cootshk.quicksearch.compat

import io.github.cootshk.quicksearch.QuickSearch
//? =1.20.1
/*import io.github.cootshk.quicksearch.compat.create.CreateCompat*/
import net.fabricmc.loader.api.FabricLoader

object QuickSearchCompatLoader {
    @JvmStatic
    fun load() {
        QuickSearch.addCompat(VanillaCompat)
        //? =1.20.1 {
        /*if (FabricLoader.getInstance().isModLoaded("create")) {
            QuickSearch.addCompat(CreateCompat)
        }
        *///?}
    }
}