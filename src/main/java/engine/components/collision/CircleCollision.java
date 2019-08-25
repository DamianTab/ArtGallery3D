package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CircleCollision extends Collider {

    private final float radius;

    @Override
    public boolean isCollision(Collider collider) {
        //todo Damian
        return false;
    }
}
