package io.github.mwttg.pixelartillery2d;

/**
 * The configuration for the OpenGL context.
 *
 * @param title the name of the window
 * @param width the width of the window (in pixel)
 * @param height the height of the window (in pixel)
 * @param fullScreen a flag for full-screen
 * @param vSync a flag for vertical sync
 * @param nearPlane the near plane (in world coordinates). The closest distance to start rendering stuff (z-coordinate)
 * @param farPlane the far plane (in world coordinates). The most far away distance before stop rendering stuff (z-coordinate)
 */
public record OpenGlConfiguration(
        String title,
        int width,
        int height,
        boolean fullScreen,
        boolean vSync,
        float nearPlane,
        float farPlane
) {

    public String prettyFormat() {
        return """
                OpenGL 4.1 Configuration
                    - Title ......................... %s
                    - Window Dimension .............. %sx%s
                    - FullScreen .................... %s
                    - VSync ......................... %s
                    - near Plane .................... %.2f
                    - far Plane ..................... %.2f
                """.formatted(title, width, height, fullScreen, vSync, nearPlane, farPlane);
    }
}
