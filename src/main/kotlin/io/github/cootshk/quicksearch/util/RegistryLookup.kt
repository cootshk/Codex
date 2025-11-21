package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.impl.Searchable
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.registry.Registries

object RegistryLookup {

    @JvmStatic
    fun searchString(c: Any): String {
        return (c as Searchable).searchString()
    }

    @JvmStatic
    var all: Map<String, Any> = lookup()

    @JvmStatic
    val items = lookupItems()
    // TODO: entities?
    @JvmStatic
    val entities = lookupEntities()

    @JvmStatic
    fun lookupItems(): Map<String, Item> {
        return Registries.ITEM.toList().toTypedArray().associateBy { item ->
            searchString(item)
        }
    }
    @JvmStatic
    fun lookupEntities(): Map<String, EntityType<*>> {
        return Registries.ENTITY_TYPE.filter { entityType ->
            entityType.isSaveable
        }.associateBy { entityType ->
            searchString(entityType)
        }
    }

    @JvmStatic
    fun lookup(): Map<String, Any> {
        return items + entities
    }
}