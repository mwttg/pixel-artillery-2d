package io.github.mwttg.pixelartillery2d.sound;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SoundBuffer {

  private static final Logger LOG = LoggerFactory.getLogger(SoundBuffer.class);

  private SoundBuffer() {}

  static int create(final SoundData soundFileData) {
    final var data = BufferUtils.createByteBuffer(soundFileData.data().length);
    data.put(soundFileData.data());
    data.flip();

    final var id = AL11.alGenBuffers();
    OpenAlCleanUp.addSoundBufferId(id);
    AL11.alBufferData(id, soundFileData.openAlFormat(), data, soundFileData.sampleRate());
    LOG.debug("... create SoundBuffer with id='{}'", id);

    return id;
  }
}
