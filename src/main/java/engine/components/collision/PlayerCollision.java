package engine.components.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerCollision extends Collider {

    private final float radius;

    @Override
    public boolean isCollision(Collider collider) {

        float x = gameObject.getTransform().getAbsolutePosition().x;
        float z = gameObject.getTransform().getAbsolutePosition().z;

        if (collider instanceof WallCollision) {
            WallCollision wallCollision = (WallCollision) collider;
            //Wazna linijka, która oblicza wszystkie wspołrzedne względem świata oraz współczynniki równania prostej w postaci ogólnej
            wallCollision.calculateLineEndingsAndLineCoefficients();

            //Sprawdza czy współrzedne gracza sa w danym odcinku
            if (wallCollision.isLookingForCollisionWithXAxis()) {
                //wzdluz osi x
                if (!isBetween(wallCollision.getX1(), wallCollision.getX2(), x)) return false;
            } else {
                //wzdluz osi z
                if (!isBetween(wallCollision.getZ1(), wallCollision.getZ2(), z)) return false;
            }

            //Obliczanie odleglosci od prostej w postaci ogólnej
            float distance = wallCollision.getDistanceFromPoint(x, z);

            //Jesli wspolrzedne pasują to sprawdza odległość od tego odcinka - gdy jest mniejsza lub rowna to jest kolizja
            return distance <= radius ? true : false;
        }
        //Not supported collider
        else {
            throw new IllegalStateException();
        }
    }

    private boolean isBetween(float first, float second, float value) {
        if (first < second) {
            return first < value && value < second ? true : false;
        } else {
            return second < value && value < first ? true : false;
        }
    }
}
