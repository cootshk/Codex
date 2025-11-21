package io.github.cootshk.quicksearch.impl;

public interface Searchable {
    String quickSearch$getSearchString();
    default String searchString() {
        return quickSearch$getSearchString();
    }
}
