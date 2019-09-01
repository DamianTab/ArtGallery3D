package artgellary;


import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;

public class Room extends GameObject {

    public static final float MESH_WIDTH = 8.0f;

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(),"obj/room/Room.mtl"));
            addComponent(new MeshFilter("obj/room/Room.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        addChild(new RoomLight());
    }

    @Override
    public void update() {

    }
}
