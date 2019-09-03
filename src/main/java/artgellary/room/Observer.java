package artgellary.room;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import org.joml.Vector3f;

public class Observer extends GameObject {

    Painting target;

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



    public void goToPainting(Painting target) {
        this.target = target;
    }

    @Override
    public void update() {

    }
}
