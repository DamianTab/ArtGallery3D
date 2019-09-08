package engine.graphics.mesh;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

// Actual mesh with vertices
public class MeshPart {
    private int vaoId;
    private int vboId;
    private int eboId;

    @Setter
    private Vertex[] vertices;
    @Setter
    private int[] indices;
    @Getter
    @Setter
    private String materialName;
    private boolean tangentsCalculated = false;

    public void createBuffers() {

        vaoId = glGenVertexArrays();
        vboId = glGenBuffers();
        eboId = glGenBuffers();

        insertBufferData();
    }

    // Insert current vertices to buffers
    private void insertBufferData() {

        // Crete float buffer for vertices data
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length*Vertex.floatCount());
        for(Vertex vertex : vertices) {
            vertex.addToBuffer(verticesBuffer);
        }
        verticesBuffer.flip();

        // Create int buffer for indices data
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBindVertexArray(vaoId);

        //Insert vertices and indices buffers

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        //Set vertex attributes in order: position, normal, uv, tangent, bitangent

        int floatSize = 4;
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.floatCount()*floatSize, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  3*floatSize);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  6*floatSize);
        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 3, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  8*floatSize);
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(4, 3, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  11*floatSize);

        // Unbind
        glBindVertexArray(0);
    }

    private void bind()
    {
        glBindVertexArray(vaoId);
    }

    private  void unbind() {
        glBindVertexArray(0);
    }

    public void draw() {
        bind();
        // Draw Elements: each vertex has its own index and can be drawn multiple times.
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        unbind();
    }

    // Only call when needed: Calculate tangents and bitangents of normal vector
    // This is only needed to create tbn matrix.
    // Po polsku są to prostopadłe do wektora normalnego tak aby zdefiniować przestrzeń wektora normalnego jako koordynaty x, y ,z
    public void calculateTangents() {
        if(!tangentsCalculated) {
            for (int i = 0; i < indices.length; i += 3) {

                // Each triangle has the same normal so the tangents and bitangets will be the same.
                Vertex v1 = vertices[indices[i]];
                Vertex v2 = vertices[indices[i + 1]];
                Vertex v3 = vertices[indices[i + 2]];

                // The math behind this is complicated
                Vector3f edge1 = new Vector3f();
                Vector3f edge2 = new Vector3f();
                Vector2f deltaUV1 = new Vector2f();
                Vector2f deltaUV2 = new Vector2f();
                v2.getPosition().sub(v1.getPosition(), edge1);
                v3.getPosition().sub(v1.getPosition(), edge2);
                v2.getUv().sub(v1.getUv(), deltaUV1);
                v3.getUv().sub(v1.getUv(), deltaUV2);
                Vector3f tangent = new Vector3f();
                Vector3f bitangent = new Vector3f();
                float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);
                tangent.x = f * (deltaUV2.y * edge1.x - deltaUV1.y * edge2.x);
                tangent.y = f * (deltaUV2.y * edge1.y - deltaUV1.y * edge2.y);
                tangent.z = f * (deltaUV2.y * edge1.z - deltaUV1.y * edge2.z);
                tangent.normalize();

                bitangent.x = f * (-deltaUV2.x * edge1.x + deltaUV1.x * edge2.x);
                bitangent.y = f * (-deltaUV2.x * edge1.y + deltaUV1.x * edge2.y);
                bitangent.z = f * (-deltaUV2.x * edge1.z + deltaUV1.x * edge2.z);
                bitangent.normalize();

                // Each vertex can be used multiple times so we take an average tangent and bitangent to achieve a smooth effect.
                // One way to do this is to add all tangents and bitangents and then normalise them.
                v1.getTangent().add(tangent);
                v1.getBitangent().add(bitangent);
                v2.getTangent().add(tangent);
                v2.getBitangent().add(bitangent);
                v3.getTangent().add(tangent);
                v3.getBitangent().add(bitangent);
            }
            for(Vertex v : vertices) {
                v.getTangent().normalize();
                v.getBitangent().normalize();
            }
            // We need to update new vertex data in buffers
            insertBufferData();
            tangentsCalculated = true;
        }
    }
}
