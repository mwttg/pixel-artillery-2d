package io.github.mwttg.pixelartillery2d.sound;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public final class Sound {

    private final int soundSourceId;

    private Sound(final int soundSourceId) {
        this.soundSourceId = soundSourceId;
    }

    public static Sound create(final String filename) {
        return create(filename, false);
    }

    public static Sound create(final String filename, final boolean loop) {
        final var soundFileData = WavFile.readFrom(filename);
        final var soundBufferId = SoundBuffer.create(soundFileData);
        final var soundSourceId = SoundSource.create(soundBufferId, loop, new Vector3f());

        return new Sound(soundSourceId);
    }

    public void play() {
        AL11.alSourcePlay(soundSourceId);
    }

    public void pause() {
        AL11.alSourcePause(soundSourceId);
    }

    public void stop() {
        AL11.alSourceStop(soundSourceId);
    }

    public boolean isPlaying() {
        return AL11.alGetSourcei(soundSourceId, AL11.AL_SOURCE_STATE) == AL11.AL_PLAYING;
    }

    public void updatePosition(final Vector3f position) {
        AL11.alSource3f(soundSourceId, AL11.AL_POSITION, position.x(), position.y(), position.z());
    }

    public void updatePosition(final Matrix4f modelMatrix) {
        final var result = new Vector3f();
        modelMatrix.getTranslation(result);
        updatePosition(result);
    }

    public void setVelocity(final Vector3f velocity) {
        AL11.alSource3f(soundSourceId, AL11.AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
    }

    public void setGain(float gain) {
        AL11.alSourcef(soundSourceId, AL11.AL_GAIN, gain);
    }
}
