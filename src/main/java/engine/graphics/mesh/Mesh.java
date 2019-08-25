package engine.graphics.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int vaoId;
    private int vboId;

    private Vector3f[] positions;
    private Vector3f[] normals;
    private Vector2f[] uvs;

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

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
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
        }

        Vector3f[] verticesArray = new Vector3f[vertexTempList.size()];
        positions = vertexTempList.toArray(verticesArray);
        Vector3f[] normalsArray = new Vector3f[normalTempList.size()];
        normals = normalTempList.toArray(normalsArray);
        Vector2f[] uvArray = new Vector2f[uvTempList.size()];
        uvs = uvTempList.toArray(uvArray);
    }

    private void createBuffers() {

        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        for(Vector3f vertex : positions) {
            verticesBuffer.put(vertex.x);
            verticesBuffer.put(vertex.y);
            verticesBuffer.put(vertex.z);
        }

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
    }

    public void draw() {
        //TODO
    }
}
