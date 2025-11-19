package io.github.cootshk.codex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CodexLogger {
    private static final Logger logger = LogManager.getLogger(Codex.class);

    public static Logger getLogger() {
        return logger;
    }
}
