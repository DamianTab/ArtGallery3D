package artgellary.room.paintings;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;

// Obiekt opisujący obraz wraz z ramą.
public class Painting  extends GameObject {
    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(),"obj/frame/10305_picture_frame_V2_max2011_it2.mtl"));
            addComponent(new MeshFilter("obj/frame/10305_picture_frame_V2_max2011_it2.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Canvas canvas = new Canvas();
        addChild(canvas);
    }

    @Override
    public void update() {

    }
}
