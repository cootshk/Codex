package io.github.cootshk.codex.util

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.registry.Registries

object RegistryLookup {

    @JvmStatic
    var items = lookupItems()
    // TODO: entities?
    @JvmStatic
    var entities = lookupEntities()

    private fun testLookup(items: Array<*>) {
        println("Testing array...")
        println("Count: ${items.count()}")
        println("First: ${items.first()}")
        println("Last: ${items.last()}")
    }

    fun lookupItems(): Array<Item> {
        return Registries.ITEM.toList().toTypedArray()
    }
    fun lookupEntities(): Array<EntityType<Entity>> {
        return Registries.ENTITY_TYPE.filter { entityType ->
            entityType.isSaveable
        }.toList().toTypedArray() as Array<EntityType<Entity>>
    }
}