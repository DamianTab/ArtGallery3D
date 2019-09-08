package artgellary.room;

import engine.models.GameObject;
import engine.utils.Rand;
import org.joml.Vector3f;

// Obiekt opisujący miejsce w którym wisi obraz. Potrzebny jest on do ustawienia ludzika w prawidłowe miejsce (poniewaz ma stała współrzędną y)
public class PaintingSpot extends GameObject {
    @Override
    public void start() {
        Painting painting = new Painting();
        addChild(painting);
        float size = 0.015f + 0.02f * Rand.RANDOM.nextFloat();
        painting.getTransform().setScale(size);
        painting.getTransform().setRotation(new Vector3f((float)Math.PI/2.0f, 0.0f, 0.0f));
        float y = 1.4f + 0.2f * Rand.RANDOM.nextFloat();
        painting.getTransform().setPosition(new Vector3f(0.0f, y, 0.0f));
    }

    public Vector3f getObservePosition() {
        Vector3f position = getTransform().getPosition();
        Vector3f result = new Vector3f(position.x, 0.0f, position.z - 1.0f);
        //getTransform().getPosition().add(new Vector3f(), result);
        return result;
    }

    @Override
    public void update() {

    }
}
