package io.github.mwttg.pixelartillery2d.config;

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
