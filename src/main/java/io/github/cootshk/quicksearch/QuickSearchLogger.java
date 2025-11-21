package io.github.cootshk.quicksearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuickSearchLogger {
    private static final Logger logger = LogManager.getLogger(QuickSearch.class);

    public static Logger getLogger() {
        return logger;
    }
}
