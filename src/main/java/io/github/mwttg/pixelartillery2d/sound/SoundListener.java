package io.github.mwttg.pixelartillery2d.sound;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which represents the sound listener (the receiver) of the played {@link Sound}s. This is usually
 * the camera (view matrix) or the player (model matrix) inside the world.
 * An OpenAL SoundListener exists by default. This class provides methods to manipulate the listener.
 */
public class SoundListener {

//    private static final Logger LOG = LoggerFactory.getLogger(SoundListener.class);

    private SoundListener() {
    }

// TODO delete

//    public static void create(final Vector3f position) {
//        LOG.info("create OpenAL sound listener");
//        AL11.alListener3f(AL11.AL_POSITION, position.x(), position.y(), position.z());
//        AL11.alListener3f(AL11.AL_VELOCITY, 0, 0, 0);
//    }
//
//    public static void create(final Matrix4f matrix) {
//        LOG.info("create OpenAL sound listener");
//        AL11.alListener3f(AL11.AL_VELOCITY, 0, 0, 0);
//        updatePosition(matrix);
//    }

    /**
     * Update the position of the {@link SoundListener}.
     *
     * @param position the position as {@link Vector3f}
     */
    public static void updatePosition(final Vector3f position) {
        AL11.alListener3f(AL11.AL_POSITION, position.x(), position.y(), position.z());
    }

    /**
     * Update the position of the {@link SoundListener} extracted by a model matrix (e.g. from the player).
     *
     * @param modelMatrix the model matrix of the listener
     */
    public static void updatePositionM(final Matrix4f modelMatrix) {
        final var position = new Vector3f();
        modelMatrix.getTranslation(position);
        AL11.alListener3f(AL11.AL_POSITION, position.x(), position.y(), position.z());
    }

    /**
     * Update the position of the {@link SoundListener} extracted from the view matrix (the camera)
     *
     * @param viewMatrix the view matrix
     */
    public static void updatePositionC(final Matrix4f viewMatrix) {
        // http://forum.lwjgl.org/index.php?topic=6080.0
        final var at = new Vector3f();
        viewMatrix.positiveZ(at).negate();
        final var up = new Vector3f();
        viewMatrix.positiveY(up);
        final var data = new float[]{at.x(), at.y(), at.z(), up.x(), up.y(), up.z()};
        AL11.alListenerfv(AL11.AL_ORIENTATION, data);
    }

    /**
     * Update the velocity of the listener (used for Doppler effect, remember to update the position also.
     * OpenAL will not do it for you).
     *
     * @param velocity the velocity as {@link Vector3f}
     */
    public static void updateVelocity(final Vector3f velocity) {
        AL11.alListener3f(AL11.AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
    }
}
