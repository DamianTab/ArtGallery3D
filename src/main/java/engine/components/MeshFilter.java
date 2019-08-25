package engine.components;

import engine.graphics.mesh.Mesh;
import engine.models.Component;
import lombok.Data;

@Data
public class MeshFilter extends Component {
    private Mesh mesh;

    @Override
    protected Type getType() {
        return Type.MESH_FILTER;
    }
}
