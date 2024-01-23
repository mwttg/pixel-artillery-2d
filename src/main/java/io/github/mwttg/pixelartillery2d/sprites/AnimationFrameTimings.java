package io.github.mwttg.pixelartillery2d.sprites;

import java.util.List;

final class AnimationFrameTimings {

    private final List<Integer> delaysInMs;
    private final AnimationType type;
    private final int maxFrames;
    private int currentFrame;
    private long lastTick;
    private int alternatingSummand;

    private AnimationFrameTimings(final List<Integer> delaysInMs, final AnimationType type) {
        this.delaysInMs = delaysInMs;
        this.type = type;
        this.maxFrames = delaysInMs.size();
        this.currentFrame = 0;
        this.lastTick = System.currentTimeMillis();
        this.alternatingSummand = 1;
    }

    static AnimationFrameTimings create(final List<Integer> delaysInMs) {
        return new AnimationFrameTimings(delaysInMs, AnimationType.LOOP);
    }

    static AnimationFrameTimings create(final List<Integer> delaysInMs, final AnimationType type) {
        return new AnimationFrameTimings(delaysInMs, type);
    }

    void reset() {
        lastTick = System.currentTimeMillis();
        currentFrame = 0;
    }

    int getCurrentFrame() {
        final var now = System.currentTimeMillis();
        if (now - lastTick > delaysInMs.get(currentFrame)) {
            lastTick = System.currentTimeMillis();
            nextFrame();
        }
        return currentFrame;
    }

    private void nextFrame() {
        switch (type) {
            case LOOP -> loopAnimation();
            case ONCE -> onceAnimation();
            case ALTERNATING -> alternatingAnimation();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void loopAnimation() {
        currentFrame++;
        if (currentFrame >= maxFrames) {
            currentFrame = 0;
        }
    }

    private void onceAnimation() {
        if (currentFrame < maxFrames) {
            currentFrame++;
        }
    }

    private void alternatingAnimation() {
        if (currentFrame >= maxFrames) {
            alternatingSummand = -1;
        }
        if (currentFrame < 0) {
            alternatingSummand = 1;
        }
        currentFrame = currentFrame + alternatingSummand;
    }
}
