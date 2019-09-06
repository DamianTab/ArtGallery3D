#version 330 core

#define MAX_LIGHTS 5 //Maximum number of point lights

uniform mat4 p_matrix;
uniform mat4 v_matrix;
uniform mat4 m_matrix;
uniform vec3 viewPos;

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexNormal;
layout(location = 2) in vec2 texCoord;
layout(location = 3) in vec3 bitangent;
layout(location = 4) in vec3 tangent;

out vec3 i_fragPos;
out vec2 i_texCoord;
out mat3 i_tbn;

out vec3 i_t;
out vec3 i_b;
out vec3 i_n;

void main(){
    vec4 vertexPosition4 = vec4(vertexPosition, 1.0);
    gl_Position = p_matrix*v_matrix*m_matrix*vertexPosition4;
    i_fragPos = vec3( m_matrix * vertexPosition4);
    i_texCoord = texCoord;

    vec3 t = normalize(mat3(m_matrix) * tangent);
    vec3 b = normalize(mat3(m_matrix) * bitangent );
    vec3 n = normalize(mat3(m_matrix) * vertexNormal );

    i_tbn = mat3(t, b, n);

    i_t = t;
    i_b = bitangent;
    i_n = n;
}