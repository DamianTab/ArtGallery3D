package engine.graphics.mesh;

import engine.graphics.material.Material;
import engine.graphics.material.MaterialPart;
import engine.graphics.material.SingleMaterial;
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

    protected Mesh() {

    }

    protected List<MeshPart> parts = new ArrayList<>();

    public void draw(ShaderProgram program, Material material) {
        if(material instanceof SingleMaterial) {
            SingleMaterial singleMaterial = ((SingleMaterial)material);
            singleMaterial.getMaterial().use(program);
            for(MeshPart part : parts) {
                drawMeshPart(part, singleMaterial.getMaterial());
            }
        }
        else {
            for(MeshPart part : parts) {
                MaterialPart materialPart = material.getPart(part.getMaterialName());
                materialPart.use(program);
                drawMeshPart(part, materialPart);
            }
        }
    }

    private void drawMeshPart(MeshPart meshPath, MaterialPart materialPart) {
        if(materialPart.requiresTangentSpace()) {
            meshPath.calculateTangents();
        }
        meshPath.draw();
    }


}
