package io.github.cootshk.quicksearch.compat
//? create
/*import io.github.cootshk.quicksearch.compat.create.CreateCompat*/
import io.github.cootshk.quicksearch.compat.jei.JEICompat
import io.github.cootshk.quicksearch.compat.rei.REICompat
import net.fabricmc.loader.api.FabricLoader

object QuickSearchCompatLoader {
    private fun canLoad(modId: String): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }
    @JvmStatic
    fun load() {
        VanillaCompat.initialize()
        //? create
        /*if (canLoad("create")) CreateCompat.initialize()*/
        if (canLoad("jei")) JEICompat.initialize()
        if (canLoad("roughlyenoughitems")) REICompat.initialize()
    }
}