package io.github.mwttg.pixelartillery2d.cleanup;

import io.github.mwttg.pixelartillery2d.graphic.OpenGlCleanUp;
import io.github.mwttg.pixelartillery2d.sound.OpenAlCleanUp;

public final class CleanUp {

    private CleanUp() {
    }

    public static void purge() {
        OpenGlCleanUp.purge();
        OpenAlCleanUp.purge();
    }
}
