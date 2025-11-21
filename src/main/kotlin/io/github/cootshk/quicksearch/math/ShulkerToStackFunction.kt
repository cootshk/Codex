package io.github.cootshk.quicksearch.math

import net.objecthunter.exp4j.function.Function

object ShulkerToStackFunction : Function("stacksin", 1) {
    override fun apply(vararg args: Double): Double {
        return args[0] * 27F
    }
}
