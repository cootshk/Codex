package io.github.cootshk.quicksearch.impl;

import io.github.cootshk.quicksearch.api.SearchableType;
import net.minecraft.network.chat.Component;

public interface Searchable {
    Component quickSearch$getName();
    String quickSearch$getIdentifier();
    SearchableType quickSearch$getSearchableType();

    default Component getName() {
        return quickSearch$getName();
    }
    default String getSearchString() {
        return getName().getString();
    }
    default String getIdentifier() {
        return quickSearch$getIdentifier();
    }
    default SearchableType getType() {
        return quickSearch$getSearchableType();
    }
}
