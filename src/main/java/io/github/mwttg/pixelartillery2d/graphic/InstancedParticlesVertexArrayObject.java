package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class InstancedParticlesVertexArrayObject {

  private static final Logger LOG =
      LoggerFactory.getLogger(InstancedParticlesVertexArrayObject.class);

  private static final int VECTOR4F_SIZE_IN_BYTES = 4 * 4;
  private static final int MATRIX4F_SIZE_IN_BYTES = 4 * VECTOR4F_SIZE_IN_BYTES;

  private InstancedParticlesVertexArrayObject() {}

  static int create(final int vertexVboId, final int modelMatrixVboId, final int colorVboId) {
    final int id = GL41.glGenVertexArrays();
    OpenGlCleanUp.addVertexArrayObjectId(id);
    GL41.glBindVertexArray(id);

    // vertices
    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, vertexVboId);
    GL41.glVertexAttribPointer(0, 3, GL41.GL_FLOAT, false, 0, 0);

    // color vector
    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, colorVboId);
    GL41.glVertexAttribPointer(1, 4, GL41.GL_FLOAT, false, VECTOR4F_SIZE_IN_BYTES, 0);
    GL41.glVertexAttribDivisor(1, 1);

    // model matrix
    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, modelMatrixVboId);
    GL41.glVertexAttribPointer(2, 4, GL41.GL_FLOAT, false, MATRIX4F_SIZE_IN_BYTES, 0);
    GL41.glVertexAttribDivisor(2, 1);
    GL41.glVertexAttribPointer(
        3, 4, GL41.GL_FLOAT, false, MATRIX4F_SIZE_IN_BYTES, VECTOR4F_SIZE_IN_BYTES);
    GL41.glVertexAttribDivisor(3, 1);
    GL41.glVertexAttribPointer(
        4, 4, GL41.GL_FLOAT, false, MATRIX4F_SIZE_IN_BYTES, 2 * VECTOR4F_SIZE_IN_BYTES);
    GL41.glVertexAttribDivisor(4, 1);
    GL41.glVertexAttribPointer(
        5, 4, GL41.GL_FLOAT, false, MATRIX4F_SIZE_IN_BYTES, 3 * VECTOR4F_SIZE_IN_BYTES);
    GL41.glVertexAttribDivisor(5, 1);

    GL41.glEnableVertexAttribArray(0); // vertices
    GL41.glEnableVertexAttribArray(1); // colors
    GL41.glEnableVertexAttribArray(2); // modelMatrix
    GL41.glEnableVertexAttribArray(3); // modelMatrix
    GL41.glEnableVertexAttribArray(4); // modelMatrix
    GL41.glEnableVertexAttribArray(5); // modelMatrix

    LOG.debug("create ParticlesVertexArrayObject with id='{}'", id);
    return id;
  }
}
