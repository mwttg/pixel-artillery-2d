package io.github.mwttg.pixelartillery2d.graphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class TextFile {

  private static final Logger LOG = LoggerFactory.getLogger(TextFile.class);

  private TextFile() {}

  static String readFrom(final String filename) {
    checkPreconditions(filename);

    final Path path = Paths.get(filename);
    try (final Stream<String> lines = Files.lines(path)) {
      LOG.info("read TextFile '{}'", filename);
      return lines.collect(Collectors.joining("\n"));
    } catch (IOException e) {
      LOG.error(
          "An error occurred during reading the text file '{}'. The Exception was: ", filename, e);
      throw new RuntimeException(e);
    }
  }

  static String readFromResources(final String filename) {
    checkPreconditions(filename);

    try (final InputStream stream = TextFile.class.getResourceAsStream(filename)) {
      if (stream == null) {
        throw new RuntimeException(
            "Unable to get stream to resource file '%s'".formatted(filename));
      }
      final BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
      LOG.info("read TextFile from resources '{}'", filename);
      return bufferedReader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      LOG.error(
          "An error occurred during reading the text file '{}' from resources folder. The Exception"
              + " was: ",
          filename,
          e);
      throw new RuntimeException(e);
    }
  }

  private static void checkPreconditions(final String filename) {
    assert filename != null : "filename for reading a TextFile was null";
    assert !filename.isEmpty() : "filename for reading a TextFile was empty";
  }
}
