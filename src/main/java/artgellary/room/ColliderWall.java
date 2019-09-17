package artgellary.room;

import engine.components.collision.LineCollision;
import engine.models.GameObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ColliderWall extends GameObject {

    private LineCollision lineCollision;

    public ColliderWall(float length) {
        lineCollision = new LineCollision(length);
        addComponent(lineCollision);
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {

    }
}
