#version 330 core

#define MAX_LIGHTS 5 //Maximum number of point lights

// Macierz perspektywy
uniform mat4 p_matrix;
// Macierz widoku
uniform mat4 v_matrix;
// Macierz module
uniform mat4 m_matrix;
// Pozycja oka
uniform vec3 viewPos;

// Posyzja wierzchołka
layout(location = 0) in vec3 vertexPosition;
// Wektor normalny wieerzchołka
layout(location = 1) in vec3 vertexNormal;
// Koordynaty tekstury (współrzędne UV)
layout(location = 2) in vec2 texCoord;
layout(location = 3) in vec3 tangent;
layout(location = 4) in vec3 bitangent;

out vec3 i_fragPos;
out vec2 i_texCoord;
out mat3 i_tbn;

void main(){
    gl_Position = p_matrix*v_matrix*m_matrix*vec4(vertexPosition, 1.0);
    i_fragPos = vec3( m_matrix * vec4(vertexPosition, 1.0));
    i_texCoord = texCoord;

    //Generacja macierzy TBN
    vec3 t = normalize(mat3(m_matrix) * tangent);
    vec3 b = normalize(mat3(m_matrix) * bitangent );
    vec3 n = normalize(mat3(m_matrix) * vertexNormal );

    i_tbn = mat3(t, b, n);
}