package engine.components;

import engine.models.Component;
import engine.models.GameObject;
import lombok.AllArgsConstructor;
import org.joml.Vector3f;

public class Transform extends Component {

    Vector3f position;
    Vector3f rotation;
    Vector3f scale;
    Transform parent;

    public Transform(GameObject gameObject, Vector3f position, Vector3f rotation, Vector3f scale, Transform parent) {
        super(gameObject);
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.parent = parent;
    }

    public void getMatrix(){

    }

}
