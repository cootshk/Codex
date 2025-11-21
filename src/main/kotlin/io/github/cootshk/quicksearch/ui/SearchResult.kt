package io.github.cootshk.quicksearch.ui

import io.github.cootshk.quicksearch.impl.Searchable
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.HorizontalAlignment
import io.wispforest.owo.ui.core.Sizing
import io.wispforest.owo.ui.core.VerticalAlignment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item

class SearchResult : FlowLayout {
    val name: Component
    constructor(entry: Searchable) :
        super(Sizing.fixed(195), Sizing.content(), Algorithm.HORIZONTAL) {
        val component: Component = when (entry) {
            is Item -> of(entry)
            is EntityType<*> -> of(entry)
            else -> TODO("implement")
        }

        this.alignment(HorizontalAlignment.LEFT, VerticalAlignment.CENTER)

        this.name = Components.label(entry.name)
        this.children(listOf(component, this.name))
    }

    private companion object {
        val horizontalSize: Sizing get() = Sizing.fixed(20)
        val verticalSize: Sizing get() = Sizing.fixed(20)

        private fun of(item: Item): Component {
            val itemComponent = Components.item(item.defaultStack)
                .sizing(horizontalSize, verticalSize)
            return itemComponent
        }

        private fun of(entityType: EntityType<*>): Component {
            val entityComponent = Components.entity(Sizing.content(), entityType, null)
                .scaleToFit(true)
                .sizing(horizontalSize, verticalSize)
            return entityComponent
        }
    }
}
