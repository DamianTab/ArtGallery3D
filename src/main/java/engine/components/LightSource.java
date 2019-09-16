package engine.components;

import engine.graphics.Framebuffer;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.MeshPart;
import engine.graphics.shader.ShaderManager;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.DepthCubeMap;
import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureManager;
import engine.models.Component;
import engine.models.GameObject;
import engine.utils.FloatBufferUtils;
import lombok.Data;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Vector;

import static org.lwjgl.opengl.GL20.*;

// A simple class representing point light
@Data
public class LightSource extends Component {
    // These parameters decribe how light will lose intensity over distance.
    private float constant = 0.0f; // Not dependent on distance
    private float linear = 0.0f; // Linearly dependent on distance
    private float quadratic = 2.0f; // Distance squared
    private Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private Texture shadowCubeMap;

    private Framebuffer depthFrameBuffer;
    private ShaderProgram shadowShader;

    public LightSource() {
        depthFrameBuffer = new Framebuffer(TextureManager.getInstance().nextDepthMap());
        shadowCubeMap = depthFrameBuffer.getTexture();
        try {
            shadowShader = ShaderManager.getInstance().getShader("glsl/depth/v_depth.glsl", "glsl/depth/f_depth.glsl", "glsl/depth/g_depth.glsl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set all lightsource properties to shader.
    // i = index in array
    public void use(ShaderProgram program, int i) {
        String prefix = "lights[" + i + "].";
        glUniform3fv(program.getLocation(prefix + "ambientColor"), FloatBufferUtils.vector3ToFloatBuffer(ambientColor));
        glUniform3fv(program.getLocation(prefix + "diffuseColor"), FloatBufferUtils.vector3ToFloatBuffer(diffuseColor));
        glUniform3fv(program.getLocation(prefix + "specularColor"), FloatBufferUtils.vector3ToFloatBuffer(specularColor));
        glUniform1f(program.getLocation(prefix + "constant"), constant);
        glUniform1f(program.getLocation(prefix + "linear"), linear);
        glUniform1f(program.getLocation(prefix + "quadratic"), quadratic);
        glUniform3fv(program.getLocation(prefix + "position"), FloatBufferUtils.vector3ToFloatBuffer(getTransform().getAbsolutePosition()));
        shadowCubeMap.use(program, prefix + "shadowCubeMap");
    }

    private void useShadowTransforms(ShaderProgram program) {
        Matrix4f[] shadowTransforms = generateShadowMatrices();
        for(int j = 0; j < 6; j++) {
            glUniformMatrix4fv(program.getLocation("shadowMatrices[" + j + "]"), false, FloatBufferUtils.matrix4ToFloatBuffer(shadowTransforms[j]));
        }
    }

    public void renderCubeMap(GameObject root, Camera camera) {
        depthFrameBuffer.setViewPort();
        depthFrameBuffer.bind();
        glClear(GL_DEPTH_BUFFER_BIT);

        shadowShader.use();
        glUniform3fv(shadowShader.getLocation("lightPos"), FloatBufferUtils.vector3ToFloatBuffer(getTransform().getAbsolutePosition()));
        camera.setFarPlaneUniform(shadowShader);
        useShadowTransforms(shadowShader);

        root.executeForEvery((GameObject gameObject) -> {
            MeshRenderer meshRenderer = (MeshRenderer) gameObject.getComponent(Component.Type.MESH_RENDERER);
            MeshFilter meshFilter = (MeshFilter) gameObject.getComponent(Component.Type.MESH_FILTER);
            // Draw only is material and mesh are present
            if (meshRenderer != null && meshFilter != null) {
                Mesh mesh = meshFilter.getMesh();
                glUniformMatrix4fv(shadowShader.getLocation("m_matrix"), false, FloatBufferUtils.matrix4ToFloatBuffer(gameObject.getTransform().getAbsoluteMatrix()));
                mesh.draw();
            }
        });
        depthFrameBuffer.unbind();
    }

    private Matrix4f[]  generateShadowMatrices() {
        float aspect = 1.0f;
        float near = 0.01f;
        float far = 100.0f;
        Vector3f lightPos = getTransform().getAbsolutePosition();

        Matrix4f shadowProj = new Matrix4f().identity().perspective((float)Math.toRadians(90.0),aspect, near, far);
        Matrix4f[] shadowtransforms = new Matrix4f[6];
        shadowtransforms[0] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(1.0f, 0.0f, 0.0f), new Vector3f()), new Vector3f(0.0f, -1.0f, 0.0f)), new Matrix4f());
        shadowtransforms[1] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(-1.0f, 0.0f, 0.0f), new Vector3f()), new Vector3f(0.0f, -1.0f, 0.0f)), new Matrix4f());
        shadowtransforms[2] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f()), new Vector3f(0.0f, 0.0f, 1.0f)), new Matrix4f());
        shadowtransforms[3] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(0.0f, -1.0f, 0.0f), new Vector3f()), new Vector3f(0.0f, 0.0f, -1.0f)), new Matrix4f());
        shadowtransforms[4] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f()), new Vector3f(0.0f, -1.0f, 0.0f)), new Matrix4f());
        shadowtransforms[5] = shadowProj.mul(new Matrix4f().identity().lookAt(lightPos, lightPos.add(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f()), new Vector3f(0.0f, -1.0f, 0.0f)), new Matrix4f());
        return shadowtransforms;
    }

    @Override
    protected Type getType() {
        return Type.LIGHT_SOURCE;
    }
}
