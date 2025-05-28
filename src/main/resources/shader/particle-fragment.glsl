#version 410

in vec4 outColor;

out vec4 color;

void main()
{
    if (outColor.a < 0.05)
        discard;

    color = outColor;
}