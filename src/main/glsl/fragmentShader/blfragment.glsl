#version 440

#define lightsCount 3
struct Light {
	vec4 pos;
	vec4 color;
	float phconst;
};
//Zmienne interpolowane
in vec4 ic;
in Light o_lights[lightsCount];
in vec4 n;
in vec4 v;
in vec2 iTexCoord0;
in vec2 iTexCoord1;
in float visibility;

uniform sampler2D textureMap0;
uniform sampler2D textureMap1;
uniform sampler2D textureMap2;

out vec4 pixelColor; //Zmienna wyjsciowa fragment shadera. Zapisuje sie do niej ostateczny (prawie) kolor piksela

float toonShading(float a, int num){
    return round(num*a)/num;
}
float calcLights(){
	return 0;
}
void main(void) {

    vec4 ml[lightsCount];
	vec4 mr[lightsCount];
	float nl[lightsCount];
	float rv[lightsCount];
	vec4 mn=normalize(n);
	vec4 mv=normalize(v);
	//vec4 ld=vec4(1,1,1,1); //Kolor swiatla
	vec4 ks=1*texture(textureMap1,iTexCoord0);//vec4(1,1,1,1);
	vec4 ls=vec4(1,1,1,1);
	//vec4 kd=ic; //Kolor obiektu
	//if (ic == 0)
    	vec4 kd=(texture(textureMap0,iTexCoord0));
	if (kd.r < 0.4f && kd.g < 0.05f && kd.b < 0.05f)
		discard;
	else
		kd=(9*kd+1.2*(texture(textureMap2,iTexCoord1)))/11;

	for (int i = 0; i < lightsCount; i++){
		ml[i]=normalize(o_lights[i].pos);
		mr[i]=reflect(-ml[i],mn);
		nl[i]=clamp(dot(mn,ml[i]),0.4f,1.0f);
		rv[i]=pow(clamp(dot(mr[i],mv),0,1),o_lights[i].phconst);
	}

    //nl = toonShading(nl,2);
    //rv = toonShading(rv,2);

	pixelColor=vec4(o_lights[0].color.rgb*kd.rgb*nl[0]+ks.rgb*ls.rgb*rv[0],kd.a);
	for (int i = 1; i < lightsCount; i++)
		pixelColor+=vec4(o_lights[i].color.rgb*kd.rgb*nl[i]+ks.rgb*ls.rgb*rv[i],kd.a);

	vec4 fogcolor = vec4(0.05f, 0.05f, 0.05f,1.0f);
	pixelColor=mix(fogcolor,pixelColor,visibility);
}
