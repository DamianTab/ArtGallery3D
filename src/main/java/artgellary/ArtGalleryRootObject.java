package artgellary;


import engine.models.GameObject;
import engine.utils.Time;
import org.joml.Vector3f;

public class ArtGalleryRootObject extends GameObject {

    Room[] rooms;

    @Override
    public void start() {
        rooms = new Room[4];
        for(int i = 0; i < 4; i++) {
            rooms[i] = new Room();
            addChild(rooms[i]);
        }

        Player player = new Player();
        addChild(player);
    }

    @Override
    public void update() {
        for(int i = 0; i < 4; i++) {
            float rotation = (float)Math.toRadians(90.0*i);
            float radius = (float)Math.sqrt(2)*Room.MESH_WIDTH/2.0f;
            float posAngle = (float)Math.toRadians(135.0f + 90.0f * i);
            float x = (float)Math.sin(posAngle)*radius;
            float z = (float)Math.cos(posAngle)*radius;

            rooms[i].getTransform().setRotation(new Vector3f(0.0f, rotation, 0.0f));
            rooms[i].getTransform().setPosition(new Vector3f(x, 0.0f, z));
        }
    }
}
