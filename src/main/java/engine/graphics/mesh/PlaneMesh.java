package engine.graphics.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class PlaneMesh extends Mesh {

    public PlaneMesh() {
        init();
    }

    public void init() {
        Vertex[] vertices = {
                new Vertex(
                        new Vector3f(-1.0f, -1.0f, 0.0f),
                        new Vector3f(0.0f, 0.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f)
                ),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, 0.0f),
                        new Vector3f(0.0f, 0.0f, -1.0f),
                        new Vector2f(1.0f, 0.0f)
                ),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, 0.0f),
                        new Vector3f(0.0f, 0.0f, -1.0f),
                        new Vector2f(1.0f, 1.0f)
                ),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, 0.0f),
                        new Vector3f(0.0f, 0.0f, -1.0f),
                        new Vector2f(0.0f, 1.0f)
                )
        };
        int[] indices = {
                0, 1, 2,
                0, 2, 3
        };
        MeshPart meshPart = new MeshPart();
        meshPart.setMaterialName("");
        meshPart.setVertices(vertices);
        meshPart.setIndices(indices);
        meshPart.createBuffers();
        parts.add(meshPart);
    }
}
