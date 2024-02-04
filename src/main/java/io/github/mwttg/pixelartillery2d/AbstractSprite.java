package io.github.mwttg.pixelartillery2d;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

abstract class AbstractSprite {

    private final int vertexArrayObjectId;
    private final int textureId;
    private final HorizontalFlipUnit horizontalFlipUnit;

    AbstractSprite(final int vertexArrayObjectId, final int textureId, final HorizontalFlipUnit horizontalFlipUnit) {
        this.vertexArrayObjectId = vertexArrayObjectId;
        this.textureId = textureId;
        this.horizontalFlipUnit = horizontalFlipUnit;
    }

    /**
     * Renders a static or animated Sprite.
     *
     * @param uniform the {@link Uniform} which is used for uploading data to the GPU. The Uniform
     *                belongs to the {@link ShaderProgram} which is used for rendering the Sprite
     * @param modelMatrix the Model-Matrix (position, rotation, scaling of the plane in the world)
     * @param viewMatrix the View-Matrix (the camera)
     * @param projectionMatrix the Projection-Matrix (in 2D usually an orthographic projection)
     */
    public void draw(final Uniform uniform, final Matrix4f modelMatrix, final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
        uniform.upload(modelMatrix, viewMatrix, projectionMatrix, textureId);
        GL41.glBindVertexArray(vertexArrayObjectId);
        GL41.glDrawArrays(GL41.GL_TRIANGLES, getFirstVertex(), 6);
    }

    /**
     * Check if the Sprite is flipped horizontal (mirrored on the y-axis).
     *
     * @return true if the sprite is flipped, false otherwise
     */
    public boolean isFlippedHorizontal() {
        return horizontalFlipUnit.isFlippedHorizontal();
    }

    /**
     * Flips the Sprite horizontal (mirrors the Sprite on the y-axis).
     */
    public void flipHorizontal() {
        horizontalFlipUnit.executeHorizontalFlip();
    }

    abstract int getFirstVertex();
}
