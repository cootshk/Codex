package io.github.cootshk.quicksearch;

import io.github.cootshk.quicksearch.api.QuickSearchHandler;
import io.github.cootshk.quicksearch.compat.QuickSearchCompatLoader;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Fabric wouldn't load this if it was in Kotlin.
public class QuickSearch implements ModInitializer {
    public static Logger logger = LogManager.getLogger();
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        QuickSearchCompatLoader.load();
    }

    public static void addCompat(QuickSearchHandler compat) {
    }
}
