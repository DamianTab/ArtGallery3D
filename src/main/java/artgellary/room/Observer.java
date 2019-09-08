package artgellary.room;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import engine.utils.Time;
import org.joml.Vector3f;

// Obiekt opisujący postać poruszajacą się w pomieszczeniu i patrzącą się na obrazy.
public class Observer extends GameObject {

    private final float speed = 0.0005f;
    private final float angularSpeed = 0.005f;
    private Vector3f target;
    private float targetAngle;
    private boolean atTarget;

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(),"obj/observer/StickFigurea.mtl"));
            addComponent(new MeshFilter("obj/observer/StickFigurea.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTransform().setScale(0.015f);
    }



    public void goTo(Vector3f target, float angle) {
        this.target = target;
        this.targetAngle = angle;
        this.atTarget = false;
    }

    @Override
    public void update() {
        if(target != null) {
            if(atTarget == false) {
                Vector3f direction = new Vector3f();
                target.sub(getTransform().getPosition(), direction);
                float distance = direction.length();
                float shift = speed * Time.DELTA_TIME;
                float angle;
                if (distance > shift) {
                    direction.normalize().mul(speed * Time.DELTA_TIME);
                    getTransform().shiftBy(direction);
                    angle = (float) Math.atan2(-direction.z, direction.x) + (float) Math.toRadians(90.0);
                } else {
                    angle = targetAngle;
                }
                getTransform().shiftBy(direction);
                rotateTowards(angle);
            }
            else {
                rotateTowards(targetAngle);
            }
        }
    }

    private void rotateTowards(float angle) {
        Vector3f currentRot = getTransform().getRotation();
        float currentAngle = currentRot.y;
        float delta = Time.DELTA_TIME*angularSpeed;
        float resultAngle;
        if(Math.abs(currentAngle - angle) < delta) {
            resultAngle = angle;
        }
        else {
            if (angle > currentAngle) {
                resultAngle = currentAngle + delta;
            } else {
                resultAngle = currentAngle - delta;
            }
        }
        getTransform().setRotation(new Vector3f(currentRot.x, resultAngle, currentRot.z));
    }
}
