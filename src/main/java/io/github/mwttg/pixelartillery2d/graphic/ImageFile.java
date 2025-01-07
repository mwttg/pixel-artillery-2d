package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ImageFile {

  private static final Logger LOG = LoggerFactory.getLogger(ImageFile.class);

  private ImageFile() {}

  static Image readFrom(final String filename) {
    assert filename != null : "filename for reading a ImageFile was null";
    assert !filename.isEmpty() : "filename for reading a ImageFile was empty";

    try (final var stack = MemoryStack.stackPush()) {
      final var width = stack.mallocInt(1);
      final var height = stack.mallocInt(1);
      final var color = stack.mallocInt(1);

      STBImage.stbi_set_flip_vertically_on_load(true);
      final var pixels = STBImage.stbi_load(filename, width, height, color, 4);
      if (pixels == null) {
        LOG.error(
            "An error occurred during reading the ImageFile '{}'. The reason was: {}",
            filename,
            STBImage.stbi_failure_reason());
        throw new RuntimeException("Can't read image '%s'".formatted(filename));
      }

      LOG.info("read ImageFile '{}'", filename);
      return new Image(pixels, width.get(), height.get());
    }
  }
}
