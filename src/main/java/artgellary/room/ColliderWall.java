package artgellary.room;

import engine.components.collision.WallCollision;
import engine.models.GameObject;

public class ColliderWall extends GameObject {

    public ColliderWall(float length) {
        super();
        WallCollision wallCollision = new WallCollision(length);
        addComponent(wallCollision);
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {

    }
}
