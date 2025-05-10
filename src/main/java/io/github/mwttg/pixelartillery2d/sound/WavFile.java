package io.github.mwttg.pixelartillery2d.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.lwjgl.openal.AL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class WavFile {

  private static final Logger LOG = LoggerFactory.getLogger(WavFile.class);

  private WavFile() {}

  static SoundData readFrom(final String filename) {
    LOG.info("read WavFile: '{}'", filename);
    final File file = new File(filename);
    try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(file)) {
      final byte[] data = inputStream.readAllBytes();
      final AudioFormat format = inputStream.getFormat();
      final int openAlFormat = getOpenAlFormat(format);
      final int sampleRate = (int) format.getSampleRate();
      return new SoundData(data, openAlFormat, sampleRate);
    } catch (final UnsupportedAudioFileException e) {
      throw new RuntimeException(
          "The sound file '%s' was not a .wav audio file".formatted(filename));
    } catch (final IOException e) {
      throw new RuntimeException("The file '%s' could not opened".formatted(filename));
    }
  }

  private static int getOpenAlFormat(final AudioFormat format) {
    final int channels = format.getChannels();
    final int sampleSize = format.getSampleSizeInBits();
    final Pair tuple = new Pair(channels, sampleSize);
    return switch (tuple) {
      case Pair(int c, int s) when (c == 1) && (s == 8) -> AL11.AL_FORMAT_MONO8;
      case Pair(int c, int s) when (c == 1) && (s == 16) -> AL11.AL_FORMAT_MONO16;
      case Pair(int c, int s) when (c == 2) && (s == 8) -> AL11.AL_FORMAT_STEREO8;
      case Pair(int c, int s) when (c == 2) && (s == 16) -> AL11.AL_FORMAT_STEREO16;
      default ->
          throw new IllegalArgumentException(
              "Unsupported format channels = '%s', sampleSize = '%s' bit"
                  .formatted(channels, sampleSize));
    };
  }

  private record Pair(int channels, int sampleSize) {}
}
