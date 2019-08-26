package engine.graphics.mesh;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

@Data
@AllArgsConstructor
public class Vertex {
    private Vector3f position;
    private Vector3f normal;
    private Vector2f uv;

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(position.x);
        buffer.put(position.y);
        buffer.put(position.z);
        buffer.put(normal.x);
        buffer.put(normal.y);
        buffer.put(normal.z);
        buffer.put(uv.x);
        buffer.put(uv.y);
    }

    // How much memory does this structure takes
    public static int size() {
        return Float.SIZE * floatCount();
    }
    // How many floats does this structure uses
    public static int floatCount() {return 3 + 3 + 2;}
}
