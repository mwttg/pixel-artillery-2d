package io.github.mwttg.pixelartillery2d.sound;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

/**
 * A class that represents a sound, which can be played with OpenAL
 */
public class Sound {

    private final int soundSourceId;

    private Sound(final int soundSourceId) {
        this.soundSourceId = soundSourceId;
    }

    /**
     * Creates a {@link Sound} from a .wav file, which can be later played without looping it.
     *
     * @param filename the path and filename of the .wav file
     * @return the {@link Sound}
     */
    public static Sound create(final String filename) {
        return create(filename, false);
    }

    /**
     * Creates a {@link Sound} from a .wav file.
     *
     * @param filename the path and filename of the .wav file
     * @param loop a boolean for playing the sound in a loop
     * @return the {@link Sound}
     */
    public static Sound create(final String filename, final boolean loop) {
        final var soundFileData = WavFile.readFrom(filename);
        final var soundBufferId = SoundBuffer.create(soundFileData);
        final var soundSourceId = SoundSource.create(soundBufferId, loop, new Vector3f());

        return new Sound(soundSourceId);
    }

    /**
     * Play the {@link Sound}.
     */
    public void play() {
        AL11.alSourcePlay(soundSourceId);
    }

    /**
     * Pause the {@link Sound}.
     */
    public void pause() {
        AL11.alSourcePause(soundSourceId);
    }

    /**
     * Stop the {@link Sound}.
     */
    public void stop() {
        AL11.alSourceStop(soundSourceId);
    }

    /**
     * Check if the {@link Sound}.is playing.
     *
     * @return true or false
     */
    public boolean isPlaying() {
        return AL11.alGetSourcei(soundSourceId, AL11.AL_SOURCE_STATE) == AL11.AL_PLAYING;
    }

    /**
     * Update the position of the {@link Sound}. The default position is {0.0f, 0.0f, 0.0f}.
     *
     * @param position the new position as a {@link Vector3f}
     */
    public void updatePosition(final Vector3f position) {
        AL11.alSource3f(soundSourceId, AL11.AL_POSITION, position.x(), position.y(), position.z());
    }

    /**
     * Update the position of the {@link Sound}. The position gets extracted from the model matrix.
     * The default position is {0.0f, 0.0f, 0.0f}
     *
     * @param modelMatrix the model matrix of the object which should have the sound
     */
    public void updatePosition(final Matrix4f modelMatrix) {
        final var result = new Vector3f();
        modelMatrix.getTranslation(result);
        updatePosition(result);
    }

    /**
     * Set the velocity of the object which has the {@link Sound}. It is used for the Doppler-effect.
     * Keep in mind you still have to update the position. The default velocity is {0.0f, 0.0f, 0.0f}
     *
     * @param velocity the velocity as a {@link Vector3f}
     */
    public void setVelocity(final Vector3f velocity) {
        AL11.alSource3f(soundSourceId, AL11.AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
    }

    /**
     * That the gain of the {@link Sound}. The default gain is 1.0f.
     *
     * @param gain the gain as float
     */
    public void setGain(float gain) {
        AL11.alSourcef(soundSourceId, AL11.AL_GAIN, gain);
    }
}
