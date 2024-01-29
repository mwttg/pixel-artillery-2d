package io.github.mwttg.pixelartillery2d.sprites;

import org.joml.Matrix4f;

public interface Sprite {

    /**
     * Draws (renders) the Sprite.
     *
     * @param uniform the {@link Uniform} which is used for uploading data to the GPU. The Uniform
     *                belongs to the {@link ShaderProgram} which is used for rendering the Sprite
     * @param modelMatrix the Model-Matrix (position, rotation, scaling of the plane in the world)
     * @param viewMatrix the View-Matrix (the camera)
     * @param projectionMatrix the Projection-Matrix (in 2D usually an orthographic projection)
     */
    void draw(final Uniform uniform, final Matrix4f modelMatrix, final Matrix4f viewMatrix, final Matrix4f projectionMatrix);

    /**
     * Check if the Sprite is flipped horizontal (mirrored on the y-axis).
     *
     * @return true if the sprite is flipped, false otherwise
     */
    boolean isFlippedHorizontal();

    /**
     * Flips the Sprite horizontal (mirrors the Sprite on the y-axis).
     */
    void flipHorizontal();
}
