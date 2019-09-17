package artgellary;


import artgellary.room.ColliderWall;
import artgellary.room.Room;
import engine.components.collision.Collider;
import engine.components.collision.LineCollision;
import engine.models.GameObject;
import org.joml.Vector3f;

//Główny obiekt
public class ArtGalleryRootObject extends GameObject {

    //Mapa składa się z 4 pokoi.
    Room[] rooms;
    Player player;
    float [] wallsCollidersSize = { 4f,4f, 1.6f, 2f, 2f,1.6f,1.6f,2f,2f,1.6f };
    float [] wallsCollidersPosition = { 0f,-2f  ,2f,0   ,1.2f,2f   ,0.4f,3f   ,-0.4f,3f    ,-1.2f,2f    - };


    @Override
    public void start() {
        rooms = new Room[4];
        for(int i = 0; i < 4; i++) {
            rooms[i] = new Room();
            addChild(rooms[i]);
            float rotation = (float)Math.toRadians(90.0*i);
            float radius = (float)Math.sqrt(2)*Room.MESH_WIDTH/2.0f;
            float posAngle = (float)Math.toRadians(135.0f + 90.0f * i);
            float x = (float)Math.sin(posAngle)*radius;
            float z = (float)Math.cos(posAngle)*radius;

            rooms[i].getTransform().setRotation(new Vector3f(0.0f, rotation, 0.0f));
            rooms[i].getTransform().setPosition(new Vector3f(x, 0.0f, z));

            for (int j = 0; j < wallsCollidersSize.length; j++) {
                System.out.println(j/2);
                ColliderWall colliderWall = new ColliderWall(wallsCollidersSize[j/2]);
                rooms[i].addChild(colliderWall);
                colliderWall.getTransform().setPosition(new Vector3f(0f, 0f, -2f));

            }



        }

        //Dodanie gracza który się porusza
        player = new Player(this);
        addChild(player);
    }

    @Override
    public void update() {
        for(int i = 0; i < 4; i++) {
            rooms[i].prepareLight(player.mainCamera.getCamera());
        }
    }

}
