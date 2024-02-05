package io.github.mwttg.pixelartillery2d.sound;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * A class for initializing the sound device.
 */
public class SoundDevice {

    private static final Logger LOG = LoggerFactory.getLogger(SoundDevice.class);

    private SoundDevice() {
    }

    /**
     * Initializes the sound device and makes it ready to use.
     */
    public static void initialize() {
        final var deviceId = ALC11.alcOpenDevice((ByteBuffer) null);
        OpenAlCleanUp.addDeviceId(deviceId);
        if (deviceId == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        LOG.info("initialize OpenAL SoundDevice with id='{}'", deviceId);

        final var capabilities = ALC.createCapabilities(deviceId);
        final var context = ALC11.alcCreateContext(deviceId, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        ALC11.alcMakeContextCurrent(context);
        AL.createCapabilities(capabilities);
    }
}
