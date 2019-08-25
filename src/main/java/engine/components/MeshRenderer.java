package engine.components;

import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.models.Component;
import lombok.Data;

@Data
public class MeshRenderer extends Component {
    private Material material;

    void render(Mesh mesh){
        //todo
    }

    @Override
    protected Type getType() {
        return Type.TRANSFORM;
    }
}
