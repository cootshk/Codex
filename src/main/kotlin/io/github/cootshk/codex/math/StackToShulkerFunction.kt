package io.github.cootshk.codex.math

import net.objecthunter.exp4j.function.Function
import kotlin.math.ceil

object StackToShulkerFunction : Function("shulkersof", 1) {
    override fun apply(vararg args: Double): Double {
        return ceil(args[0] / 27f)
    }
}