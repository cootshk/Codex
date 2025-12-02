package io.github.cootshk.quicksearch.util

import io.github.cootshk.quicksearch.client.QuickSearchClient
import java.io.File

object History {
    private const val FILE = "moddata/quicksearch_history.txt"

    private fun maxLines(): Int {
        return QuickSearchClient.config.maxSearchResults()
    }

    fun saveQuery(query: String) {
        if (query.isBlank()) return
        val history = loadHistory(1)
        if (history.isNotEmpty() && history[0] == query) return
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

    /**
     * Loads the most recent [entries] search queries from history. If there are fewer than [entries] queries,
     * all available queries will be returned.
     *
     * Warning: this function can return an empty list if no history file exists.
     * @param entries The number of recent entries to load.
     * @return A list of recent search queries, with the most recent first.
     */
    fun loadHistory(entries: Int): List<String> {
        val file = File(FILE)
        if (!file.exists()) {
            return emptyList()
        }
        return file.readLines().takeLast(entries).reversed().filter { entry -> entry.isNotBlank() }
    }
}