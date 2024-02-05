package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class VertexArrayObject {

    private static final Logger LOG = LoggerFactory.getLogger(VertexArrayObject.class);

    private VertexArrayObject() {
    }

    static int create(final int vertexVboId, final int textureVboId) {
        final var id = GL41.glGenVertexArrays();
        OpenGlCleanUp.addVertexArrayObjectId(id);
        GL41.glBindVertexArray(id);
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, vertexVboId);
        GL41.glVertexAttribPointer(0, 3, GL41.GL_FLOAT, false, 0, 0);
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, textureVboId);
        GL41.glVertexAttribPointer(1, 2, GL41.GL_FLOAT, false, 0, 0);
        GL41.glEnableVertexAttribArray(0); // vertices
        GL41.glEnableVertexAttribArray(1); // texture coordinates

        LOG.debug("create VertexArrayObject with id='{}'", id);
        return id;
    }
}
