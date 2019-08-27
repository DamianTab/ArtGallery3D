package engine.graphics.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int vaoId;
    private int vboId;
    private int eboId;

    //After reading
    private Vector3f[] positions;
    private Vector3f[] normals;
    private Vector2f[] uvs;

    private Vertex[] vertices;
    private int[] indices;

    //TODO Zamienic na Vertexy

    public Mesh(String path) throws IOException {
        init(path);
    }

    private void init(String path) throws IOException {
        read(path);
        createBuffers();
    }

    // Read file content and save it to arrays;
    private void read(String path) throws IOException {

        List<Vector3f> vertexTempList = new ArrayList<>();
        List<Vector3f> normalTempList = new ArrayList<>();
        List<Vector2f> uvTempList = new ArrayList<>();
        List<VertexIndex> vertexIndicesTempList = new ArrayList<>();
        List<Integer> indexTempList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path)));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(" ");
            String identifier = split[0];
            // Vertex
            if(identifier.equals("v")) {
                Vector3f vertex = new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
                if(split.length > 4) {
                    vertex.div(Float.parseFloat(split[4]));
                }
                vertexTempList.add(vertex);
            }
            // UV
            else if(identifier.equals("vt")) {
                Vector2f uv = new Vector2f(Float.parseFloat(split[1]), Float.parseFloat(split[2]));
                uvTempList.add(uv);
            }
            // Normal
            else if(identifier.equals("vn")) {
                Vector3f normal =  new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
                normalTempList.add(normal);
            }
            // Face
            else if(identifier.equals("f")) {
                List<Integer> faceIndices = new ArrayList<>();
                for(int i = 1; i < split.length; i++) {
                    int index = -1;
                    VertexIndex vertexIndex = new VertexIndex(split[i]);
                    // Check for duplicates
                    int j = 0;
                    for(VertexIndex vi : vertexIndicesTempList) {
                        if(vi.equals(vertexIndex)) {
                            // This is already present
                            index = i;
                        }
                        j++;
                    }
                    // This is a new vertex index
                    if(index == -1) {
                        vertexIndicesTempList.add(vertexIndex);
                        index = vertexIndicesTempList.size() - 1;
                    }

                    faceIndices.add(index);
                }
                int first = faceIndices.get(0);
                for(int i = 2; i < faceIndices.size(); i++) {
                    // Add triangle
                    indexTempList.add(first);
                    indexTempList.add(faceIndices.get(i - 1));
                    indexTempList.add(faceIndices.get(i));
                }
            }
        }

        Vector3f[] verticesArray = new Vector3f[vertexTempList.size()];
        positions = vertexTempList.toArray(verticesArray);
        Vector3f[] normalsArray = new Vector3f[normalTempList.size()];
        normals = normalTempList.toArray(normalsArray);
        Vector2f[] uvArray = new Vector2f[uvTempList.size()];
        uvs = uvTempList.toArray(uvArray);
        indices = indexTempList.stream().mapToInt(integer->integer).toArray();

        vertices = new Vertex[vertexIndicesTempList.size()];
        int i = 0;
        for(VertexIndex vertexIndex : vertexIndicesTempList) {
            Vector3f position = positions[vertexIndex.getPositionId() - 1];
            Vector3f normal = normals[vertexIndex.getNormalId() - 1];
            Vector2f uv = uvs[vertexIndex.getUvId() - 1];
            vertices[i] = new Vertex(position, normal ,uv);
            i++;
        }

    }

    private void createBuffers() {

        vaoId = glGenVertexArrays();
        vboId = glGenBuffers();
        eboId = glGenBuffers();


        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length*Vertex.floatCount());
        for(Vertex vertex : vertices) {
            vertex.addToBuffer(verticesBuffer);
        }
        //TODO Upewnić się że powinno być flip!
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
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.floatCount(), 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false,  Vertex.floatCount(),  3);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false,  Vertex.floatCount(),  6);

        // Unbind
        glBindVertexArray(0);

    }

    private void bind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBindVertexArray(vaoId);
    }

    public void draw() {
        bind();
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

    }
}
