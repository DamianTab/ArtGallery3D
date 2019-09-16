package artgellary.room;

import engine.components.collision.LineCollision;
import engine.models.GameObject;
import lombok.Getter;

public class ColliderWall extends GameObject {

    private LineCollision lineCollision;

    public ColliderWall(float x1, float z1, float x2, float z2) {
        super();
        lineCollision = new LineCollision(x1, z1, x2, z2);
        addComponent(lineCollision);
    }

    public void recalculate(){
        lineCollision.recalculatePosition();
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {

    }
}
