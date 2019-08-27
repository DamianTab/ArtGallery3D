package artgellary;


import engine.Time;
import engine.components.Camera;
import engine.models.GameObject;
import org.joml.Math;
import org.joml.Vector3f;

import java.io.IOException;

public class ArtGalleryRootObject extends GameObject {

    Camera camera;

    @Override
    public void start() {

        Room room = new Room();
        addChild(room);

        camera = new Camera(room.getTransform());
        addComponent(camera);
    }

    @Override
    public void update() {
        double t =( (double)Time.getTimeElapsedMillis())/1000000000000.0;
        float sin = (float)Math.sin(t);
        float cos = (float)Math.cos(t);
        camera.getTransform().setPosition(new Vector3f( 3.0f*sin, 1.0f*sin, 2.0f*cos));

    }
}
