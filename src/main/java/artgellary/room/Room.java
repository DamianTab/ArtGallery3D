package artgellary.room;


import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import org.joml.Vector3f;

import java.util.Random;

public class Room extends GameObject {

    public static final float MESH_WIDTH = 8.0f;
    private Observer observer;
    private Random random = new Random();

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

        //observer = new Observer();
        //addChild(observer);

        for(int i = 0; i < 2; i++) {
            PaintingWall paintingWall = new PaintingWall();
            addChild(paintingWall);
            paintingWall.getTransform().setRotation(new Vector3f(0.0f, (i + 1)*(float)Math.PI/2.0f, 0.0f));
        }
    }


    @Override
    public void update() {
    }
}
