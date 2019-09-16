package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CircleCollision extends Collider {

    private final float radius;

    @Override
    public boolean isCollision(Collider collider) {

        float x = gameObject.getTransform().getAbsolutePosition().x;
        float z = gameObject.getTransform().getAbsolutePosition().z;
        
        if (collider instanceof LineCollision){
            LineCollision line = (LineCollision) collider;
            float distance = Math.abs(line.getA()*x + line.getB()*z + line.getC())
                    / (float) Math.sqrt(line.getA() * line.getA() + line.getB() * line.getB());

            if (distance <= radius) {
                return true;
            }else {
                return false;
            }
        }
        //Not support collider
        else {
            throw new IllegalStateException();
        }
    }
}
