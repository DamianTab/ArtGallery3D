package engine.graphics.material;

import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.utils.FloatBufferUtils;
import lombok.Setter;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3fv;

public class MaterialPart {
    @Setter
    private Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    @Setter
    private Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    @Setter
    private Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);
    @Setter
    private Texture ambientMap;
    @Setter
    private Texture diffuseMap;
    @Setter
    private Texture specularMap;
    @Setter
    private float shininess = 50.0f;

    public void use(ShaderProgram program) {

        // Use textures
        if(ambientMap != null) {
            ambientMap.use(program);
        }
        if(diffuseMap != null) {
            diffuseMap.use(program);
        }
        if(specularMap != null) {
            specularMap.use(program);
        }

        // Set texture colors
        glUniform3fv(program.getLocation("material.ambientColor"), FloatBufferUtils.vector3ToFloatBuffer(ambientColor));
        glUniform3fv(program.getLocation("material.diffuseColor"), FloatBufferUtils.vector3ToFloatBuffer(diffuseColor));
        glUniform3fv(program.getLocation("material.specularColor"), FloatBufferUtils.vector3ToFloatBuffer(specularColor));
        glUniform1f(program.getLocation("material.shininess"), shininess);
    }
}
