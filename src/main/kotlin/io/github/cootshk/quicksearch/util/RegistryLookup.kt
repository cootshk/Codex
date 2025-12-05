package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.impl.Searchable
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.Item
import net.minecraft.core.registries.BuiltInRegistries

object RegistryLookup {

    @JvmStatic
    fun searchString(c: Any): String {
        return (c as Searchable).searchString
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
        return BuiltInRegistries.ITEM.toList().toTypedArray().associateBy { item ->
            searchString(item)
        }
    }
    @JvmStatic
    fun lookupEntities(): Map<String, EntityType<*>> {
        return BuiltInRegistries.ENTITY_TYPE.filter { entityType ->
            !entityType.category.equals(MobCategory.MISC)
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
