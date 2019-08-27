package engine;

import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderProgram;
import engine.models.Component;
import engine.models.GameObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Renderer {

    public void draw(GameObject root){
        Camera camera = (Camera) root.getComponent(Component.Type.CAMERA);
        if(camera != null) {
            camera.generateViewAndPerspective();
            root.executeForEvery((GameObject gameObject) -> {
                MeshRenderer meshRenderer = (MeshRenderer) gameObject.getComponent(Component.Type.MESH_RENDERER);
                MeshFilter meshFilter = (MeshFilter) gameObject.getComponent(Component.Type.MESH_FILTER);
                if (meshRenderer != null && meshFilter != null) {
                    meshRenderer.use();
                    setUniforms(gameObject, meshRenderer.getShader(), camera);

                    //Finally draw a mesh
                    meshFilter.getMesh().draw();
                }
            });
        }
        else {
            System.err.println("Root object does not have a camera!");
        }
    }

    //Set uniforms in shader
    private void setUniforms(GameObject o, ShaderProgram program, Camera camera) {
        FloatBuffer pMatrixBuffer = BufferUtils.createFloatBuffer(16);
        camera.getPerspectiveMatrix().get(pMatrixBuffer);
        glUniformMatrix4fv(program.getLocation("p_matrix"), false, pMatrixBuffer);
        FloatBuffer vMatrixBuffer = BufferUtils.createFloatBuffer(16);
        camera.getViewMatrix().get(vMatrixBuffer);
        glUniformMatrix4fv(program.getLocation("v_matrix"), false, vMatrixBuffer);
        FloatBuffer mMatrixBuffer = BufferUtils.createFloatBuffer(16);
        o.getTransform().getAbsoluteMatrix().get(mMatrixBuffer);
        glUniformMatrix4fv(program.getLocation("m_matrix"), false, mMatrixBuffer);

        FloatBuffer vector3Buffer = BufferUtils.createFloatBuffer(3);
        camera.getTransform().getAbsolutePosition().get(vector3Buffer);

        glUniform3fv(program.getLocation("viewPos"), vector3Buffer);
    }
}
