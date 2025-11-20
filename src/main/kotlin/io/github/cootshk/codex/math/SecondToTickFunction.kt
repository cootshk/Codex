package io.github.cootshk.codex.math

import net.objecthunter.exp4j.function.Function

object SecondToTickFunction : Function("ticksin", 1) {
    override fun apply(vararg args: Double): Double {
        return args[0] * 20F
    }
}
