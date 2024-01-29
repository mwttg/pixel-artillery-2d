package io.github.mwttg.pixelartillery2d.sprites;

import java.util.List;

public class AnimatedSprite extends AbstractSprite implements Sprite {

    private final AnimationTimingUnit animationTimingUnit;

    private AnimatedSprite(int vertexArrayObjectId, int textureId, final HorizontalFlipUnit horizontalFlipUnit, final AnimationTimingUnit animationTimingUnit) {
        super(vertexArrayObjectId, textureId, horizontalFlipUnit);
        this.animationTimingUnit = animationTimingUnit;
    }

    /**
     * Creates an animated Sprite. The texture file must contain all animation frames in a row (NOT column or grid)
     * and all frames must have the same size (width and height). The {@link AnimationType} is LOOP by default.
     *
     * @param width the width of the Sprite in world dimension units (the width of the plane, where the Sprite gets 'drawn on')
     * @param height the height of the Sprite in world dimension units (the height of the plane, where the Sprite gets 'drawn on')
     * @param textureFile the texture file (like .png) with all animation steps/frames
     * @param frameTimingsInMs the list of Integers (ms) of how long each frame should be shown (index 0 = first frame, index n = n-th frame)
     * @return the {@link AnimatedSprite}
     */
    public static AnimatedSprite create(final float width, final float height, final String textureFile, final List<Integer> frameTimingsInMs) {
        return create(width, height, textureFile, frameTimingsInMs, AnimationType.LOOP);
    }

    /**
     * Creates an animated Sprite. The texture file must contain all animation frames in a row (NOT column or grid)
     * and all frames must have the same size (width and height).
     *
     * @param width the width of the Sprite in world dimension units (the width of the plane, where the Sprite gets 'drawn on')
     * @param height the height of the Sprite in world dimension units (the height of the plane, where the Sprite gets 'drawn on')
     * @param textureFile the texture file (like .png) with all animation steps/frames
     * @param frameTimingsInMs the list of Integers (ms) of how long each frame should be shown (index 0 = first frame, index n = n-th frame)
     * @param type the {@link AnimationType} - How the Animation should be played
     * @return the {@link AnimatedSprite}
     */
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

    /**
     * Resets the animation to frame zero.
     */
    public void resetAnimation() {
        animationTimingUnit.reset();
    }
}
