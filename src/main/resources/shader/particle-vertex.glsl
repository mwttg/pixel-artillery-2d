#version 410

layout (location = 0) in vec3 position;
layout (location = 1) in vec4 color;
layout (location = 2) in mat4 modelMatrix;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec4 outColor;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
    outColor = color;
}