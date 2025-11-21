package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.impl.Searchable
import net.minecraft.entity.EntityType
import net.minecraft.entity.Ownable
import net.minecraft.item.Item
import net.minecraft.registry.Registries

object RegistryLookup {

    @JvmStatic
    fun searchString(c: Any): String {
        return (c as Searchable).searchString()
    }

    @JvmStatic
    val items = lookupItems()
    // TODO: entities?
    @JvmStatic
    val entities = lookupEntities()

    @JvmStatic
    val all = lookup()

    @JvmStatic
    fun lookupItems(): Map<String, Item> {
        return Registries.ITEM.toList().toTypedArray().associateBy { item ->
            searchString(item)
        }
    }
    @JvmStatic
    fun lookupEntities(): Map<String, EntityType<*>> {
        return Registries.ENTITY_TYPE.filter { entityType ->
            // TODO: fix this (doesn't filter anything currently)
            !entityType.baseClass.isAssignableFrom(Ownable::class.java)
        }.associateBy { entityType ->
            searchString(entityType)
        }
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun lookup(): Map<String, Searchable> {
        return (items as Map<String, Searchable>) + (entities as Map<String, Searchable>)
    }
}
