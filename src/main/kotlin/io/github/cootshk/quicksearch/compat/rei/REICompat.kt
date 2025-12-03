package io.github.cootshk.quicksearch.compat.rei

import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType
import me.shedaniel.rei.api.client.view.ViewSearchBuilder
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.item.Item

object REICompat : QuickSearchHandler("rei") {
    override fun initialize() {
        register(null, SearchableType.ITEM,Int.MIN_VALUE + 3) {
            ViewSearchBuilder.builder().addRecipesFor(EntryStacks.of(it as Item)).open();
        }
    }
}