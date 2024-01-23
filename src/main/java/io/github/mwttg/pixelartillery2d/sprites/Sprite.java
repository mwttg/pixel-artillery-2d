package io.github.mwttg.pixelartillery2d.sprites;

import org.joml.Matrix4f;

public interface Sprite {

    void draw(final Uniform uniform, final Matrix4f modelMatrix, final Matrix4f viewMatrix, final Matrix4f projectionMatrix);
}
