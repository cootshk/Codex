package io.github.cootshk.quicksearch.ui

import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Sizing
import net.minecraft.entity.EntityType
import net.minecraft.item.Item

object SearchResult {

    val horizontalSize: Sizing get() = Sizing.fixed(20)
    val verticalSize: Sizing get() = Sizing.fixed(20)

    fun of(item: Item): Component {
        val itemComponent = Components.item(item.defaultStack)
            .sizing(horizontalSize, verticalSize)
        return itemComponent
    }
    fun of(entityType: EntityType<*>): Component {
        val entityComponent = Components.entity(Sizing.content(), entityType, null)
            .sizing(horizontalSize, verticalSize)
        return entityComponent
    }

    fun <T> from(entry: T): Component {
        if (entry == null) { throw NullPointerException() }
        when (entry) {
            is Item -> {
                return of(entry)
            }
            is EntityType<*> -> {
                return of(entry)
            }
        }
        TODO("implement")
    }
}