package io.github.mwttg.pixelartillery2d.cleanup;

import io.github.mwttg.pixelartillery2d.graphic.OpenGlCleanUp;

public final class CleanUp {

    private CleanUp() {
    }

    public static void purge() {
        OpenGlCleanUp.purge();
    }
}
