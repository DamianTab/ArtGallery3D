package engine.graphics.shader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int id;

    public ShaderProgram(String vertexPath, String fragmentPath) throws Exception {
        init(vertexPath, fragmentPath);
    }

    private void init(String vertexPath, String fragmentPath) throws Exception {
        id = glCreateProgram();
        if (id == 0) {
            throw new Exception("Could not create Shader");
        }

        Shader vertex = new Shader(vertexPath, GL_VERTEX_SHADER);
        vertex.attach(id);

        Shader fragment = new Shader(fragmentPath, GL_FRAGMENT_SHADER);
        fragment.attach(id);

        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(id, 1024));
        }

        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(id, 1024));
        }
    }

    public int getLocation(String attribute) {
        return glGetUniformLocation(id, attribute);
    }

    public void use() {
        glUseProgram(id);
    }
}
