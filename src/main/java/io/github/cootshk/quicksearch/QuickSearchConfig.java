package io.github.cootshk.quicksearch;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId = "quicksearch")
@Config(name = "quicksearch", wrapperName = "QSConfig")
public class QuickSearchConfig {
    @RangeConstraint(min = 0.0, max = 100.0, decimalPlaces = 0)
    public int maxSearchResults = 100;
}