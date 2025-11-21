package io.github.cootshk.quicksearch.impl;

import net.minecraft.text.Text;

public interface Searchable {
    Text getName();
    default String searchString() {
        return getName().getString();
    }
}
