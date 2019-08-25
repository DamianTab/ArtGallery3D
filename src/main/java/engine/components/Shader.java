package engine.components;

import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    @Getter
    private int id;

    public Shader(String path, int shaderType) throws IOException {
        init(path, shaderType);
    }

    private void init(String path, int shaderType) throws IOException {
        id = glCreateShader(shaderType);
        String code = FileUtils.readFileToString(new File(path), "UTF-8");
        glShaderSource(id, code);
        glCompileShader(id);
    }

    public void attach(int program) {
        glAttachShader(program, id);
    }

    public void deattach(int program) {
        glDetachShader(program, id);
    }


}
