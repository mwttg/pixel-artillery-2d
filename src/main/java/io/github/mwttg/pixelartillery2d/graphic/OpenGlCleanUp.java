package io.github.mwttg.pixelartillery2d.graphic;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A class with a method to clean up all the OpenGL stuff (Buffers, Textures, Programs, etc.)
 */
public final class OpenGlCleanUp {

    private static final Logger LOG = LoggerFactory.getLogger(OpenGlCleanUp.class);

    private static long gameWindowId;
    private static final List<Integer> vertexBufferObjectIds = new ArrayList<>();
    private static final List<Integer> vertexArrayObjectIds = new ArrayList<>();
    private static final List<Integer> shaderProgramIds = new ArrayList<>();
    private static final List<Integer> textureIds = new ArrayList<>();

    private OpenGlCleanUp() {
    }

    static void setGameWindowId(final Long id) {
        gameWindowId = id;
    }

    static void addVertexBufferObjectId(final int id) {
        vertexBufferObjectIds.add(id);
    }

    static void addVertexArrayObjectId(final int id) {
        vertexArrayObjectIds.add(id);
    }

    static void addShaderProgramId(final int id) {
        shaderProgramIds.add(id);
    }

    static void addTextureId(final int id) {
        textureIds.add(id);
    }

    /**
     * Clean up all OpenGL stuff.
     */
    public static void purge() {
        LOG.info("clean up OpenGL");
        LOG.debug("... free Textures");
        textureIds.forEach(GL41::glDeleteTextures);
        LOG.debug("... free ShaderPrograms");
        shaderProgramIds.forEach(GL41::glDeleteProgram);
        LOG.debug("... free VertexArrayObjects");
        vertexArrayObjectIds.forEach(GL41::glDeleteVertexArrays);
        LOG.debug("... free VertexBufferObjects");
        vertexBufferObjectIds.forEach(GL41::glDeleteBuffers);
        LOG.debug("... terminate GameWindow");
        terminateGameWindow();
    }

    private static void terminateGameWindow() {
        Callbacks.glfwFreeCallbacks(gameWindowId);
        GLFW.glfwDestroyWindow(gameWindowId);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
