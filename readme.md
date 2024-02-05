[![Build Pipeline](https://github.com/mwttg/pixel-artillery-2d/actions/workflows/build.yml/badge.svg)](https://github.com/mwttg/pixel-artillery-2d/actions/workflows/build.yml)

# Pixel ARTillery 2D

A small library for rendering Pixel Art with OpenGL 4.1 and playing sounds with OpenAL based on [LWJGL 3][lwjgl] and [JOML][joml].

## Requirements
|        | Version |
|--------|---------|
| JDK    | 21      |
| OpenGL | 4.1     |
| OpenAL | 1.1     |

## How to use this library

### Step 1: Import the dependency (e.g. with Maven)
````xml
<dependency>
    <groupId>io.github.mwttg</groupId>
    <artifactId>pixel-artillery-2d</artifactId>
    <version>1.0.0</version>
</dependency>
````

### Step 2: Create a window

````java
final OpenGlConfiguration config = new OpenGlConfiguration("My Window", 1920, 1080, true, true, 0.01f, 100.0f);
final GamwWindow myWindowId = GameWindow.create(config);
````

### Step 3: Create a Sprite

````java
// a static sprite without animation
final Sprite staticSprite = StaticSprite.create(1.0f, 1.0f, "./data/static-sprite.png");

// an animated Sprite with 3 Frames
final Sprite animatedSprite = AnimatedSprite.create(1.0f, 1.0f, "./data/animated-sprite.png", List.of(150, 150, 200));
````

### Step 4: Create a ShaderProgram with Uniform (or use default)

````java
final int myShaderProgramId = ShaderProgram.createFrom("./shader/vertex.glsl", "./shader/fragment.glsl");
final Uniform myUniform = Uniform.create(myShaderProgramId);

// or you can use the default ShaderProgram
final int defaultShaderProgramId = ShaderProgram.createDefaultShader();
final int defaultUniform = Uniform.create(defaultShaderProgramId);
````

### Step 5: Draw the Sprites inside a loop
````java
while (!GLFW.glfwWindowShouldClose(myWindowId)) {
    GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

    GL41.glUseProgram(myShaderSprogramId);
    staticSprite.draw(myUniform, model1Matrix, viewMatrix, projectionMatrix);
    animatedSprite.draw(myUniform, model2Matrix, viewMatrix, projectionMatrix);
    
    GLFW.glfwSwapBuffers(myWindowId);
    GLFW.glfwPollEvents();
 }
````

Or you can use the abstract class `SimpleApplication`. 
An example can be found [here][pixel-artillery-example].

## FAQ

#### Question:
Why OpenGL 4.1?

#### Answer:
It is the last version which is supported by Mac/Apple. 
So this lib can be used without complicated setup stuff.

---

#### Question:
What texture filtering is used for the Sprites?

#### Answer:
This library is build for pixel art. 
That means there is no antialiasing for textures instead it is 'pixel perfect'.
In detail the `GL_TEXTURE_MIN_FILTER` is set to `GL_NEAREST_MIPMAP_NEAREST` and the `GL_TEXTURE_MAG_FILTER` is set to `GL_NEAREST`.

---

#### Question:
Is there example code that uses this library?

#### Answer:
Yes. see: https://github.com/mwttg-games/pixel-artillery-2d-test for graphics and sound example.

[comment]: <> (collection of links sorted alphabetically ascending)
[joml]: https://github.com/JOML-CI/JOML
[lwjgl]: https://www.lwjgl.org/
[pixel-artillery-example]: https://github.com/mwttg-games/pixel-artillery-2d-test
