package io.github.mwttg.pixelartillery2d;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public abstract class SimpleApplication {

    private final long gameWindowId;

    protected SimpleApplication(final OpenGlConfiguration configuration) {
        this.gameWindowId = GameWindow.create(configuration);
    }

    protected SimpleApplication(final String name, final int width, final int height) {
        final var configuration = new OpenGlConfiguration(name, width, height, true, true, 0.01f, 100.0f);
        this.gameWindowId = GameWindow.create(configuration);
    }

    /**
     * A method where stuff can be executed once before entering the 'game loop'.
     */
    protected abstract void initialize();

    /**
     * A method which is called in a loop. Here you can render stuff or calculate an apply physics.
     */
    protected abstract void gameLoop();

    public void start() {
        initialize();

        while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
            GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

            gameLoop();

            GLFW.glfwSwapBuffers(gameWindowId);
            GLFW.glfwPollEvents();
        }

        CleanUp.purge();
    }
}
