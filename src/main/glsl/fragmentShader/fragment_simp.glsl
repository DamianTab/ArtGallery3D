#version 440


//Zmienne interpolowane
in vec4 ic;
in vec2 iTexCoord0;

uniform sampler2D textureMap0;

out vec4 pixelColor; //Zmienna wyjsciowa fragment shadera. Zapisuje sie do niej ostateczny (prawie) kolor piksela

void main(void) {
	vec4 kd=texture(textureMap0,iTexCoord0);
	if (kd.r < 0.2f && kd.g < 0.05f && kd.b < 0.05f)
		discard;
	pixelColor=kd;
}
