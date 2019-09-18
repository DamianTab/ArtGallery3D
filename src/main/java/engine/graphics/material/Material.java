package engine.graphics.material;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glUniform3fv;

@Data
public class Material {
    @Getter
    protected Map<String, MaterialPart> parts = new HashMap<>();
    public MaterialPart getPart(String name) {
        return parts.get(name);
    }
}
