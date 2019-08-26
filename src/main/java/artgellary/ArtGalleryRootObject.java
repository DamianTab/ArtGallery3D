package artgellary;

import artgellary.shaders.ConstantShader;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.material.Material;
import engine.graphics.mesh.Mesh;
import engine.models.GameObject;

import java.io.IOException;

public class ArtGalleryRootObject extends GameObject {

    Camera camera;

    @Override
    public void start() {
        camera = new Camera();
        addComponent(camera);

        try {
            addComponent(new MeshRenderer(new ConstantShader(), new Material()));
            addComponent(new MeshFilter(new Mesh("box.obj")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}
