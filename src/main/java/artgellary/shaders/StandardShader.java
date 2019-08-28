package artgellary.shaders;

import engine.graphics.shader.ShaderProgram;

public class StandardShader extends ShaderProgram {
    public StandardShader() throws Exception {
        super("glsl/standard/v_standard.glsl", "glsl/standard/f_standard.glsl");
    }
}
