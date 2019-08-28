#version 330 core

#define MAX_LIGHTS 5 //Maximum number of point lights

uniform mat4 p_matrix;
uniform mat4 v_matrix;
uniform mat4 m_matrix;
uniform vec3 viewPos;
uniform vec3 lightPositions[MAX_LIGHTS];

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexNormal;
layout(location = 2) in vec2 texCoord;

out vec3 i_fragPos;
out vec2 i_texCoord;
out vec3 i_normal;
out vec3 i_viewPos;

void main(){
    vec4 vertexPosition4 = vec4(vertexPosition, 1.0);
    gl_Position = p_matrix*v_matrix*m_matrix*vertexPosition4;
    i_fragPos = vec3(p_matrix * v_matrix * m_matrix * vertexPosition4);
    i_normal = vertexNormal;
    i_texCoord = texCoord;
    i_viewPos = vec3(m_matrix * vec4(viewPos, 1.0f));
}