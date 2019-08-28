package engine.components;

import engine.graphics.shader.ShaderProgram;
import engine.models.Component;
import engine.utils.FloatBufferUtils;
import lombok.Data;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL20.*;

@Data
public class LightSource extends Component {
    private float constant = 0.0f;
    private float linear = 0.0f;
    private float quadratic = 1.0f;
    private Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);

    public void use(ShaderProgram program, int i) {
        String prefix = "lights[" + i + "].";
        glUniform3fv(program.getLocation(prefix + "ambientColor"), FloatBufferUtils.vector3ToFloatBuffer(ambientColor));
        glUniform3fv(program.getLocation(prefix + "diffuseColor"), FloatBufferUtils.vector3ToFloatBuffer(diffuseColor));
        glUniform3fv(program.getLocation(prefix + "specularColor"), FloatBufferUtils.vector3ToFloatBuffer(specularColor));
        glUniform1f(program.getLocation(prefix + "constant"), constant);
        glUniform1f(program.getLocation(prefix + "linear"), linear);
        glUniform1f(program.getLocation(prefix + "quadratic"), quadratic);
    }

    @Override
    protected Type getType() {
        return Type.LIGHT_SOURCE;
    }
}
