package engine.components.collision;

import lombok.Getter;

import javax.annotation.processing.Generated;

public class LineCollision extends Collider {

    private final float x1;
    private final float z1;
    private final float x2;
    private final float z2;

//    Równanie prostej ogólne
    @Getter
    private final float A;
    @Getter
    private final float B;
    @Getter
    private final float C;


    public LineCollision(float x1, float z1, float x2, float z2) {
        this.x1 = x1;
        this.z1 = z1;
        this.x2 = x2;
        this.z2 = z2;

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
