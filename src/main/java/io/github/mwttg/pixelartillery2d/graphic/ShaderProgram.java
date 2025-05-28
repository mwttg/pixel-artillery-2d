package io.github.mwttg.pixelartillery2d.graphic;

import java.util.Map;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A class for creating a ShaderProgram (VertexShader and FragmentShader). */
public final class ShaderProgram {

  private static final Logger LOG = LoggerFactory.getLogger(ShaderProgram.class);

  private static final Map<Integer, String> SHADER_TYPE_TABLE =
      Map.of(
          0x8B31, "GL_VERTEX_SHADER",
          0x8B30, "GL_FRAGMENT_SHADER");

  private ShaderProgram() {}

  /**
   * Creates the default Shader Program (simple drawing static/animated Sprites without effects).
   *
   * @return the OpenGL ID of the Shader Program
   */
  public static int createDefaultShader() {
    final String vertexShaderCode = TextFile.readFromResources("/shader/vertex.glsl");
    final String fragmentShaderCode = TextFile.readFromResources("/shader/fragment.glsl");
    return createShaderProgram(vertexShaderCode, fragmentShaderCode);
  }

  /**
   * Creates the default Instanced Shader Program (simple drawing of multiple (but same) static
   * Sprites without effects).
   *
   * @return the OpenGL ID of the Shader Program
   */
  public static int createDefaultInstancedShader() {
    final String vertexShaderCode = TextFile.readFromResources("/shader/vertex-instanced.glsl");
    final String fragmentShaderCode = TextFile.readFromResources("/shader/fragment.glsl");
    return createShaderProgram(vertexShaderCode, fragmentShaderCode);
  }

  /**
   * Creates the default Instanced Shader Program for Particles (simple drawing of multiple (but
   * same) geometry with defined colors.
   *
   * @return the OpenGL ID of the Shader Program
   */
  public static int createDefaultParticleShader() {
    final String vertexShaderCode = TextFile.readFromResources("/shader/particle-vertex.glsl");
    final String fragmentShaderCode = TextFile.readFromResources("/shader/particle-fragment.glsl");
    return createShaderProgram(vertexShaderCode, fragmentShaderCode);
  }

  /**
   * Creates a Shader Program from a custom Vertex Shader file and a custom Fragment Shader file.
   *
   * @param vertexShaderFile the file with the Vertex Shader source code
   * @param fragmentShaderFile the file with the Fragment Shader source code
   * @return the OpenGL ID of the Shader Program
   */
  public static int createFrom(final String vertexShaderFile, final String fragmentShaderFile) {
    final String vertexShaderCode = TextFile.readFrom(vertexShaderFile);
    final String fragmentShaderCode = TextFile.readFrom(fragmentShaderFile);
    return createShaderProgram(vertexShaderCode, fragmentShaderCode);
  }

  private static int createShaderProgram(
      final String vertexShaderCode, final String fragmentShaderCode) {
    final int vertexShaderId = createShader(GL41.GL_VERTEX_SHADER);
    compileShader(vertexShaderId, vertexShaderCode);

    final int fragmentShaderId = createShader(GL41.GL_FRAGMENT_SHADER);
    compileShader(fragmentShaderId, fragmentShaderCode);

    final int shaderProgramId = GL41.glCreateProgram();
    GL41.glAttachShader(shaderProgramId, vertexShaderId);
    GL41.glAttachShader(shaderProgramId, fragmentShaderId);
    linkShaderProgram(shaderProgramId);
    OpenGlCleanUp.addShaderProgramId(shaderProgramId);
    GL41.glDetachShader(shaderProgramId, vertexShaderId);
    GL41.glDetachShader(shaderProgramId, fragmentShaderId);
    GL41.glDeleteShader(vertexShaderId);
    GL41.glDeleteShader(fragmentShaderId);

    LOG.info("create ShaderProgram with id='{}'", shaderProgramId);
    return shaderProgramId;
  }

  private static void linkShaderProgram(final int shaderProgramId) {
    GL41.glLinkProgram(shaderProgramId);
    if (GL41.glGetProgrami(shaderProgramId, GL41.GL_LINK_STATUS) == GL41.GL_FALSE) {
      throw new RuntimeException(
          "Can't link ShaderProgram with id='%s'. Reason: %s"
              .formatted(shaderProgramId, GL41.glGetProgramInfoLog(shaderProgramId)));
    }
    LOG.debug("... link ShaderProgram with id='{}'", shaderProgramId);
  }

  private static void compileShader(final int shaderId, final String sourceCode) {
    GL41.glShaderSource(shaderId, sourceCode);
    GL41.glCompileShader(shaderId);
    if (GL41.glGetShaderi(shaderId, GL41.GL_COMPILE_STATUS) == GL41.GL_FALSE) {
      throw new RuntimeException(
          "Can't compile Shader with id='%s'. Reason: %s"
              .formatted(shaderId, GL41.glGetProgramInfoLog(shaderId)));
    }
    LOG.debug("... compile Shader with id='{}'", shaderId);
  }

  private static int createShader(final int type) {
    final int id = GL41.glCreateShader(type);
    if (id == 0) {
      throw new RuntimeException(
          "Can't create Shader of type '%s'"
              .formatted(SHADER_TYPE_TABLE.getOrDefault(type, "UNKNOWN")));
    }

    LOG.debug(
        "... create Shader of type: '{}' with id='{}'",
        SHADER_TYPE_TABLE.getOrDefault(type, "UNKNOWN"),
        id);
    return id;
  }
}
