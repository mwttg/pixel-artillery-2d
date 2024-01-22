package io.github.mwttg.pixelartillery;

import io.github.mwttg.pixelartillery.config.OpenGlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixelArtillery {

    private static final Logger LOG = LoggerFactory.getLogger(PixelArtillery.class);

    public static void main(String[] args) {
        final var configuration = new OpenGlConfiguration("Test", 800, 600, true, true, 0.01f, 20.0f);
        LOG.info(configuration.prettyFormat());
        LOG.info("Hello World");
    }
}
