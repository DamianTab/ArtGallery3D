package engine.graphics.mesh;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joml.Vector2f;
import org.joml.Vector3f;

@Data
@AllArgsConstructor
public class Vertex {
    private Vector3f position;
    private Vector3f normal;
    private Vector2f uv;
}
