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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3fv;

@Data
public class Material {
    @Getter
    protected Map<String, MaterialPart> parts = new HashMap<>();
    public MaterialPart getPart(String name) {
        return parts.get(name);
    }
}
