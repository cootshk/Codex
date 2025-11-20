package io.github.cootshk.codex.impl;

public interface CodexSearchable {
    String codex$getSearchString();
    default String searchString() {
        return codex$getSearchString();
    }
}
