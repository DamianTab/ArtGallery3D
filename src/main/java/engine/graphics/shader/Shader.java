package engine.graphics.shader;

import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static org.lwjgl.opengl.GL20.*;

// Class representing a single shader file
public class Shader {

    @Getter
    private int id;

    public Shader(String path, int shaderType) throws IOException {
        init(path, shaderType);
    }

    private void init(String path, int shaderType) throws IOException {
        id = glCreateShader(shaderType);
        // Read shader cope as string
        StringWriter writer = new StringWriter();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        IOUtils.copy(inputStream, writer, "UTF-8");
        String code = writer.toString();
        glShaderSource(id, code);
        glCompileShader(id);
    }

    public void attach(int program) {
        glAttachShader(program, id);
    }

//    Not used
    public void deattach(int program) {
        glDetachShader(program, id);
    }


}
