package io.github.cootshk.quicksearch;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId = "quicksearch")
@Config(name = "quicksearch", wrapperName = "QSConfig")
public class QuickSearchConfig {
    @RangeConstraint(min = 0.0, max = 1000.0, decimalPlaces = 0)
    public int maxSearchResults = 100;
    @RangeConstraint(min = 0.0, max = 1.0, decimalPlaces = 2)
    public double requiredSearchScore = 0.15;
    @RangeConstraint(min = 1.0, max = 15.0, decimalPlaces = 0)
    public int requiredLetters = 3;
}