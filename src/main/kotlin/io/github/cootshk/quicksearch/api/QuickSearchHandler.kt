package io.github.cootshk.quicksearch.api

import io.github.cootshk.quicksearch.NextHandler
import io.github.cootshk.quicksearch.impl.Searchable

abstract class QuickSearchHandler {

    abstract fun initialize()

    final fun register(match: Regex, matchClass: SearchableType?, handler: (Searchable) -> Unit) {
        register(match, matchClass, getHighestPriority(), handler)
    }
    final fun register(match: Regex, handler: (Searchable) -> Unit) {
        register(match, null, handler)
    }
    final fun register(match: Regex, matchClass: SearchableType?, priority: Int, handler: (Searchable) -> Unit) {
        handlers += priority to HandlerEntry(match, matchClass, handler)
    }
    final fun register(match: Regex, priority: Int, handler: (Searchable) -> Unit) {
        register(match, null, priority, handler)
    }
    final fun register(namespace: String,  matchClass: SearchableType?, handler: (Searchable) -> Unit) {
        register(HandlerEntry.match(namespace), matchClass, handler)
    }
    final fun register(namespace: String, handler: (Searchable) -> Unit) {
        register(namespace, null, handler)
    }
    final fun register(namespace: String, matchClass: SearchableType?, priority: Int, handler: (Searchable) -> Unit) {
        register(HandlerEntry.match(namespace), matchClass, priority, handler)
    }
    final fun register(namespace: String, priority: Int, handler: (Searchable) -> Unit) {
        register(namespace, null, priority, handler)
    }

    final fun skip() { throw NextHandler() }

    companion object {
        var handlers: Map<Int, HandlerEntry> = mutableMapOf()
            private set(value) {
                field = value.toSortedMap(compareByDescending { it })
            }
        private val highestPriority: Int
            get() = (handlers.keys.lastOrNull() ?: -1) + 1
    }
    fun getHighestPriority(): Int {
        return highestPriority
    }
}