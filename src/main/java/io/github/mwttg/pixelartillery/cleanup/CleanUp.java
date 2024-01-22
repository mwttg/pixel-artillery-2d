package io.github.mwttg.pixelartillery.cleanup;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class CleanUp {

    private static final Logger LOG = LoggerFactory.getLogger(CleanUp.class);

    private static long gameWindowId;
    private static final List<Integer> vertexBufferObjectIds = new ArrayList<>();
    private static final List<Integer> vertexArrayObjectIds = new ArrayList<>();
    private static final List<Integer> shaderProgramIds = new ArrayList<>();
    private static final List<Integer> shaderIds = new ArrayList<>();
    private static final List<Integer> textureIds = new ArrayList<>();

    private CleanUp() {
    }

    public static void setGameWindowId(final Long id) {
        gameWindowId = id;
    }

    public static void addVertexBufferObjectId(final int id) {
        vertexBufferObjectIds.add(id);
    }

    public static void addVertexArrayObjectId(final int id) {
        vertexArrayObjectIds.add(id);
    }

    public static void addShaderProgramId(final int id) {
        shaderProgramIds.add(id);
    }

    public static void addShaderId(final int id) {
        shaderIds.add(id);
    }

    public static void addTextureId(final int id) {
        textureIds.add(id);
    }

    public static void purge() {
        LOG.info("clean up OpenGL");
        LOG.debug("... free Textures");
        textureIds.forEach(GL41::glDeleteTextures);
        LOG.debug("... free ShaderPrograms");
        shaderProgramIds.forEach(GL41::glDeleteProgram);
        LOG.debug("... free Shaders");
        shaderIds.forEach(GL41::glDeleteShader);
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
