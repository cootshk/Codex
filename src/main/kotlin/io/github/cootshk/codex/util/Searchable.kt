package io.github.cootshk.codex.util

import io.github.cootshk.codex.ui.CodexSearchResult
import io.wispforest.owo.ui.core.Component
import net.minecraft.item.Item
import net.minecraft.text.Text

data class Searchable(val name: Text, private val _description: Text?, val render: Component) {
    constructor(name: Text, render: Component) : this(name, null, render)
    val description get() = _description ?: Text.translatable("codex.description.default") as Text
    override fun toString(): String {
        return description.toString()
    }

    constructor(item: Item) : this(
        item.name,
        Text.of(Text.translatable("codex.description.default")),
        CodexSearchResult.of(item)
    )
}
