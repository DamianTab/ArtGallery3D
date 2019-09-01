package artgellary;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import org.joml.Vector3f;

public class Observer extends GameObject {
    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(),"obj/observer/StickFigurea.mtl"));
            addComponent(new MeshFilter("obj/observer/StickFigurea.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTransform().setScale(0.015f);
    }

    @Override
    public void update() {

    }
}
