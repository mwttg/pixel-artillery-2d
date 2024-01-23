package io.github.mwttg.pixelartillery2d.sprites;

public class StaticSprite extends AbstractSprite implements Sprite {

    private StaticSprite(int vertexArrayObjectId, int textureId) {
        super(vertexArrayObjectId, textureId);
    }

    public static StaticSprite create(final float width, final float height, final String textureFile) {
        final var plane = PlaneFactory.create(width, height);
        final var vertexVboId = VertexBufferObject.create(plane.vertices());
        final var textureVboId = VertexBufferObject.create(plane.uvCoordinates());
        final var vertexArrayObjectId = VertexArrayObject.create(vertexVboId, textureVboId);
        final var textureId = Texture.create(textureFile);

        return new StaticSprite(vertexArrayObjectId, textureId);
    }

    @Override
    protected int getFirstVertex() {
        return 0;
    }
}
