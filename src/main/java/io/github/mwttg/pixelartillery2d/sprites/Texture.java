package io.github.mwttg.pixelartillery2d.sprites;

import io.github.mwttg.pixelartillery2d.cleanup.CleanUp;
import org.lwjgl.opengl.GL41;
import org.lwjgl.stb.STBImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Texture {

    private static final Logger LOG = LoggerFactory.getLogger(Texture.class);

    private Texture() {
    }

    /**
     * Creates a (OpenGL) Texture from an image file (like .png). Nearest filtering is used to have 'pixel perfect'
     * Textures (no interpolations).
     *
     * @param filename the path and filename to the image file for the texture
     * @return the OpenGL ID of the Texture
     */
    static int create(final String filename) {
        final var image = ImageFile.readFrom(filename);
        final var id = GL41.glGenTextures();
        GL41.glBindTexture(GL41.GL_TEXTURE_2D, id);
        GL41.glPixelStorei(GL41.GL_UNPACK_ALIGNMENT, 1);
        GL41.glTexImage2D(
                GL41.GL_TEXTURE_2D,
                0,
                GL41.GL_RGBA,
                image.width(),
                image.height(),
                0,
                GL41.GL_RGBA,
                GL41.GL_UNSIGNED_BYTE,
                image.pixels()
        );
        // NEAREST Filtering instead of LINEAR for sharp edges, because we are using PixelArt
        GL41.glTexParameteri(GL41.GL_TEXTURE_2D, GL41.GL_TEXTURE_MIN_FILTER, GL41.GL_NEAREST_MIPMAP_NEAREST);
        GL41.glTexParameteri(GL41.GL_TEXTURE_2D, GL41.GL_TEXTURE_MAG_FILTER, GL41.GL_NEAREST);
        GL41.glGenerateMipmap(GL41.GL_TEXTURE_2D);
        STBImage.stbi_image_free(image.pixels());

        LOG.debug("create Texture {}x{} with id='{}' from file '{}'", image.width(), image.height(), id, filename);
        CleanUp.addTextureId(id);
        return id;
    }
}
