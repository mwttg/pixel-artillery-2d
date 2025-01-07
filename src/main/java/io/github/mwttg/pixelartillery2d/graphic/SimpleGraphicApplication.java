package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

/** A class which can be extended for creating a simple application with Pixel ARTillery 2D */
public abstract class SimpleGraphicApplication {

  private final long gameWindowId;

  /**
   * Constructor for a simple graphic application
   *
   * @param configuration the {@link OpenGlConfiguration}
   */
  protected SimpleGraphicApplication(final OpenGlConfiguration configuration) {
    this.gameWindowId = GameWindow.create(configuration);
  }

  /**
   * Constructor for a simple graphic application
   *
   * @param name the name of the window
   * @param width the width of the window (in pixels)
   * @param height the height of the window (in pixels)
   */
  protected SimpleGraphicApplication(final String name, final int width, final int height) {
    final var configuration =
        new OpenGlConfiguration(name, width, height, true, true, 0.01f, 100.0f);
    this.gameWindowId = GameWindow.create(configuration);
  }

  /** A method where stuff can be executed once before entering the 'game loop'. */
  protected abstract void initialize();

  /**
   * A method which is called in a loop. Here you can render stuff or calculate an apply physics.
   */
  protected abstract void gameLoop();

  /** Start the loop of the application */
  public void start() {
    initialize();

    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      gameLoop();

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }

    OpenGlCleanUp.purge();
  }
}
