package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.FloatBuffer;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which can be used for uploading data (like: view Matrix, projection Matrix, Texture) to
 * the GPU for an instanced {@link ShaderProgram}.
 */
public class InstancedUniform {

  private static final Logger LOG = LoggerFactory.getLogger(InstancedUniform.class);

  private static final String VIEW_MATRIX = "viewMatrix";
  private static final String PROJECTION_MATRIX = "projectionMatrix";
  private static final String TEXTURE_SAMPLER0 = "textureSampler0";
  private static final int CAPACITY = 16;

  private final Map<String, Integer> locations;
  private final FloatBuffer matrixBuffer;

  private InstancedUniform(final Map<String, Integer> locations) {
    this.locations = locations;
    this.matrixBuffer = BufferUtils.createFloatBuffer(CAPACITY);
  }

  /**
   * Creates an {@link InstancedUniform} for an instanced {@link ShaderProgram}. The
   * InstancedUniform is used to upload data (like View-Matrix, Texture, etc.= to the GPU, so it can
   * be used inside the Shader (e.g. instanced Vertex Shader or Fragment Shader). The upload happens
   * right before the {@link InstancedStaticSprite} gets rendered.
   *
   * @param shaderProgramId the OpenGL id of the {@link ShaderProgram}
   * @return the {@link InstancedUniform}
   */
  public static InstancedUniform create(final int shaderProgramId) {
    final Map<String, Integer> locations =
        Map.of(
            VIEW_MATRIX, GL41.glGetUniformLocation(shaderProgramId, VIEW_MATRIX),
            PROJECTION_MATRIX, GL41.glGetUniformLocation(shaderProgramId, PROJECTION_MATRIX),
            TEXTURE_SAMPLER0, GL41.glGetUniformLocation(shaderProgramId, TEXTURE_SAMPLER0));
    LOG.info("create InstancedUniform for ShaderProgram with id='{}'", shaderProgramId);
    return new InstancedUniform(locations);
  }

  void upload(final Matrix4f viewMatrix, final Matrix4f projectionMatrix, final int textureId) {
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
