package io.github.cootshk.quicksearch.math

import net.objecthunter.exp4j.function.Function

object ItemToStackFunction : Function("itemsof", 1) {
    override fun apply(vararg args: Double): Double {
        return args[0] * 64F
    }
}