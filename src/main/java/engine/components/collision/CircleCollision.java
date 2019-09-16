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
            if (line.getX1() == line.getX2()){
                if (!isBetween(line.getZ1(), line.getZ2(), z))
                    return false;
            }else {
                if (!isBetween(line.getX1(), line.getX2(), x))
                    return false;
            }

            float distance = Math.abs(line.getA()*x + line.getB()*z + line.getC())
                    / (float) Math.sqrt(line.getA() * line.getA() + line.getB() * line.getB());

            if (distance <= radius) {
                System.out.println(line.getX1() +"  " +line.getZ1() +"  " +line.getX2() +"  " +line.getZ2());
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

    private boolean isBetween(float first, float second, float value){
        if (first < second){
            if (first < value && value < second){
                return true;
            }else {
                return false;
            }
        }else {
            if (second < value && value < first){
                return true;
            }else {
                return false;
            }
        }

    }
}
