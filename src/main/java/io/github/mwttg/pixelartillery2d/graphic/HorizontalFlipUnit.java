package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.opengl.GL41;

final class HorizontalFlipUnit {

  private final Plane plane;
  private final int uvCoordinatesVboId;
  private boolean isFlippedHorizontal;

  private HorizontalFlipUnit(final Plane plane, final int uvCoordinatesVboId) {
    this.isFlippedHorizontal = false;
    this.plane = plane;
    this.uvCoordinatesVboId = uvCoordinatesVboId;
  }

  static HorizontalFlipUnit initialize(final Plane plane, final int uvCoordinatesVboId) {
    return new HorizontalFlipUnit(plane, uvCoordinatesVboId);
  }

  boolean isFlippedHorizontal() {
    return isFlippedHorizontal;
  }

  void executeHorizontalFlip() {
    GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, uvCoordinatesVboId);
    if (!isFlippedHorizontal) {
      GL41.glBufferSubData(GL41.GL_ARRAY_BUFFER, 0, plane.uvCoordinatesFlippedHorizontal());
      isFlippedHorizontal = true;
    } else {
      GL41.glBufferSubData(GL41.GL_ARRAY_BUFFER, 0, plane.uvCoordinates());
      isFlippedHorizontal = false;
    }
  }
}
