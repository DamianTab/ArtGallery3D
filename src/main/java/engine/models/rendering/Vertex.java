package engine.models.rendering;

import lombok.Data;
import org.joml.Vector2f;
import org.joml.Vector3f;

@Data
public class Vertex {
    private Vector3f position;
    private Vector3f normal;
    private Vector2f uv;
}
