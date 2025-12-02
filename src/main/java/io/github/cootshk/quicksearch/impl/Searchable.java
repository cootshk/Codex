package io.github.cootshk.quicksearch.impl;

import io.github.cootshk.quicksearch.api.SearchableType;
import net.minecraft.text.Text;

public interface Searchable {
    Text quickSearch$getName();
    SearchableType quickSearch$getSearchableType();

    default Text getName() {
        return quickSearch$getName();
    }
    default String getSearchString() {
        return getName().getString();
    }
    default String getIdentifier() {
        return getName().toString();
    }
    default SearchableType getType() {
        return quickSearch$getSearchableType();
    }
}
