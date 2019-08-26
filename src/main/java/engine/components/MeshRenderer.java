package engine.components;

import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.graphics.shader.ShaderProgram;
import engine.models.Component;
import lombok.Data;

@Data
public class MeshRenderer extends Component {
    private Material material;
    private ShaderProgram shader;

    void render(Mesh mesh){

    }

    @Override
    protected Type getType() {
        return Type.TRANSFORM;
    }
}