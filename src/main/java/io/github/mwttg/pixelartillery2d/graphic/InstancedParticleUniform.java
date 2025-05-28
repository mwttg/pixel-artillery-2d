package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.FloatBuffer;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which can be used for uploading data (view Matrix and projection Matrix) to the GPU for
 * an instanced particle {@link ShaderProgram}.
 */
public class InstancedParticleUniform {

  private static final Logger LOG = LoggerFactory.getLogger(InstancedParticleUniform.class);

  private static final String VIEW_MATRIX = "viewMatrix";
  private static final String PROJECTION_MATRIX = "projectionMatrix";
  private static final int CAPACITY = 16;

  private final Map<String, Integer> locations;
  private final FloatBuffer matrixBuffer;

  private InstancedParticleUniform(final Map<String, Integer> locations) {
    this.locations = locations;
    this.matrixBuffer = BufferUtils.createFloatBuffer(CAPACITY);
  }

  /**
   * Creates an {@link InstancedParticleUniform} for an instanced {@link ShaderProgram} for
   * particles. The ParticleUniform is used to upload data (like View-Matrix, Projection-Matrix,
   * etc.) to the GPU, so it can be used inside the Shader (e.g. instanced Vertex Shader or Fragment
   * Shader). The upload happens right before the particles gets rendered.
   *
   * @param shaderProgramId the OpenGL id of the {@link ShaderProgram}
   * @return the {@link InstancedParticleUniform}
   */
  public static InstancedParticleUniform create(final int shaderProgramId) {
    final Map<String, Integer> locations =
        Map.of(
            VIEW_MATRIX, GL41.glGetUniformLocation(shaderProgramId, VIEW_MATRIX),
            PROJECTION_MATRIX, GL41.glGetUniformLocation(shaderProgramId, PROJECTION_MATRIX));
    LOG.info("create ParticleUniform for ShaderProgram with id='{}'", shaderProgramId);
    return new InstancedParticleUniform(locations);
  }

  void upload(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    GL41.glUniformMatrix4fv(locations.get(VIEW_MATRIX), false, viewMatrix.get(matrixBuffer));
    GL41.glUniformMatrix4fv(
        locations.get(PROJECTION_MATRIX), false, projectionMatrix.get(matrixBuffer));
  }
}
