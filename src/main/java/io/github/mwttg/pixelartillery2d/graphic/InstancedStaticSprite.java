package io.github.mwttg.pixelartillery2d.graphic;

import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

/** A class for a static instanced (not animated) Sprite. */
public class InstancedStaticSprite {

  private final int modelMatricesVertexBufferObjectId;
  private final int vertexArrayObjectId;
  private final int textureId;

  private InstancedStaticSprite(
      final int modelMatricesVertexBufferObjectId,
      final int vertexArrayObjectId,
      final int textureId) {
    this.modelMatricesVertexBufferObjectId = modelMatricesVertexBufferObjectId;
    this.vertexArrayObjectId = vertexArrayObjectId;
    this.textureId = textureId;
  }

  /**
   * Creates a static instanced Sprite (no animation) - (An {@link InstancedStaticSprite} can be
   * used for particle effects, because one Sprite gets rendered multiple times within one OpenGL
   * draw call).
   *
   * @param width the width of the Sprite in world dimension units (the width of the plane, where
   *     the Sprite gets 'drawn on'
   * @param height the height of the Sprite in world dimension units (the width of the plane, where
   *     the Sprite gets 'drawn on'
   * @param textureFile the texture file (like .png)
   * @return the {@link InstancedStaticSprite}
   */
  public static InstancedStaticSprite create(
      final float width, final float height, final String textureFile) {
    final Plane plane = PlaneFactory.create(width, height);
    final int vertexVboId = VertexBufferObject.create(plane.vertices());
    final int uvCoordinatesVboId = VertexBufferObject.create(plane.uvCoordinates());
    final int modelMatrixVboId = InstancedMatrix4fBufferObject.create();
    final int vertexArrayObjectId =
        InstancedVertexArrayObject.create(vertexVboId, uvCoordinatesVboId, modelMatrixVboId);
    final int textureId = Texture.create(textureFile);

    return new InstancedStaticSprite(modelMatrixVboId, vertexArrayObjectId, textureId);
  }

  /**
   * Renders a static instanced Sprite. An instanced Sprite means one Sprite can get rendered
   * multiple times in different positions within one OpenGL draw call.
   *
   * @param uniform the {@link InstancedUniform} which is used for uploading data to the GPU. The
   *     InstancedUniform belongs to the {@link ShaderProgram} which is used for rendering the
   *     Sprites.
   * @param modelMatrices the List of Model-Matrices (positions, rotations, scaling of all the
   *     Sprite planes inside the world)
   * @param viewMatrix the View-Matrix (the camera)
   * @param projectionMatrix the Projection-Matrix (in 2D usually an orthographic projection)
   */
  public void draw(
      final InstancedUniform uniform,
      final List<Matrix4f> modelMatrices,
      final Matrix4f viewMatrix,
      final Matrix4f projectionMatrix) {

    uniform.upload(viewMatrix, projectionMatrix, textureId);
    InstancedMatrix4fBufferObject.pushMatrices(modelMatricesVertexBufferObjectId, modelMatrices);
    GL41.glBindVertexArray(vertexArrayObjectId);
    GL41.glDrawArraysInstanced(GL41.GL_TRIANGLES, 0, 6, modelMatrices.size());
  }
}
