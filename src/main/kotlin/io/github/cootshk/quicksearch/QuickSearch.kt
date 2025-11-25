package io.github.cootshk.quicksearch

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object QuickSearch : ModInitializer {

    @JvmStatic
    val config = QSConfig.createAndLoad()!!;
    @JvmStatic
    val logger: Logger = LogManager.getLogger()
    override fun onInitialize() {
    }
}
