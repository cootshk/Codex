package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.QuickSearch
import java.io.File

object History {
    private const val FILE = "moddata/quicksearch_history.txt"

    private fun maxLines(): Int {
        return QuickSearch.config.maxSearchResults()
    }

    fun saveQuery(query: String) {
        if (query.isBlank()) return
        if (loadHistory(1)[0] == query) return
        val file = File(FILE)
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            file.createNewFile()
        }
        file.appendText(query + "\n")
        if (file.readLines().size > maxLines()) {
            val lines = file.readLines().takeLast(maxLines())
            file.writeText(lines.joinToString("\n") + "\n")
        }
    }

    fun loadHistory(): List<String> {
        return loadHistory(maxLines())
    }
    fun loadHistory(entries: Int): List<String> {
        val file = File(FILE)
        if (!file.exists()) {
            return emptyList()
        }
        return file.readLines().takeLast(entries).reversed()
    }
}