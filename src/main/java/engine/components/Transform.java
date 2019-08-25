package engine.components;

import lombok.AllArgsConstructor;
import org.joml.Vector3f;

@AllArgsConstructor
public class Transform {

    Vector3f position;
    Vector3f rotation;
    Vector3f scale;
    Transform parent;

    public void getMatrix(){
    }

}
