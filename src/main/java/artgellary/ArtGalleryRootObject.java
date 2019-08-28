package artgellary;


import engine.Time;
import engine.components.Camera;
import engine.models.GameObject;
import org.joml.Math;
import org.joml.Vector3f;

import java.io.IOException;

public class ArtGalleryRootObject extends GameObject {

    @Override
    public void start() {

        Room room = new Room();
        addChild(room);

        MainCamera mainCamera = new MainCamera();
        addChild(mainCamera);

        mainCamera.setTarget(room.getTransform());
    }

    @Override
    public void update() {
    }
}
