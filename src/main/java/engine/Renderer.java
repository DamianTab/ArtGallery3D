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

    public void draw(GameObject gameObject){
        Camera camera = (Camera) gameObject.getComponent(Component.Type.CAMERA);
        if(camera != null) {
            gameObject.executeForEvery((GameObject gameobject) -> {
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
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        camera.getPerspectiveMatrix().get(matrixBuffer);
        glUniformMatrix4fv(program.getLocation("p_matrix"), false, matrixBuffer);
        camera.getViewMatrix().get(matrixBuffer);
        glUniformMatrix4fv(program.getLocation("v_matrix"), false, matrixBuffer);
        o.getTransform().getAbsoluteMatrix().get(matrixBuffer);
        glUniformMatrix4fv(program.getLocation("m_matrix"), false, matrixBuffer);

        FloatBuffer vector3Buffer = BufferUtils.createFloatBuffer(3);
        camera.getTransform().getAbsolutePosition().get(vector3Buffer);

        glUniform3fv(program.getLocation("viewPos"), vector3Buffer);
    }
}
