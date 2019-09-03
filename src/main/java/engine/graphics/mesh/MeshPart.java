package engine.graphics.mesh;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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

    public void createBuffers() {

        vaoId = glGenVertexArrays();
        vboId = glGenBuffers();
        eboId = glGenBuffers();


        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length*Vertex.floatCount());
        for(Vertex vertex : vertices) {
            vertex.addToBuffer(verticesBuffer);
        }
        verticesBuffer.flip();

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBindVertexArray(vaoId);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glEnableVertexAttribArray(0);
        int floatSize = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.floatCount()*floatSize, 0);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  3*floatSize);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false,  Vertex.floatCount()*floatSize,  6*floatSize);

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
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        unbind();
    }
}
