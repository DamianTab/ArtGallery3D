#version 440

//Zmienne jednorodne
uniform mat4 P;
uniform mat4 V;
uniform mat4 M;

//Atrybuty
in vec4 vertex; //wspolrzedne wierzcholka w przestrzeni modelu
in vec4 normal; //wspolrzedne wektora normalnego w przestrzeni modelu
in vec4 color; //kolor wierzchołka
in vec2 texCoord0; //współrzędna teksturowania

//Zmienne interpolowane
out vec4 ic;
out vec2 iTexCoord0;

void main(void) {

    ic=vec4(color.rgb*0.5,color.a);
    iTexCoord0=texCoord0;
    gl_Position=P*V*M*vertex;
}
