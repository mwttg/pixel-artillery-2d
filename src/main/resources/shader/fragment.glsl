#version 410

in vec2 outTextureCoordinate;

uniform sampler2D textureSampler0;

out vec4 color;

void main()
{
    color = texture(textureSampler0, outTextureCoordinate);
}