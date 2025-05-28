package io.github.mwttg.pixelartillery2d.graphic;

import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL41;

/** A class for instanced Particles (planes with color). */
public class InstancedParticles {

  private final int modelMatrixVboId;
  private final int colorVboId;
  private final int vaoId;

  private InstancedParticles(final int modelMatrixVboId, final int colorVboId, final int vaoId) {
    this.modelMatrixVboId = modelMatrixVboId;
    this.colorVboId = colorVboId;
    this.vaoId = vaoId;
  }

  /**
   * Creates the base for instanced particles. Sets up VBOs and VAO. Uses instanced rendering for
   * the planes.
   *
   * @param particleWidth the width of the particle
   * @param particleHeight the height of the particle
   * @return the {@link InstancedParticles} system
   */
  public static InstancedParticles create(final float particleWidth, final float particleHeight) {
    final Plane plane = PlaneFactory.create(particleWidth, particleHeight);
    final int vertexVboId = VertexBufferObject.create(plane.vertices());
    final int colorVboId = InstancedVector4fVertexBufferObject.create();
    final int modelMatrixVboId = InstancedMatrix4fBufferObject.create();
    final int vaoId =
        InstancedParticlesVertexArrayObject.create(vertexVboId, modelMatrixVboId, colorVboId);

    return new InstancedParticles(modelMatrixVboId, colorVboId, vaoId);
  }

  /**
   * Renders the instanced particles. Amount of particles and position (or scaling, etc.) is defined
   * by the modelMatrices. The list of colors (also instanced) contains the color for each particle
   * (size of colors should match the size of modelMatrices)
   *
   * @param uniform the {@link InstancedParticleUniform} which is used for uploading view- and
   *     projection matrix to the GPU. The InstancedParticleUniform belongs to the {@link
   *     ShaderProgram} which is used for rendering the particles.
   * @param vertexColors the color of each particle (the size of this list should match the size of
   *     the list for modelMatrices)
   * @param modelMatrices the list of model-matrices (position, rotation, scale) of each particle,
   *     which should get rendered.
   * @param viewMatrix the View-Matrix (the camera)
   * @param projectionMatrix the Projection-Matrix (in 2D usually an orthographic projection)
   */
  public void draw(
      final InstancedParticleUniform uniform,
      final List<Vector4f> vertexColors,
      final List<Matrix4f> modelMatrices,
      final Matrix4f viewMatrix,
      final Matrix4f projectionMatrix) {
    uniform.upload(viewMatrix, projectionMatrix);
    InstancedVector4fVertexBufferObject.pushVectors(colorVboId, vertexColors);
    InstancedMatrix4fBufferObject.pushMatrices(modelMatrixVboId, modelMatrices);
    GL41.glBindVertexArray(vaoId);
    GL41.glDrawArraysInstanced(GL41.GL_TRIANGLES, 0, 6, modelMatrices.size());
  }
}
