package artgellary;


import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import org.joml.Vector3f;

public class Room extends GameObject {

    public static final float MESH_WIDTH = 8.0f;
    private Observer observer;

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(),"obj/room/Room.mtl"));
            addComponent(new MeshFilter("obj/room/Room.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        RoomLight roomLight = new RoomLight();
        addChild(roomLight);

        observer = new Observer();
        addChild(observer);
    }

    @Override
    public void update() {
        //System.out.println(observer.getTransform().getAbsolutePosition());
    }
}
