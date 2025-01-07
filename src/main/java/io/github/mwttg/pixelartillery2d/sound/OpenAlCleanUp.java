package io.github.mwttg.pixelartillery2d.sound;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A class with a method to clean up all the OpenAL stuff (Buffers, Devices, etc.) */
public final class OpenAlCleanUp {

  private static final Logger LOG = LoggerFactory.getLogger(OpenAlCleanUp.class);

  private static long deviceId;
  private static final List<Integer> soundBufferIds = new ArrayList<>();
  private static final List<Integer> soundSourceIds = new ArrayList<>();

  private OpenAlCleanUp() {}

  static void addDeviceId(final long id) {
    deviceId = id;
  }

  static void addSoundBufferId(final int id) {
    soundBufferIds.add(id);
  }

  static void addSoundSourceId(final int id) {
    soundSourceIds.add(id);
  }

  /** Clean up all OpenAL stuff. */
  public static void purge() {
    LOG.info("clean up OpenAL");
    LOG.debug("... free sound sources");
    soundBufferIds.forEach(AL11::alDeleteSources);
    LOG.debug("... free sound buffers");
    soundSourceIds.forEach(AL11::alDeleteBuffers);
    LOG.debug("... close sound device");
    ALC11.alcCloseDevice(deviceId);
  }
}
