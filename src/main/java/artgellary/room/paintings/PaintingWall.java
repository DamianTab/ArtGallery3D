package artgellary.room.paintings;

import engine.models.GameObject;
import lombok.Getter;
import org.joml.Vector3f;

// Obiekt opisujący zbiór obrazów na jednej ścianie.
public class PaintingWall extends GameObject {

    @Getter
    private PaintingSpot[] paintingSpots;

    @Override
    public void start() {
        paintingSpots = new PaintingSpot[3];
        for(int i = 0; i < 3; i++) {
            paintingSpots[i] = new PaintingSpot();
            addChild( paintingSpots[i]);
            float x = (i - 1)*1.0f;
            float z = 1.95f;
            paintingSpots[i].getTransform().setPosition(new Vector3f(x, 0.0f, z));
        }
    }

    @Override
    public void update() {
    }
}
