package io.github.mwttg.pixelartillery2d.graphic;

/** A class for a static (not animated) Sprite. */
public class StaticSprite extends AbstractSprite implements Sprite {

  private StaticSprite(
      final int vertexArrayObjectId,
      final int textureId,
      final HorizontalFlipUnit horizontalFlipUnit) {
    super(vertexArrayObjectId, textureId, horizontalFlipUnit);
  }

  /**
   * Creates a static Sprite (no animation).
   *
   * @param width the width of the Sprite in world dimension units (the width of the plane, where
   *     the Sprite gets 'drawn on')
   * @param height the height of the Sprite in world dimension units (the height of the plane, where
   *     the Sprite gets 'drawn on')
   * @param textureFile the texture file (like .png)
   * @return the {@link StaticSprite}
   */
  public static StaticSprite create(
      final float width, final float height, final String textureFile) {
    final Plane plane = PlaneFactory.create(width, height);
    final int vertexVboId = VertexBufferObject.create(plane.vertices());
    final int uvCoordinatesVboId = VertexBufferObject.create(plane.uvCoordinates());
    final int vertexArrayObjectId = VertexArrayObject.create(vertexVboId, uvCoordinatesVboId);
    final int textureId = Texture.create(textureFile);
    final HorizontalFlipUnit horizontalFlipUnit =
        HorizontalFlipUnit.initialize(plane, uvCoordinatesVboId);

    return new StaticSprite(vertexArrayObjectId, textureId, horizontalFlipUnit);
  }

  @Override
  int getFirstVertex() {
    return 0;
  }
}
