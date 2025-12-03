package io.github.cootshk.quicksearch.compat.jei

import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.api.SearchableType
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.ingredients.IIngredientType
import mezz.jei.api.ingredients.ITypedIngredient
import mezz.jei.api.recipe.IFocusFactory
import mezz.jei.api.recipe.RecipeIngredientRole
import net.minecraft.world.item.Item
import java.util.Optional

object JEICompat : QuickSearchHandler() {
    override fun initialize() {
        register(null, SearchableType.ITEM, Int.MIN_VALUE + 2) { i ->
            try {
                val focus = JEIPlugin.factory.createFocus(RecipeIngredientRole.OUTPUT,VanillaTypes.ITEM_STACK, (i as Item).defaultInstance)
                JEIPlugin.jeiRuntime.recipesGui.show(focus)
            } catch (e: Exception) {
                e.printStackTrace()
                skip()
            }
        }
    }
}