package io.github.mwttg.pixelartillery2d.graphic;

/** Defines how the Sprite animation should be played (for {@link AnimatedSprite}). */
public enum AnimationType {

  /**
   * Plays the Sprite animation as a loop. This means after the animation is finished it started
   * from the beginning again. This is normally the default.
   */
  LOOP,

  /**
   * Plays the Sprite animation only once. After that the last frame of the animation is rendered
   * (like a static Sprite)
   */
  ONCE,

  /**
   * Plays the Sprite animation from start to end. Once the end is reached the animation is played
   * from end to start. After that the process repeats.
   */
  ALTERNATING
}
