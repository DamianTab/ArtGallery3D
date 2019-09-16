package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CircleCollision extends Collider {


    private final float radius;

    @Override
    public boolean isCollision(Collider collider) {
        if (collider instanceof LineCollision){

            return false;
        }
        //Not support collider
        else {
            throw new IllegalStateException();
        }
    }
}
