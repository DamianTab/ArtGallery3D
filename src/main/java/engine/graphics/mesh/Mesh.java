package engine.graphics.mesh;

import engine.graphics.material.Material;
import engine.graphics.shader.ShaderProgram;
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

    private List<MeshPart> parts = new ArrayList<>();

    public Mesh(String path) throws IOException {
        init(path);
    }

    private void init(String path) throws IOException {
        read(path);
    }

    // Read file content and save it to arrays;
    private void read(String path) throws IOException {

        List<Vector3f> vertexTempList = new ArrayList<>();
        List<Vector3f> normalTempList = new ArrayList<>();
        List<Vector2f> uvTempList = new ArrayList<>();
        List<VertexIndex> vertexIndicesTempList = new ArrayList<>();
        List<Integer> indexTempList = new ArrayList<>();
        String materialName = null;


        MeshPart part = null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path)));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] split = line.split("[ ]+");
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
                Vector2f uv = new Vector2f();
                uv.x = Float.parseFloat(split[1]);
                // Support for models created in 3DS Max
                if(split.length > 3) {
                    uv.y = 1 - Float.parseFloat(split[2]);
                }
                else {
                    uv.y = Float.parseFloat(split[2]);
                }
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
                            index = j;
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
            else if(identifier.equals("usemtl")) {
                if(vertexIndicesTempList.size() > 0) {
                    part = generateMeshPart(vertexTempList, normalTempList, uvTempList, indexTempList, vertexIndicesTempList, materialName);
                    parts.add(part);
                }
                materialName = split[1];
            }
        }
        part = generateMeshPart(vertexTempList, normalTempList, uvTempList, indexTempList, vertexIndicesTempList, materialName);
        parts.add(part);
    }

    private MeshPart generateMeshPart(List<Vector3f> vertexTempList, List<Vector3f> normalTempList, List<Vector2f> uvTempList, List<Integer> indexTempList, List<VertexIndex> vertexIndicesTempList, String materialName) {
        MeshPart result = new MeshPart();
        result.setMaterialName(materialName);
        result.setIndices(indexTempList.stream().mapToInt(integer->integer).toArray());

        Vertex[] vertices = new Vertex[vertexIndicesTempList.size()];
        int i = 0;
        for(VertexIndex vertexIndex : vertexIndicesTempList) {
            Vector3f position = vertexTempList.get(vertexIndex.getPositionId() - 1);
            Vector3f normal = normalTempList.get(vertexIndex.getNormalId() - 1);
            Vector2f uv = uvTempList.get(vertexIndex.getUvId() - 1);
            vertices[i] = new Vertex(position, normal ,uv);
            i++;
        }
        result.setVertices(vertices);
        result.createBuffers();

//        vertexTempList.clear();
//        normalTempList.clear();
//        uvTempList.clear();
        indexTempList.clear();
        vertexIndicesTempList.clear();
        return result;
    }

    public void draw(ShaderProgram program, Material material) {
        for(MeshPart part : parts) {
            material.getPart(part.getMaterialName()).use(program);
            part.draw();
        }
    }


}
