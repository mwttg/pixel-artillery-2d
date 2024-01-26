package io.github.mwttg.pixelartillery2d.mainloop;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public abstract class AbstractMainLoop {

    protected abstract void initialize();

    protected abstract void execute();

    public void loop(final long gameWindowId) {
        initialize();

        while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
            GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

            execute();

            GLFW.glfwSwapBuffers(gameWindowId);
            GLFW.glfwPollEvents();
        }
    }
}
