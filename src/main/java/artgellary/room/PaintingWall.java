package artgellary.room;

import engine.models.GameObject;
import engine.utils.Rand;
import org.joml.Vector3f;

public class PaintingWall extends GameObject {

    @Override
    public void start() {
        for(int i = 0; i < 3; i++) {
            Painting painting = new Painting();
            addChild(painting);
            float size = 0.015f + 0.02f * Rand.RANDOM.nextFloat();
            painting.getTransform().setScale(size);
            painting.getTransform().setRotation(new Vector3f((float)Math.PI/2.0f, 0.0f, 0.0f));

            float x = (i - 1)*1.0f;
            float z = 1.95f;
            float y = 1.4f + 0.2f * Rand.RANDOM.nextFloat();
            painting.getTransform().setPosition(new Vector3f(x, y, z));
        }
    }

    @Override
    public void update() {

    }
}
