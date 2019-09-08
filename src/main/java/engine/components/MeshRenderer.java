package engine.components;

import engine.graphics.material.Material;
import engine.graphics.material.MaterialManager;
import engine.graphics.mesh.Mesh;
import engine.graphics.shader.ShaderProgram;
import engine.models.Component;
import lombok.Data;

import java.io.IOException;

@Data
public class MeshRenderer extends Component {

    public MeshRenderer(ShaderProgram shader, Material material) {
        this.material = material;
        this.shader = shader;
    }

    public MeshRenderer(ShaderProgram shader, String materialPath) throws IOException {
        this(shader, MaterialManager.getInstance().getMaterial(materialPath));
    }

    private Material material;
    private ShaderProgram shader;

    public void use(){
        shader.use();
    }

    @Override
    protected Type getType() {
        return Type.MESH_RENDERER;
    }
}
