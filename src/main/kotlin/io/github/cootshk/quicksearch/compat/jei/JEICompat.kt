package io.github.cootshk.quicksearch.compat.jei

import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType

object JEICompat : QuickSearchHandler("jei") {
    override fun initialize() {
        register(null, SearchableType.ITEM, Int.MIN_VALUE + 2) {
            TODO("Write the JEI plugin code before this")
        }
    }
}