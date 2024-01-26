package io.github.mwttg.pixelartillery2d.sprites;

import java.util.List;

public class AnimatedSprite extends AbstractSprite implements Sprite {

    private final AnimationTimingUnit animationTimingUnit;

    private AnimatedSprite(int vertexArrayObjectId, int textureId, final HorizontalFlipUnit horizontalFlipUnit, final AnimationTimingUnit animationTimingUnit) {
        super(vertexArrayObjectId, textureId, horizontalFlipUnit);
        this.animationTimingUnit = animationTimingUnit;
    }

    public static AnimatedSprite create(final float width, final float height, final String textureFile, final List<Integer> frameTimingsInMs) {
        return create(width, height, textureFile, frameTimingsInMs, AnimationType.LOOP);
    }

    public static AnimatedSprite create(final float width, final float height, final String textureFile, final List<Integer> frameTimingsInMs, final AnimationType type) {
        final var maxFrames = frameTimingsInMs.size();
        final var planeStrip = PlaneFactory.createPlaneStrip(width, height, maxFrames);
        final var vertexVboId = VertexBufferObject.create(planeStrip.vertices());
        final var uvCoordinatesVboId = VertexBufferObject.create(planeStrip.uvCoordinates());
        final var vertexArrayObjectId = VertexArrayObject.create(vertexVboId, uvCoordinatesVboId);
        final var textureId = Texture.create(textureFile);
        final var horizontalFlipUnit = HorizontalFlipUnit.initialize(planeStrip, uvCoordinatesVboId);
        final var animationTimingUnit = AnimationTimingUnit.create(frameTimingsInMs, type);

        return new AnimatedSprite(vertexArrayObjectId, textureId, horizontalFlipUnit, animationTimingUnit);
    }

    @Override
    protected int getFirstVertex() {
        return animationTimingUnit.getCurrentFrame() * 6;
    }

    public void resetAnimation() {
        animationTimingUnit.reset();
    }
}
