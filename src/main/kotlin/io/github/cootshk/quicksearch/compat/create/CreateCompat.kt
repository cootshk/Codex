package io.github.cootshk.quicksearch.compat.create

//? create {
/*import com.simibubi.create.foundation.gui.ScreenOpener
import com.simibubi.create.foundation.ponder.ui.PonderUI
import io.github.cootshk.quicksearch.QuickSearch
import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType
import net.minecraft.item.Item

object CreateCompat : QuickSearchHandler("create") {
    override fun initialize() {
        register(null, SearchableType.ITEM, 0) { s ->
            // Open Ponder, if available
            val i = s as Item
            try {
                val ui = PonderUI.of(i.defaultStack)
                ScreenOpener.open(ui)
            } catch (e: Exception) {
                QuickSearch.logger.info("No ponder screen found, falling back a layer!")
                skip();
            }
        }
    }
}
*///?}