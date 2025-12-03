package io.github.cootshk.quicksearch.api

import io.github.cootshk.quicksearch.NextHandler
import io.github.cootshk.quicksearch.impl.Searchable
import net.fabricmc.loader.api.FabricLoader
import kotlin.math.max

abstract class QuickSearchHandler {
    abstract fun initialize()

    internal fun register(match: Regex?, matchClass: SearchableType?, handler: (Searchable) -> Unit) {
        register(match, matchClass, getHighestPriority(1000), handler)
    }
    internal fun register(match: Regex?, handler: (Searchable) -> Unit) {
        register(match, null, handler)
    }
    internal fun register(match: Regex?, matchClass: SearchableType?, priority: Int, handler: (Searchable) -> Unit) {
        addHandler(priority, HandlerEntry(match ?: Regex(".*"), matchClass, handler))
    }
    internal fun register(match: Regex?, priority: Int, handler: (Searchable) -> Unit) {
        register(match, null, priority, handler)
    }
    internal fun register(namespace: String,  matchClass: SearchableType?, handler: (Searchable) -> Unit) {
        register(match(namespace), matchClass, highestPriority, handler)
    }
    internal fun register(namespace: String, handler: (Searchable) -> Unit) {
        register(namespace, null, handler)
    }
    internal fun register(namespace: String, matchClass: SearchableType?, priority: Int, handler: (Searchable) -> Unit) {
        register(match(namespace), matchClass, priority, handler)
    }
    internal fun register(namespace: String, priority: Int, handler: (Searchable) -> Unit) {
        register(namespace, null, priority, handler)
    }

    internal fun skip() { throw NextHandler() }

    companion object {
        var handlers: Map<Int, HandlerEntry> = mutableMapOf()
            private set(value) {
                field = value.toSortedMap(compareByDescending { it })
            }
        private val highestPriority: Int
            get() = (handlers.keys.lastOrNull() ?: -1) + 1
        private fun addHandler(priority: Int, entry: HandlerEntry) {
            // To ensure no duplicate priorities exist, we recreate the map
            var entry = entry
            val mutable = handlers.toMutableMap()
            var target: Int = priority
            while (true) {
                if (!mutable.containsKey(target)) {
                    mutable[target] = entry
                    break
                }
                // Shift up
                val existing = mutable[target]!!
                mutable[target] = entry
                entry = existing
                target += 1
                if (target == Int.MAX_VALUE) {
                    throw IllegalStateException("Attempted to register a handler but no available priority slots exist. (Try lowering the priority of some handlers?)")
                }
            }
            handlers = mutable
        }
        private fun match(namespace: String): Regex {
            return Regex("^${Regex.escape(namespace.removeSuffix(":"))}:.*$")
        }
        fun getHighestPriority(min: Int): Int {
            return max(min, highestPriority)
        }
    }
}