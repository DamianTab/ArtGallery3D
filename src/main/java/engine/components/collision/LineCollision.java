package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LineCollision extends Collider {

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    @Override
    public boolean isCollision(Collider collider) {
        //todo Damian
        return false;
    }
}
