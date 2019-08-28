package artgellary;

import engine.components.LightSource;
import engine.models.GameObject;
import org.joml.Vector3f;

public class RoomLight extends GameObject {
    @Override
    public void start() {
        LightSource lightSource = new LightSource();
        addComponent(lightSource);

        getTransform().setPosition(new Vector3f(0.0f, 2.0f, 0.0f));
    }

    @Override
    public void update() {

    }
}