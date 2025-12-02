package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.impl.Searchable
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
//? if >1.20 {
import net.minecraft.registry.Registries
//?} else {
/*import net.minecraft.util.registry.Registry
*///?}

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
        //? if >1.20 {
        return Registries.ITEM
        //?} else {
        /*return Registry.ITEM
        *///?}
            .toList().toTypedArray().associateBy { item ->
                searchString(item)
            }
    }
    @JvmStatic
    fun lookupEntities(): Map<String, EntityType<*>> {
        //? if >1.20 {
        return Registries.ENTITY_TYPE
        //?} else {
        /*return Registry.ENTITY_TYPE
        *///?}
            .filter { entityType ->
                !entityType.spawnGroup.equals(SpawnGroup.MISC)
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
