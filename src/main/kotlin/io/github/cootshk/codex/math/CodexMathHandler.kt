package io.github.cootshk.codex.math

import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs

object CodexMathHandler {

    @JvmStatic
    val functions = listOf(
        ItemToStackFunction, StackToItemFunction,
        SecondToTickFunction, TickToSecondFunction,
        ShulkerToStackFunction, StackToShulkerFunction
    )

    @JvmStatic
    fun evaluate(line: String): String {
        if (line.isBlank()) return ""
        val result: Double
        try {
        // Strip the `=` BEFORE passing it to evaluate
            val exp = ExpressionBuilder(line)
                .functions(functions)
                .build()
            result = exp.evaluate()
        } catch (_: RuntimeException) {
            return "?"
        }
        return if (result.isNaN()) {
            "NaN"
        } else if (result.isInfinite()) {
            "Infinity"
        } else if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else if (abs(result) < 1E-6) {
            return "0"
        } else {
            result.toString()
        }
    }
}