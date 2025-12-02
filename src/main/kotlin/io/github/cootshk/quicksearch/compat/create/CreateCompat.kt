package io.github.cootshk.quicksearch.compat.create

//? =1.20.1 {
/*import com.simibubi.create.foundation.gui.ScreenOpener
import com.simibubi.create.foundation.ponder.ui.PonderUI
import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType
import net.minecraft.item.Item

object CreateCompat : QuickSearchHandler() {
    override fun initialize() {
        register("*create*", SearchableType.ITEM, 0) { s ->
            // Open Ponder, if available
            val i = s as Item
            try {
                val ui = PonderUI.of(i.defaultStack)
                ScreenOpener.open(ui)
            } catch (e: Exception) {
                skip();
            }
        }
    }
}
*///?}