package engine.components.collision;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector3f;


@Getter
@RequiredArgsConstructor
public class LineCollision extends Collider {

    private boolean shouldInitialize = true;
    private final float length;

    private float x1;
    private float z1;
    private float x2;
    private float z2;

    //    Równanie prostej ogólne
    private float A;
    private float B;
    private float C;


    public void recalculatePosition() {

        if (!shouldInitialize) return;

        Vector3f absolutePosition = gameObject.getTransform().getAbsolutePosition();
        //pozycja kolidera
        System.out.println(absolutePosition);
        Vector3f absoluteRotation = gameObject.getTransform().getParent().getRotation();

        this.x1 = Math.round(absolutePosition.x + length / 2 * (float) Math.cos(absoluteRotation.y));
        this.z1 =  Math.round(absolutePosition.z + length / 2 * (float) Math.sin(absoluteRotation.y));
        this.x2 =  Math.round(absolutePosition.x + length / 2 * (float) Math.cos(absoluteRotation.y + Math.PI));
        this.z2 =  Math.round(absolutePosition.z + length / 2 * (float) Math.sin(absoluteRotation.y + Math.PI));

        if (x1 != x2){
            // Sprawdza kolizje pionowo wzdłuż osi x
            A = -((z2 - z1) / (x2 - x1));
            B = 1;
            C = x1 * ((z2 - z1) / (x2 - x1)) - z1;
        }else {
            // Sprawdza kolizje poziomo wzdłuż osi z
            A = -((x2 - x1) / (z2 - z1));
            B = 1;
            C = z1 * ((x2 - x1) / (z2 - z1)) - x1;

        }
        shouldInitialize = false;
    }

    public float getDistanceFromPoint(float x, float z){
        if (x1 != x2) {
            return Math.abs(getA()*x + getB()*z + getC())
                    / (float) Math.sqrt(getA() * getA() + getB() * getB());
        }else{
            return Math.abs(getA()*z + getB()*x + getC())
                    / (float) Math.sqrt(getA() * getA() + getB() * getB());
        }

    }

//    Not used
    @Override
    public boolean isCollision(Collider collider) {
        return false;
    }
}
