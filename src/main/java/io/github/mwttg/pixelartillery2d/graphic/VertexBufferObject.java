package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

final class VertexBufferObject {

    private static final Logger LOG = LoggerFactory.getLogger(VertexBufferObject.class);

    private static final Map<Integer, String> USAGE_TABLE = Map.of(
            0x88E1, "GL_STREAM_READ",
            0x88E2, "GL_STREAM_COPY",
            0x88E4, "GL_STATIC_DRAW",
            0x88E5, "GL_STATIC_READ",
            0x88E6, "GL_STATIC_COPY",
            0x88E8, "GL_DYNAMIC_DRAW",
            0x88E9, "GL_DYNAMIC_READ",
            0x88EA, "GL_DYNAMIC_COPY"
    );

    private VertexBufferObject() {
    }

    static int create(final float[] data) {
        return create(data, GL41.GL_STATIC_DRAW);
    }

    static int create(final float[] data, final int usage) {
        final var buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        final var id = GL41.glGenBuffers();
        CleanUp.addVertexBufferObjectId(id);
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, id);
        GL41.glBufferData(GL41.GL_ARRAY_BUFFER, buffer, usage);

        LOG.debug("create VertexBufferObject with id='{}' of usage='{}'", id, USAGE_TABLE.getOrDefault(usage, "UNKNOWN"));
        return id;
    }
}
