package engine.components;

import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.MeshManager;
import engine.models.Component;
import lombok.Data;

import java.io.IOException;

@Data

public class MeshFilter extends Component {

    public MeshFilter(Mesh mesh) {
        this.mesh = mesh;
    }

    public MeshFilter(String meshPath) throws IOException {
        this(MeshManager.getInstance().getMesh(meshPath));
    }

    private Mesh mesh;



    @Override
    protected Type getType() {
        return Type.MESH_FILTER;
    }
}
