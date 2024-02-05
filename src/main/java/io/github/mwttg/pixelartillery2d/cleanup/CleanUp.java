package io.github.mwttg.pixelartillery2d.cleanup;

import io.github.mwttg.pixelartillery2d.graphic.OpenGlCleanUp;
import io.github.mwttg.pixelartillery2d.sound.OpenAlCleanUp;

/**
 * A class with a method to clean up all the OpenGL and OpenAL stuff
 */
public final class CleanUp {

    private CleanUp() {
    }

    /**
     * Clean up everything.
     */
    public static void purge() {
        OpenGlCleanUp.purge();
        OpenAlCleanUp.purge();
    }
}
