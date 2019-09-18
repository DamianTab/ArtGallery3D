package engine.components.collision;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector3f;


@Getter
@RequiredArgsConstructor
public class WallCollision extends Collider {

    private boolean lookingForCollisionWithXAxis;
    private boolean shouldInitialize = true;
    private final float length;

    // Pozycje dwóch końcy collidera ściany
    private float x1;
    private float z1;
    private float x2;
    private float z2;

    // Równanie prostej ogólne
    private float A;
    private float B;
    private float C;

    // Jednorazowo oblicza końce lini kolidera i współczynniki prostej w postaci ogólnej
    public void calculateLineEndingsAndLineCoefficients() {

        if (!shouldInitialize) return;

        Vector3f absolutePosition = gameObject.getTransform().getAbsolutePosition();
       //todo komment
        System.out.println(absolutePosition);
        Vector3f roomRotation = gameObject.getTransform().getParent().getRotation();
        Vector3f wallCollisionRotation = gameObject.getTransform().getRotation();
        Vector3f absoluteRotation = new Vector3f();
        roomRotation.add(wallCollisionRotation, absoluteRotation);

        this.x1 = Math.round((absolutePosition.x + length / 2 * (float) Math.cos(absoluteRotation.y))*10f)/10f;
        this.z1 =  Math.round((absolutePosition.z + length / 2 * (float) Math.sin(absoluteRotation.y))*10f)/10f;
        this.x2 =  Math.round((absolutePosition.x + length / 2 * (float) Math.cos(absoluteRotation.y + Math.PI))*10f)/10f;
        this.z2 =  Math.round((absolutePosition.z + length / 2 * (float) Math.sin(absoluteRotation.y + Math.PI))*10f)/10f;

        System.out.println(x1 + "  "+z1 + "  "+x2 + "  "+z2);

        // Flaga która mówi o tym czy sprawdzamy kolizje wzdłóż osi X. Tzn z1 == z2 i później patrzymy tylko na X (bo one się różnią)
        lookingForCollisionWithXAxis = x1 != x2 ? true : false;

        if (lookingForCollisionWithXAxis){
            // Przygotowuje współrzędne lini pionowo wzdłuż osi x
            calculateLineCoefficients();
        }else {
            // Przygotowuje współrzędne lini poziomo wzdłuż osi z
            swapXWithZ();
            calculateLineCoefficients();
            swapXWithZ();
        }
        shouldInitialize = false;
    }

    public float getDistanceFromPoint(float x, float z){
        if (lookingForCollisionWithXAxis) {
            // Oblicza odległość dla lini pionowej wzdłóż osi x
            return calculateDistance(x,z);
        }else{
            // Oblicza odległość dla lini pionowej wzdłóż osi z
            return calculateDistance(z,x);
        }

    }

    private float calculateDistance(float variable1, float variable2){
        return Math.abs(getA()*variable1 + getB()*variable2 + getC())
                / (float) Math.sqrt(getA() * getA() + getB() * getB());
    }

    private void calculateLineCoefficients(){
        A = -((z2 - z1) / (x2 - x1));
        B = 1;
        C = x1 * ((z2 - z1) / (x2 - x1)) - z1;
    }

    private void swapXWithZ()
    {
        float temp1 = x1;
        float temp2 = x2;
        x1 = z1;
        x2 = z2;
        z1 = temp1;
        z2 = temp2;
    }

//    Not used
    @Override
    public boolean isCollision(Collider collider) {
        return false;
    }
}
