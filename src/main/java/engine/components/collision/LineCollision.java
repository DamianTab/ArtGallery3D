package engine.components.collision;

import artgellary.room.ColliderWall;
import lombok.Getter;
import org.joml.Vector3f;

@Getter
public class LineCollision extends Collider {

    private float x1;
    private float z1;
    private float x2;
    private float z2;

//    Równanie prostej ogólne
    private float A;
    private float B;
    private float C;

    public LineCollision(float x1, float z1, float x2, float z2) {
        this.x1 = x1;
        this.z1 = z1;
        this.x2 = x2;
        this.z2 = z2;
    }

    public void recalculatePosition() {

//        Vector3f firstPoint = new Vector3f();
//        Vector3f secondPoint = new Vector3f();
//
//        //Przepisanie rotacji z komponentu wyżej
//        Vector3f lol = gameObject.getTransform().getParent().getRotation();
//        gameObject.getTransform().setRotation(lol);
//
//        gameObject.getTransform().getParent().getPosition().add(x1,0,z1, firstPoint);
//        gameObject.getTransform().setPosition(firstPoint);
//        this.x1 = gameObject.getTransform().getAbsolutePosition().x;
//        this.z1 = gameObject.getTransform().getAbsolutePosition().z;
//
//        gameObject.getTransform().getParent().getPosition().add(x2,0,z2, secondPoint);
//        gameObject.getTransform().setPosition(secondPoint);
//        this.x2 = gameObject.getTransform().getAbsolutePosition().x;
//        this.z2 = gameObject.getTransform().getAbsolutePosition().z;

        this.x1 = gameObject.getTransform().getAbsolutePosition().x + x1;
        this.z1 = gameObject.getTransform().getAbsolutePosition().z + z2;
        this.x2 = gameObject.getTransform().getAbsolutePosition().x + x2;
        this.z2 = gameObject.getTransform().getAbsolutePosition().z + z2;

        A = -((z2-z1) / (x2-x1));
        B = 1;
        C = x1 * ((z2-z1) / (x2-x1)) - z1;

    }

    @Override
    public boolean isCollision(Collider collider) {
        //todo Damian
        return false;
    }
}
