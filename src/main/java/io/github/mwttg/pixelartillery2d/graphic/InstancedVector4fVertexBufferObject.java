package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.FloatBuffer;
import java.util.List;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class InstancedVector4fVertexBufferObject {

  private static final Logger LOG =
      LoggerFactory.getLogger(InstancedVector4fVertexBufferObject.class);

  private static final int USAGE = GL41.GL_DYNAMIC_DRAW;
  private static final int VECTOR4F_SIZE_OF_FLOATS = 4;

  static int create() {
    final int id = GL41.glGenBuffers();
    OpenGlCleanUp.addVertexBufferObjectId(id);

    LOG.debug(
        "create InstancedVector4fVertexBufferObject with id='{}' of usage='{}'",
        id,
        VertexBufferObject.USAGE_TABLE.getOrDefault(USAGE, "UNKNOWN"));

    return id;
  }

  static void pushVectors(final int vboId, final List<Vector4f> vectors) {
    final int size = vectors.size();
    // ToDo: move Buffer to create function, so it's only instantiated once (but return only id is
    // no longer possible - and free memory has to be handled another way)
    final FloatBuffer buffer = MemoryUtil.memAllocFloat(size * VECTOR4F_SIZE_OF_FLOATS);
    for (int index = 0; index < size; index++) {
      final Vector4f vector = vectors.get(index);
      vector.get(index * VECTOR4F_SIZE_OF_FLOATS, buffer);
    }

    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, vboId);
    GL41.glBufferData(GL41.GL_ARRAY_BUFFER, buffer, GL41.GL_DYNAMIC_DRAW);

    MemoryUtil.memFree(buffer);
  }
}
