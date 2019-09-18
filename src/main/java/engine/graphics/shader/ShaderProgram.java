package engine.graphics.shader;

import lombok.Getter;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

// A set of shaders (fragment and vertex)
public class ShaderProgram {
    private int id;
    @Getter
    private String vertexPath;
    @Getter
    private String fragmentPath;
    @Getter
    private String geometryPath;

    public ShaderProgram(String vertexPath, String fragmentPath, String geometryPath) throws Exception {
        this.geometryPath = geometryPath;
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
        init();
    }

    private void init() throws Exception {
        id = glCreateProgram();
        if (id == 0) {
            throw new Exception("Could not create Shader");
        }

        Shader vertex = new Shader(vertexPath, GL_VERTEX_SHADER);
        vertex.attach(id);

        Shader fragment = new Shader(fragmentPath, GL_FRAGMENT_SHADER);
        fragment.attach(id);

        if(geometryPath != null) {
            Shader geometry = new Shader(geometryPath, GL_GEOMETRY_SHADER);
            geometry.attach(id);
        }

        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(id, 1024));
        }

        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(id, 1024));
        }
    }

    //Get uniform location based on its name
    public int getLocation(String attribute) {
        return glGetUniformLocation(id, attribute);
    }

    public void use() {
        glUseProgram(id);
    }

}
