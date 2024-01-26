package io.github.mwttg.pixelartillery2d.sprites;

public class StaticSprite extends AbstractSprite implements Sprite {

    private StaticSprite(final int vertexArrayObjectId, final int textureId, final HorizontalFlipUnit horizontalFlipUnit) {
        super(vertexArrayObjectId, textureId, horizontalFlipUnit);
    }

    public static StaticSprite create(final float width, final float height, final String textureFile) {
        final var plane = PlaneFactory.create(width, height);
        final var vertexVboId = VertexBufferObject.create(plane.vertices());
        final var uvCoordinatesVboId = VertexBufferObject.create(plane.uvCoordinates());
        final var vertexArrayObjectId = VertexArrayObject.create(vertexVboId, uvCoordinatesVboId);
        final var textureId = Texture.create(textureFile);
        final var horizontalFlipUnit = HorizontalFlipUnit.initialize(plane, uvCoordinatesVboId);

        return new StaticSprite(vertexArrayObjectId, textureId, horizontalFlipUnit);
    }

    @Override
    protected int getFirstVertex() {
        return 0;
    }
}
