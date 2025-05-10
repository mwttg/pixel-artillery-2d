package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.FloatBuffer;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which can be used for uploading data (like: model Matrix, view Matrix, Texture) to the
 * GPU for the {@link ShaderProgram}.
 */
public final class Uniform {

  private static final Logger LOG = LoggerFactory.getLogger(Uniform.class);

  private static final String MODEL_MATRIX = "modelMatrix";
  private static final String VIEW_MATRIX = "viewMatrix";
  private static final String PROJECTION_MATRIX = "projectionMatrix";
  private static final String TEXTURE_SAMPLER0 = "textureSampler0";
  private static final int CAPACITY = 16;

  private final Map<String, Integer> locations;
  private final FloatBuffer matrixBuffer;

  private Uniform(final Map<String, Integer> locations) {
    this.locations = locations;
    this.matrixBuffer = BufferUtils.createFloatBuffer(CAPACITY);
  }

  /**
   * Creates an {@link Uniform} for a {@link ShaderProgram}. The Uniform is used to upload data
   * (like Model-Matrix, Texture, etc.) to the GPU, so it can be used inside the Shader (e.g. Vertex
   * Shader or Fragment Shader). The 'Upload' happens right before the Sprite gets rendered.
   *
   * @param shaderProgramId the OpenGL id of the {@link ShaderProgram}
   * @return the {@link Uniform}
   */
  public static Uniform create(final int shaderProgramId) {
    final Map<String, Integer> locations =
        Map.of(
            MODEL_MATRIX, GL41.glGetUniformLocation(shaderProgramId, MODEL_MATRIX),
            VIEW_MATRIX, GL41.glGetUniformLocation(shaderProgramId, VIEW_MATRIX),
            PROJECTION_MATRIX, GL41.glGetUniformLocation(shaderProgramId, PROJECTION_MATRIX),
            TEXTURE_SAMPLER0, GL41.glGetUniformLocation(shaderProgramId, TEXTURE_SAMPLER0));
    LOG.info("create Uniform for ShaderProgram with id='{}'", shaderProgramId);
    return new Uniform(locations);
  }

  void upload(
      final Matrix4f modelMatrix,
      final Matrix4f viewMatrix,
      final Matrix4f projectionMatrix,
      final int textureId) {
    GL41.glUniformMatrix4fv(locations.get(MODEL_MATRIX), false, modelMatrix.get(matrixBuffer));
    GL41.glUniformMatrix4fv(locations.get(VIEW_MATRIX), false, viewMatrix.get(matrixBuffer));
    GL41.glUniformMatrix4fv(
        locations.get(PROJECTION_MATRIX), false, projectionMatrix.get(matrixBuffer));
    activateTexture0(locations.get(TEXTURE_SAMPLER0), textureId);
  }

  private void activateTexture0(final int locationId, final int textureId) {
    GL41.glUniform1i(locationId, 0);
    GL41.glActiveTexture(GL41.GL_TEXTURE0);
    GL41.glBindTexture(GL41.GL_TEXTURE_2D, textureId);
  }
}
