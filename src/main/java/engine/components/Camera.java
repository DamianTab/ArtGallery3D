package engine.components;

import engine.models.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;


public class Camera extends Component {

    public Camera() {
    }

    @Getter
    @Setter
    private float fieldOfView = 90.0f;
    @Getter
    @Setter
    private float aspect = 1.0f;
    @Getter
    @Setter
    private float near = 0.1f;
    @Getter
    @Setter
    private float far = 100.0f;

    @Getter
    private Matrix4f viewMatrix;
    @Getter
    private Matrix4f perspectiveMatrix;

    public void generateViewAndPerspective() {
        Vector3f eye = getTransform().getAbsolutePosition();
        Vector3f target = new Vector3f();
        eye.add(generateFrontVector(), target);
        viewMatrix = new Matrix4f().identity().lookAt(eye, target, new Vector3f(0.0f, 1.0f, 0.0f));
        perspectiveMatrix = new Matrix4f().identity().perspective(fieldOfView, aspect, near, far);
    }



    public Vector3f generateFrontVector() {
        Vector3f rotation = getTransform().getRotation();
        float yaw = rotation.y;
        float pitch = rotation.x;
        return new Vector3f(
                (float) (Math.cos(yaw) * Math.cos(pitch)),
                (float) (Math.sin(pitch)),
                (float) (Math.sin(yaw) * Math.cos(pitch))).normalize();

    }

    @Override
    protected Type getType() {
        return Type.CAMERA;
    }
}
