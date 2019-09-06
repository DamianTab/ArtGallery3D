package artgellary.room;

import engine.components.LightSource;
import engine.models.GameObject;
import engine.utils.Time;
import org.joml.Vector3f;

public class RoomLight extends GameObject {
    @Override
    public void start() {
        LightSource lightSource = new LightSource();
        addComponent(lightSource);
        lightSource.setDiffuseColor(new Vector3f(1.0f, 1.0f, 0.0f));
        getTransform().setPosition(new Vector3f(0.0f, 2.0f, 0.0f));
    }

    @Override
    public void update() {
        double angle = Time.TIME_ELAPSED * 0.001;
        float radius = 1.0f;
        float x = (float) Math.cos(angle) * radius;
        float y = getTransform().getPosition().y;
        float z = (float) Math.sin(angle) * radius;
        getTransform().setPosition(new Vector3f(x, y, z));
    }
}
