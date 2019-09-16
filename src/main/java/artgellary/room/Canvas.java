package artgellary.room;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.material.MaterialPart;
import engine.graphics.material.SingleMaterial;
import engine.graphics.mesh.MeshManager;
import engine.graphics.shader.ShaderManager;
import engine.graphics.texture.FileTexture;
import engine.graphics.texture.TextureManager;
import engine.models.GameObject;
import engine.utils.Rand;
import org.joml.Vector3f;

import java.io.IOException;

// Obiekt opisujący płótno czyli obrazek wewnątrz ramki.
public class Canvas extends GameObject {

    public final static int PAINTING_NUMBER = 11;

    @Override
    public void start() {
        try {
            MaterialPart materialPart = new MaterialPart();
            materialPart.setDiffuseMap(getRandomPainting());
            SingleMaterial material = new SingleMaterial(materialPart);

            addComponent(new MeshRenderer(ShaderManager.getInstance().getStandardShader(), material));
            addComponent(new MeshFilter(MeshManager.getInstance().getMesh("obj/canvas/plane.obj")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTransform().setRotation(new Vector3f(0.0f, 0.0f, (float)Math.toRadians(180)));
        getTransform().setScale(new Vector3f(7.0f, 1.0f, 10.0f));
        getTransform().setPosition(new Vector3f(0.0f, -0.1f, 18.0f));
    }

    @Override
    public void update() {

    }

    // Obrazek jest losowany z puli
    private FileTexture getRandomPainting() throws IOException {
        int index = Rand.RANDOM.nextInt(PAINTING_NUMBER) + 1;
        return TextureManager.getInstance().getTexture("paintings/p" + index + ".png", FileTexture.Type.DIFFUSE);
    }
}
