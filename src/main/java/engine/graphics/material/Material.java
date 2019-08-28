package engine.graphics.material;

//todo

import engine.graphics.texture.Texture;
import engine.graphics.shader.ShaderProgram;
import engine.utils.FloatBufferUtils;
import lombok.Data;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3fv;

@Data
public class Material {
    @Getter
    private String path;
    private Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Texture ambientMap;
    private Texture diffuseMap;
    private Texture specularMap;
    private float shininess = 30.0f;

    public Material(String path) throws IOException {
        this.path = path;
        init();
    }

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

    private void init() throws IOException {
        //Parse mtl
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(" ");
            String identifier = split[0];
            if(identifier.equals("Ka")) {
                ambientColor = readColor(split);
            } else if(identifier.equals("Kd")) {
                diffuseColor = readColor(split);
            } else if(identifier.equals("Ks")) {
                diffuseColor = readColor(split);
            } else if(identifier.equals("map_Ka")) {
                ambientMap = readTexture(split, Texture.Type.AMBIENT);
            } else if(identifier.equals("map_Kd")) {
                diffuseMap = readTexture(split, Texture.Type.DIFFUSE);
            } else if(identifier.equals("map_Ks")) {
                specularMap = readTexture(split, Texture.Type.SPECULAR);
            } else if(identifier.equals("Ns")) {
                shininess = Float.parseFloat(split[1]);
            }
        }
    }

    private Vector3f readColor(String[] split) {
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);
        return new Vector3f(x ,y, z);
    }

    private Texture readTexture(String[] split, Texture.Type type) throws IOException {
        String texName = split[1];
        String parent = Paths.get(path).getParent().toString();
        String texPath = parent + File.separatorChar + texName;
        return new Texture(texPath, type);
    }
}
