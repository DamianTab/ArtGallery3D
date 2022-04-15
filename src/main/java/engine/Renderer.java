package engine;

import engine.components.Camera;
import engine.components.LightSource;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.Framebuffer;
import engine.graphics.shader.ShaderManager;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.DepthCubeMap;
import engine.graphics.texture.Texture;
import engine.models.Component;
import engine.models.GameObject;
import engine.utils.FloatBufferUtils;
import engine.utils.IntegerWrapper;

import java.util.List;

import static org.lwjgl.opengl.GL20.*;

// Klasa renderująca obiekt
public class Renderer {

    private WindowHandler windowHandler;

    public Renderer(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    public void draw(GameObject root){
        Camera camera = findCamera(root);
        if(camera != null) {
            camera.generateViewAndPerspective();
//            Renderowanie dla każdego obiektu cubeMapy - zrezygnowaliśmy bo zabierało duzo fps i słaby efekt
//            prepareLights(root, camera);
            windowHandler.setViewPort();
            glClear(GL_DEPTH_BUFFER_BIT);
            root.executeForEvery((GameObject gameObject) -> {
                MeshRenderer meshRenderer = (MeshRenderer) gameObject.getComponent(Component.Type.MESH_RENDERER);
                MeshFilter meshFilter = (MeshFilter) gameObject.getComponent(Component.Type.MESH_FILTER);
                // Draw only is material and mesh are present
                if (meshRenderer != null && meshFilter != null) {
                    render(meshRenderer, meshFilter, gameObject, camera, root);
                }
            });
        }
        else {
            System.err.println("Camera has not been found!");
        }
    }

    private void render(MeshRenderer meshRenderer, MeshFilter meshFilter, GameObject gameObject, Camera camera, GameObject root) {
        // Use shader
        meshRenderer.use();
        // Set main uniforms
        setUniforms(gameObject, meshRenderer.getShader(), camera);
        // Set light uniforms
        useLights(root, meshRenderer.getShader());
        //Finally draw a mesh
        meshFilter.getMesh().draw(meshRenderer.getShader(), meshRenderer.getMaterial());
    }

    // Find any object with a Camera component
    private Camera findCamera(GameObject root) {
        GameObject cameraObject = root.find((GameObject gameObject) -> gameObject.getComponent(Component.Type.CAMERA) != null);
        return (Camera)cameraObject.getComponent(Component.Type.CAMERA);
    }

    // Find all light components and pass them to shaders
    private void useLights(GameObject root, ShaderProgram program) {
        // I use some wrapper because I can't pass a local primitive type to a lambda.
        // This is equivalent to an int pointer
        IntegerWrapper i = new IntegerWrapper(0);
        root.executeForEvery((GameObject gameObject) -> {
            Component c = gameObject.getComponent(Component.Type.LIGHT_SOURCE);
            if(c != null) {
                LightSource lightSource = (LightSource)c;
                lightSource.use(program, i.value);
                i.value++;
            }
        });
    }

    private void prepareLights(GameObject root, Camera camera) {
        root.executeForEvery((GameObject gameObject) -> {
            Component c = gameObject.getComponent(Component.Type.LIGHT_SOURCE);
            if(c != null) {
                LightSource lightSource = (LightSource)c;
                lightSource.renderCubeMap(root, camera);
            }
        });
    }

    //Set uniforms in shader
    private void setUniforms(GameObject o, ShaderProgram program, Camera camera) {
        glUniformMatrix4fv(program.getLocation("p_matrix"), false, FloatBufferUtils.matrix4ToFloatBuffer(camera.getPerspectiveMatrix()));
        glUniformMatrix4fv(program.getLocation("v_matrix"), false, FloatBufferUtils.matrix4ToFloatBuffer(camera.getViewMatrix()));
        glUniformMatrix4fv(program.getLocation("m_matrix"), false, FloatBufferUtils.matrix4ToFloatBuffer(o.getTransform().getAbsoluteMatrix()));
        glUniform3fv(program.getLocation("viewPos"), FloatBufferUtils.vector3ToFloatBuffer(camera.getTransform().getAbsolutePosition()));
        camera.setFarPlaneUniform(program);
    }
}
