package io.github.mwttg.pixelartillery2d.graphic;

import java.nio.ByteBuffer;

record Image(ByteBuffer pixels, int width, int height) {}
