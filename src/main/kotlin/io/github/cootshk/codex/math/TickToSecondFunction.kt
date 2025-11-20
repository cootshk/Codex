package io.github.cootshk.codex.math

import net.objecthunter.exp4j.function.Function
import kotlin.math.ceil

object TickToSecondFunction : Function("secsin", 1) {
    override fun apply(vararg args: Double): Double {
        return ceil(args[0] / 20f)
    }
}