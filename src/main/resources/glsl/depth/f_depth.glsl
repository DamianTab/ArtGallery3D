#version 330 core

uniform vec3 lightPos;
uniform float farPlane;

in vec4 i_fragPos;

void main()
{
    // get distance between fragment and light source
    float lightDistance = length(i_fragPos.xyz - lightPos);

    // map to [0;1] range by dividing by far_plane
    lightDistance = lightDistance / farPlane;

    // write this as modified depth
    gl_FragDepth = lightDistance;
}