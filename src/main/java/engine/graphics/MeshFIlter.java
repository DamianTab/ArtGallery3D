package engine.graphics;

import engine.models.Component;
import engine.models.GameObject;

public class MeshFIlter extends Component {

    @Override
    protected Type getType() {
        return Type.MESH_FILTER;
    }
}
