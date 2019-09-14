#version 330 core

#define MAX_LIGHTS 4 //Maximum number of point lights

struct Material {
    vec3 ambientColor;
    vec3 diffuseColor;
    vec3 specularColor;
    float shininess;
    sampler2D ambientMap;
    sampler2D diffuseMap;
    sampler2D specularMap;
};

struct LightSource {
    float constant;
    float linear;
    float quadratic;
    vec3 ambientColor;
    vec3 diffuseColor;
    vec3 specularColor;
    vec3 position;
    samplerCube shadowCubeMap;
};

uniform mat4 p_matrix;
uniform mat4 v_matrix;
uniform mat4 m_matrix;
uniform Material material;
uniform LightSource lights[MAX_LIGHTS];
uniform vec3 viewPos;
uniform float farPlane;

out vec4 color;

in vec3 i_fragPos;
in vec2 i_texCoord;
in vec3 i_normal;

float shadowCalculation(vec3 fragPos, LightSource light) {
    // get vector between fragment position and light position
    vec3 fragToLight = fragPos - light.position;
    // use the light to fragment vector to sample from the depth map
    float closestDepth = texture(light.shadowCubeMap, fragToLight).r;
    // it is currently in linear range between [0,1]. Re-transform back to original value
    closestDepth *= farPlane;
    // now get current linear depth as the length between the fragment and light position
    float currentDepth = length(fragToLight);
    // now test for shadows
    float bias = 0.05;
    float shadow = currentDepth -  bias > closestDepth ? 1.0 : 0.0;

    return shadow;
}


vec3 calcPointLight(LightSource light, vec3 normal, vec3 fragPos, vec3 viewDir) {

    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (0.0001 + light.constant + light.linear * distance +
    light.quadratic * (distance * distance));
    // combine results
    vec3 ambient  = light.ambientColor  * vec3(texture(material.diffuseMap, i_texCoord));
    vec3 diffuse  = light.diffuseColor  * diff * vec3(texture(material.diffuseMap, i_texCoord));
    vec3 specular = light.specularColor * spec;
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;
    float shadow = shadowCalculation(fragPos, light);
    return (ambient + (1.0 - shadow)*(diffuse + specular));
}

void main() {
    vec3 normal = i_normal;
    normal = normalize(normal);
    vec3 viewDir = normalize(viewPos - i_fragPos);
    float alpha = texture(material.diffuseMap, i_texCoord).a;
    vec4 result = vec4(0.0, 0.0, 0.0, alpha);
    for(int i = 0; i < MAX_LIGHTS; i++) {
        result += vec4(calcPointLight(lights[i], normal, i_fragPos, viewDir), 0.0f);
    }

    color = result;
}