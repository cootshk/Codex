package io.github.cootshk.quicksearch;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId = "quicksearch")
@Config(name = "quicksearch", wrapperName = "QSConfig")
public class QuickSearchConfig {
    //? if >1.20 {
    @RangeConstraint(min = 0.0, max = 100.0, decimalPlaces = 0)
    //?} else {
     /*@RangeConstraint(min = 0, max = 100)
    *///?}
    int maxSearchResults = 100;
}