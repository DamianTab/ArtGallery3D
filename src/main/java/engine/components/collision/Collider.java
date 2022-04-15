package engine.components.collision;

import engine.models.Component;
import lombok.Data;
import org.joml.Vector3f;

@Data
public abstract class Collider extends Component {

    private Vector3f centerPosition;

    public abstract boolean isCollision(Collider collider);

    @Override
    protected Type getType() {
        return Type.COLLIDER;
    }
}
