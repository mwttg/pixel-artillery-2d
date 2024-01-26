package io.github.mwttg.pixelartillery2d.window;

import io.github.mwttg.pixelartillery2d.cleanup.CleanUp;
import io.github.mwttg.pixelartillery2d.config.OpenGlConfiguration;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GameWindow {

    private static final Logger LOG = LoggerFactory.getLogger(GameWindow.class);
    private static final int OPENGL_MAJOR_VERSION = 4;
    private static final int OPENGL_MINOR_VERSION = 1;

    private GameWindow() {
    }

    /**
     * Creates a window for all OpenGL operations.
     *
     * @param configuration the {@link OpenGlConfiguration} for the window
     * @return the window ID (from OpenGL)
     */
    public static long create(final OpenGlConfiguration configuration) {
        LOG.info("create GameWindow with {}", configuration.prettyFormat());

        initializeGlfw();
        final var id = initializeGameWindow(configuration);
        CleanUp.setGameWindowId(id);
        initializeKeyCallback(id);
        centerGameWindow(id, configuration);

        return id;
    }

    private static void initializeGlfw() {
        LOG.debug("... initialize GLFW");
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("GLFW wasn't initialized correctly.");
        }
    }

    private static long initializeGameWindow(final OpenGlConfiguration config) {
        LOG.debug("... initialize GameWindow");
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL41.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL41.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, OPENGL_MAJOR_VERSION);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, OPENGL_MINOR_VERSION);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL41.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        final var monitor = GLFW.glfwGetPrimaryMonitor();
        final var id = GLFW.glfwCreateWindow(
                config.width(),
                config.height(),
                config.title(),
                monitor,
                MemoryUtil.NULL
        );
        if (id == MemoryUtil.NULL) {
            throw new RuntimeException("An error occurred during initializing the GameWindow");
        }

        GLFW.glfwMakeContextCurrent(id);
        GL.createCapabilities();
        GL41.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        GLFW.glfwSwapInterval(config.vSync() ? 1 : 0);
        GLFW.glfwShowWindow(id);
        GLFW.glfwSetInputMode(id, GLFW.GLFW_STICKY_KEYS, GLFW.GLFW_TRUE);

        GL41.glEnable(GL41.GL_DEPTH_TEST);
        GL41.glEnable(GL41.GL_BLEND);
        GL41.glBlendFunc(GL41.GL_SRC_ALPHA, GL41.GL_ONE_MINUS_SRC_ALPHA);
        GL41.glEnable(GL41.GL_CULL_FACE);
        GL41.glCullFace(GL41.GL_BACK);

        return id;
    }

    private static void initializeKeyCallback(final long id) {
        LOG.debug("... initialize Key callback");

        final GLFWKeyCallbackI callback = (long windowId, int key, int scancode, int action, int mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(windowId, true);
            }
        };
        GLFW.glfwSetKeyCallback(id, callback);
    }

    private static void centerGameWindow(final long id, final OpenGlConfiguration config) {
        LOG.debug("... center GameWindow");

        final var primaryMonitor = GLFW.glfwGetPrimaryMonitor();
        final var videoMode = GLFW.glfwGetVideoMode(primaryMonitor);
        if (videoMode == null) {
            throw new RuntimeException("An error occurred during fetching the video mode of the primary monitor");
        }
        final var xPos = (videoMode.width() - config.width()) / 2;
        final var yPos = (videoMode.height() - config.height()) / 2;
        GLFW.glfwSetWindowPos(id, xPos, yPos);
    }
}
