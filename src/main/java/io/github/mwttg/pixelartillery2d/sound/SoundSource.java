package io.github.mwttg.pixelartillery2d.sound;

import org.joml.Vector3f;
import org.lwjgl.openal.AL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SoundSource {

  private static final Logger LOG = LoggerFactory.getLogger(SoundSource.class);

  static int create(final int bufferId, final boolean loop, final Vector3f position) {
    final var id = AL11.alGenSources();
    LOG.debug("... create SoundSource with id='{}'", id);
    OpenAlCleanUp.addSoundSourceId(id);
    if (loop) {
      AL11.alSourcei(id, AL11.AL_LOOPING, AL11.AL_TRUE);
    }
    AL11.alSourcei(id, AL11.AL_BUFFER, bufferId);
    AL11.alSource3f(id, AL11.AL_POSITION, position.x(), position.y(), position.z());

    return id;
  }
}
