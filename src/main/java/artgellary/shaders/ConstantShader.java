package artgellary.shaders;

import engine.graphics.shader.ShaderProgram;

public class ConstantShader extends ShaderProgram {
    public ConstantShader() throws Exception {
        super("glsl/constant/v_constant.glsl", "glsl/constant/f_constant.glsl");
    }
}
