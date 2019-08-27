#version 330 core

uniform mat4 p_matrix;
uniform mat4 v_matrix;
uniform mat4 m_matrix;

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexNormal;
layout(location = 2) in vec2 texCoord;

void main(){

	gl_Position = p_matrix*v_matrix*m_matrix*vec4(vertexPosition, 1.0f);
}