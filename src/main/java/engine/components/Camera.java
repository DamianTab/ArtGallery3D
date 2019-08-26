package engine.components;

import engine.models.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Component {

    public Camera() {
    }

    @Setter
    private Transform target;

    private float fieldOfView = 90.0f;
    private float aspect = 1.0f;
    private float near = 1.0f;
    private float far = 100.0f;

    @Getter
    private Matrix4f viewMatrix;
    @Getter
    private Matrix4f perspectiveMatrix;

    private void generateViewAndPerspective() {
        viewMatrix = new Matrix4f().identity().lookAt(getTransform().getAbsolutePosition(), target.getAbsolutePosition(), new Vector3f(0.0f, 0.0f, 1.0f));
        perspectiveMatrix = new Matrix4f().identity().perspective(fieldOfView, aspect, near, far);
    }

    @Override
    protected Type getType() {
        return Type.CAMERA;
    }
}
