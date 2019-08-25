package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RectangleCollision extends Collider {

    private float length;
    private float width;


    @Override
    public boolean isCollision(Collider collider) {
        //todo Damian
        return false;
    }
}
