package io.github.cootshk.quicksearch.compat.create

//? create {
/*import com.simibubi.create.foundation.gui.ScreenOpener
import com.simibubi.create.foundation.ponder.ui.PonderUI
import io.github.cootshk.quicksearch.QuickSearch
import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType
import net.minecraft.world.item.Item

object CreateCompat : QuickSearchHandler() {
    override fun initialize() {
        register(null, SearchableType.ITEM, 0) { s ->
            // Open Ponder, if available
            try {
                ScreenOpener.open(PonderUI.of((s as Item).defaultInstance))
            } catch (e: Exception) {
                QuickSearch.logger.info("No ponder screen found, falling back a layer!")
                skip();
            }
        }
    }
}
*///?}