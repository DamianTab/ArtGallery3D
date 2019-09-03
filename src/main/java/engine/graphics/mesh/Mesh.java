package engine.graphics.mesh;

import engine.graphics.material.Material;
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
            ((SingleMaterial)material).getMaterial().use(program);
            for(MeshPart part : parts) {
                part.draw();
            }
        }
        else {
            for(MeshPart part : parts) {
                material.getPart(part.getMaterialName()).use(program);
                part.draw();
            }
        }
    }


}
