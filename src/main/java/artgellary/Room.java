package artgellary;

import artgellary.shaders.ConstantShader;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.models.GameObject;

public class Room extends GameObject {

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(new ConstantShader(), new Material()));
            addComponent(new MeshFilter(new Mesh("box.obj")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}