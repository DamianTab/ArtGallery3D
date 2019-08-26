package artgellary;

import artgellary.shaders.ConstantShader;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.models.GameObject;
import org.joml.Vector3f;

import java.io.IOException;

public class ArtGalleryRootObject extends GameObject {

    Camera camera;

    @Override
    public void start() {

        try {
            addComponent(new MeshRenderer(new ConstantShader(), new Material()));
            addComponent(new MeshFilter(new Mesh("box.obj")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        camera = new Camera(this.getTransform());
        addComponent(camera);

        camera.getTransform().setPosition(new Vector3f( -5.0f, 0.0f, 0.0f));
    }

    @Override
    public void update() {

    }
}
