package engine.graphics.shader;

import lombok.Getter;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int id;
    @Getter
    private String vertexPath;
    @Getter
    private String fragmentPath;

    public ShaderProgram(String vertexPath, String fragmentPath) throws Exception {
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

    public boolean equals(ShaderProgram other) {
        return vertexPath.equals(other.vertexPath) && fragmentPath.equals(other.fragmentPath);
    }
}
