package artgellary;


import engine.components.Camera;
import engine.models.GameObject;
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

        camera.getTransform().setPosition(new Vector3f( -5.0f, 0.0f, 0.0f));
    }

    @Override
    public void update() {

    }
}
