#version 330 core

uniform mat4 m_matrix;

layout(location = 0) in vec3 vertexPosition;

void main()
{
    gl_Position = m_matrix * vec4(vertexPosition, 1.0);
}