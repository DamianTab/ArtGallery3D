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
            //Wazna linijka ktora oblicza wszystkie wspołrzedne względem świata
            line.recalculatePosition();

            //Sprawdza czy współrzedne gracza sa w danym odcinku
            if (line.getX1() == line.getX2()){
                if (!isBetween(line.getZ1(), line.getZ2(), z)){
                    return false;
                }

            }else {
                if (!isBetween(line.getX1(), line.getX2(), x)){
                    return false;
                }
            }

            //Obliczanie odleglosci od prostej w postaci ogólnej
            float distance = line.getDistanceFromPoint(x,z);

            //Jesli wspolrzedne pasują to sprawdza odległość od tego odcinka - gdy jest mniejsza lub rowna to jest kolizja
            if (distance <= radius) {
                System.out.println(line.getX1() +"  " +line.getZ1() +"  " +line.getX2() +"  " +line.getZ2());
                return true;
            }else {
                return false;
            }
        }
        //Not supported collider
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
