package engine.components;

import engine.models.Component;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Component {

    public Camera() {

    }

    private Transform target;
    private Transform transform;

    @Getter
    private Matrix4f viewMatrix;
    @Getter
    private Matrix4f perspectiveMatrix;

    private void generateViewAndPerspective() {
        //todo
    }
}
