package io.github.cootshk.quicksearch.util

import io.wispforest.owo.ui.core.Color

object Colors {
    // generally, only dynamically filled colors should go here.
    // The rest go directly in the XML
    val BG = color(0x1D1D1D)
    val ELEVATED = color(0x2D3134)
    val ELEVATED_HOVER = color(0x54585C)
    val ACCENT_NONE = color(0x191414)
    val ACCENT_MATH = color(0x239CBA)
    val ACCENT_SEARCH = color(0x7423BA)

    private fun color(c: Int): Color {
        return Color.ofRgb(c)
    }
}
