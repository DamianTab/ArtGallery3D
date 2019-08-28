package engine.components;

import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.graphics.shader.ShaderProgram;
import engine.models.Component;
import lombok.Data;

@Data
public class MeshRenderer extends Component {

    public MeshRenderer(ShaderProgram shader, Material material) {
        this.material = material;
        this.shader = shader;
    }

    private Material material;
    private ShaderProgram shader;

    public void use(){
        shader.use();
        material.use(shader);
    }

    @Override
    protected Type getType() {
        return Type.MESH_RENDERER;
    }
}
