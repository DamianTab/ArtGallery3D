package artgellary;


import artgellary.shaders.ConstantShader;
import artgellary.shaders.StandardShader;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.models.GameObject;

public class Room extends GameObject {

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(new StandardShader(), new Material("obj/box/Box.mtl")));
            addComponent(new MeshFilter(new Mesh("obj/box/Box.obj")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        addChild(new RoomLight());
    }

    @Override
    public void update() {

    }
}
