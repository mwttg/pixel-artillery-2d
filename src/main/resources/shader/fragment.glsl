#version 410

in vec2 outTextureCoordinate;

uniform sampler2D textureSampler0;

out vec4 color;

void main()
{
    vec4 textureColor = texture(textureSampler0, outTextureCoordinate);
    if (textureColor.a < 0.1)
        discard;

    color = textureColor;
}