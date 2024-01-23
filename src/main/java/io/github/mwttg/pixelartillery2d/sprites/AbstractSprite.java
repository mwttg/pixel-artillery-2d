package io.github.mwttg.pixelartillery2d.sprites;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

abstract class AbstractSprite {

    private final int vertexArrayObjectId;
    private final int textureId;

    protected AbstractSprite(final int vertexArrayObjectId, final int textureId) {
        this.vertexArrayObjectId = vertexArrayObjectId;
        this.textureId = textureId;
    }

    public void draw(final Uniform uniform, final Matrix4f modelMatrix, final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
        uniform.upload(modelMatrix, viewMatrix, projectionMatrix, textureId);
        GL41.glBindVertexArray(vertexArrayObjectId);
        GL41.glDrawArrays(GL41.GL_TRIANGLES, getFirstVertex(), 6);
    }

    protected abstract int getFirstVertex();
}
