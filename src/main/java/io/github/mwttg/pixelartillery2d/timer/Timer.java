package io.github.mwttg.pixelartillery2d.timer;

/** A {@link Timer} to measure a time period in seconds. */
public class Timer {

  private long lastTick;

  private Timer(final long lastTick) {
    this.lastTick = lastTick;
  }

  /**
   * Returns the time passed by in seconds since the last time this method was called (or if this
   * method is called the first time, since the timer was started).
   *
   * @return passed time in seconds
   */
  public float getDeltaTime() {
    final var currentTick = System.currentTimeMillis();
    final var delta = (currentTick - lastTick) / 1000.0f;
    lastTick = currentTick;
    return delta;
  }

  /**
   * Initializes and starts the {@link Timer}
   *
   * @return an instance of a {@link Timer}
   */
  public static Timer initialize() {
    return new Timer(System.currentTimeMillis());
  }
}
