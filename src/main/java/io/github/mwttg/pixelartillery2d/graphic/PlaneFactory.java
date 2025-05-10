package io.github.mwttg.pixelartillery2d.graphic;

// spotless:off
/**
 * <p>Wavefront OBJ file example</p>
 * # Blender v2.92.0 OBJ File: ''
 * # www.blender.org
 * o Plane
 * v 0.000000 0.000000 0.000000
 * v 1.000000 0.000000 0.000000
 * v 0.000000 1.000000 0.000000
 * v 1.000000 1.000000 0.000000
 * vt 1.000000 0.000000
 * vt 0.000000 1.000000
 * vt 0.000000 0.000000
 * vt 1.000000 1.000000
 * s off
 * f 2/1 3/2 1/3
 * f 2/1 4/4 3/2
 *
 * <p>OpenGL coordinates example</p>
 * The coordinates for the example plane from above would look like:
 *  v = 3 = (0.0, 1.0, 0.0)          v = 4 = (1.0, 1.0, 0.0)
 * vt = 2 = (0.0, 1.0)              vt = 4 = (1.0, 1.0)
 *               ------------------------
 *               | \                    |
 *               |    \                 |
 *               |       \              |
 *               |          \           |
 *               |              \       |
 *               |                 \    |
 *               |                    \ |
 *               ------------------------
 *  v = 1 = (0.0, 0.0, 0.0)          v = 2 = (1.0, 0.0, 0.0)
 * vt = 3 = (0.0, 0.0)              vt = 1 = (1.0, 0.0)
 */
// spotless:on
final class PlaneFactory {

  private static final int FLOATS_PER_PLANE = 18;
  private static final int FLOAT_PER_UV = 12;

  private PlaneFactory() {}

  static Plane create(final float width, final float height) {
    final float[] geometry = geometry(width, height);
    final float[] uvCoordinates = uvCoordinates(0, 1);
    final float[] uvCoordinatesHorizontalFlipped = uvCoordinatesFlippedHorizontal(0, 1);
    return new Plane(geometry, uvCoordinates, uvCoordinatesHorizontalFlipped);
  }

  static Plane createPlaneStrip(final float width, final float height, final int maxFrames) {
    float[] geometry = new float[maxFrames * FLOATS_PER_PLANE];
    float[] uvCoordinates = new float[maxFrames * FLOAT_PER_UV];
    float[] uvCoordinatesFlippedHorizontal = new float[maxFrames * FLOAT_PER_UV];

    for (int frame = 0; frame < maxFrames; frame++) {
      final float[] plane = geometry(width, height);
      int geometryIndex = frame * FLOATS_PER_PLANE;
      for (float f : plane) {
        geometry[geometryIndex] = f;
        geometryIndex++;
      }

      final float[] uv = uvCoordinates(frame, maxFrames);
      int uvIndex = frame * FLOAT_PER_UV;
      for (float f : uv) {
        uvCoordinates[uvIndex] = f;
        uvIndex++;
      }

      final float[] uvFlippedHorizontal = uvCoordinatesFlippedHorizontal(frame, maxFrames);
      int uvIndexFlippedHorizontal = frame * FLOAT_PER_UV;
      for (float f : uvFlippedHorizontal) {
        uvCoordinatesFlippedHorizontal[uvIndexFlippedHorizontal] = f;
        uvIndexFlippedHorizontal++;
      }
    }
    return new Plane(geometry, uvCoordinates, uvCoordinatesFlippedHorizontal);
  }

  private static float[] geometry(final float width, final float height) {
    // spotless:off
    return new float[]{
            width, 0.0f,   0.0f,
            0.0f,  height, 0.0f,
            0.0f,  0.0f,   0.0f,
            width, 0.0f,   0.0f,
            width, height, 0.0f,
            0.0f,  height, 0.0f
    };
    // spotless:on
  }

  private static float[] uvCoordinates(final int currentFrame, final int maxFrames) {
    final float width = 1.0f / (float) maxFrames;
    final float left = width * currentFrame;
    final float right = left + width;
    // spotless:off
    return new float[]{
            right, 0.0f,
            left,  1.0f,
            left,  0.0f,
            right, 0.0f,
            right, 1.0f,
            left,  1.0f
    };
    // spotless:on
  }

  private static float[] uvCoordinatesFlippedHorizontal(
      final int currentFrame, final int maxFrames) {
    final float width = 1.0f / (float) maxFrames;
    final float left = width * currentFrame;
    final float right = left + width;
    // spotless:off
    return new float[]{
            left,  0.0f,
            right, 1.0f,
            right, 0.0f,
            left,  0.0f,
            left,  1.0f,
            right, 1.0f
    };
    // spotless:on
  }
}
