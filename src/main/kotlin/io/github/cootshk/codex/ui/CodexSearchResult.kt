package io.github.cootshk.codex.ui

import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Sizing
import net.minecraft.item.Item

object CodexSearchResult {

    fun of(item: Item): Component {
        val itemComponent = Components.item(item.defaultStack)
            .sizing(Sizing.content(), Sizing.fill(100))
        return itemComponent
    }
    fun <T> from(entry: T): Component {
        if (entry == null) { throw NullPointerException() }
        TODO("implement")
    }
}