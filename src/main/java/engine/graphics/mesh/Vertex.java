package engine.graphics.mesh;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

@Data
public class Vertex {

    public Vertex(Vector3f position, Vector3f normal, Vector2f uv) {
        this.position = position;
        this.normal = normal;
        this.uv = uv;
        this.tangent = new Vector3f();
        this.bitangent = new Vector3f();
    }

    private Vector3f position;
    private Vector3f normal;
    private Vector2f uv;
    private Vector3f tangent;
    private Vector3f bitangent;

    // Put vertex data into buffer
    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(position.x);
        buffer.put(position.y);
        buffer.put(position.z);
        buffer.put(normal.x);
        buffer.put(normal.y);
        buffer.put(normal.z);
        buffer.put(uv.x);
        buffer.put(uv.y);
        buffer.put(tangent.x);
        buffer.put(tangent.y);
        buffer.put(tangent.z);
        buffer.put(bitangent.x);
        buffer.put(bitangent.y);
        buffer.put(bitangent.z);
    }

    // How many floats does this structure uses
    public static int floatCount() {return 3 + 3 + 2 + 3 + 3;}
}
