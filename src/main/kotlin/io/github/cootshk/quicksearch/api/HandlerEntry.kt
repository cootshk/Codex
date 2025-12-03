package io.github.cootshk.quicksearch.api

import io.github.cootshk.quicksearch.impl.Searchable

data class HandlerEntry(
    val matchRegex: Regex,
    val matchClass: SearchableType?,
    val handler: (Searchable) -> Unit,
) {
    private fun matchesType(searchable: Searchable): Boolean {
        return matchClass == null || searchable.type == matchClass
    }
    fun matches(searchable: Searchable): Boolean {
        return matchesType(searchable) && matchRegex.matches(searchable.identifier)
    }
}