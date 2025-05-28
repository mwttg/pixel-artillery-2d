package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.FloatBuffer;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class InstancedMatrix4fBufferObject {

  private static final Logger LOG = LoggerFactory.getLogger(InstancedMatrix4fBufferObject.class);

  private static final int USAGE = GL41.GL_DYNAMIC_DRAW;
  private static final int MATRIX4F_SIZE_OF_FLOATS = 4 * 4;

  private InstancedMatrix4fBufferObject() {}

  static int create() {
    final int id = GL41.glGenBuffers();
    OpenGlCleanUp.addVertexBufferObjectId(id);

    LOG.debug(
        "create InstancedMatrix4fVertexBufferObject with id='{}' of usage='{}'",
        id,
        VertexBufferObject.USAGE_TABLE.getOrDefault(USAGE, "UNKNOWN"));

    return id;
  }

  static void pushMatrices(final int vboId, final List<Matrix4f> matrices) {
    final int size = matrices.size();
    // ToDo: move Buffer to create function, so it's only instantiated once (but return only id is
    // no longer possible - and free memory has to be handled another way)
    final FloatBuffer buffer = MemoryUtil.memAllocFloat(size * MATRIX4F_SIZE_OF_FLOATS);
    for (int index = 0; index < size; index++) {
      final Matrix4f model = matrices.get(index);
      model.get(index * MATRIX4F_SIZE_OF_FLOATS, buffer);
    }

    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, vboId);
    GL41.glBufferData(GL41.GL_ARRAY_BUFFER, buffer, GL41.GL_DYNAMIC_DRAW);

    MemoryUtil.memFree(buffer);
  }
}
