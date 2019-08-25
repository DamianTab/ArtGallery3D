#version 440

#define lightsCount 3
struct Light {
    vec4 pos;
    vec4 color;
    float phconst;
};
//Zmienne jednorodne
uniform mat4 P;
uniform mat4 V;
uniform mat4 M;
//uniform vec4 lp[lightsCount];
uniform Light i_lights[lightsCount];


//Atrybuty
in vec4 vertex; //wspolrzedne wierzcholka w przestrzeni modelu
in vec4 normal; //wspolrzedne wektora normalnego w przestrzeni modelu
in vec4 color; //kolor wierzchołka
in vec2 texCoord0; //współrzędna teksturowania

//Zmienne interpolowane
out vec4 ic;
out Light o_lights[lightsCount];
out vec4 n;
out vec4 v;
out vec2 iTexCoord0;
out vec2 iTexCoord1;
out float visibility;

void main(void) {

    /*vec4 kd=color; //Kolor obiektu
    vec4 ld=vec4(1,1,1,1); //Kolor œwiat³a
    //Phong
    vec4 ks=vec4(1,1,1,1);
    vec4 ls=vec4(1,1,1,1);*/

    //vec4 nlp=vec4(0,0,-6,1);//Wspó³rzêdne Ÿród³a œwiat³a w przestrzeni œwiata



    /*vec4 l=normalize(V*(lp-M*vertex)); //Wektor "do œwiat³a" w przestrzeni oka
    vec4 n=normalize(V*M*normal); //Wektor normalny w przestrzeni oka
    //Phong
    vec4 r=reflect(-l,n);
    vec4 v=normalize(vec4(0,0,0,1)-V*M*vertex);*/


    //l=normalize(V*(lp[0]-M*vertex)); //Wektor "do œwiat³a" w przestrzeni oka
    //l2=normalize(V*(lp[1]-M*vertex));
    vec4 relcam = V * M * vertex;
    iTexCoord0=texCoord0;


    for(int i=0; i<lightsCount; i++){
        o_lights[i].pos=normalize(V*(i_lights[i].pos-M*vertex));
        o_lights[i].color=i_lights[i].color;
        o_lights[i].phconst=i_lights[i].phconst;
    }


    n=normalize(V*M*normal); //Wektor normalny w przestrzeni oka
    v=normalize(vec4(0,0,0,1)-V*M*vertex);

    //vec4 r=reflect(-l,n);



    /*float nl=clamp(dot(n,l),0,1);
    //Phong
    float rv=pow(clamp(dot(r,v),0,1),10); //25 to alfa*/


    float distance = length (relcam);
    visibility = clamp(exp(-pow((distance*0.0042f),1.2f)),0.0f,1.0f);

    //ic=vec4(ld.rgb*kd.rgb*nl+ks.rgb*ls.rgb*rv,kd.a); //dodana część Phonga
    ic=color;
    gl_Position=P*V*M*vertex;
    iTexCoord1=(n.xy+1)/2;


}
