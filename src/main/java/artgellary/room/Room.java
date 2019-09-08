package artgellary.room;


import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.GameObject;
import engine.utils.Rand;
import engine.utils.Time;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

// Obiekt opisujący pokój wraz z korytarzami. Jest ich 4 i każdy kolejny jest obrózony o 90 stopni.
public class Room extends GameObject {

    public static final float MESH_WIDTH = 8.0f;
    private Observer observer;
    private float counter = 100000;
    private PaintingWall[] paintingWalls;

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getNormalMappingShader(),"obj/room/Room.mtl"));
            addComponent(new MeshFilter("obj/room/Room.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        RoomLight roomLight = new RoomLight();
        addChild(roomLight);

        observer = new Observer();
        addChild(observer);

        paintingWalls = new PaintingWall[2];
        for(int i = 0; i < 2; i++) {
            paintingWalls[i] = new PaintingWall();
            addChild(paintingWalls[i]);
            paintingWalls[i].getTransform().setRotation(new Vector3f(0.0f, (i + 1)*(float)Math.PI/2.0f, 0.0f));
        }
    }


    @Override
    public void update() {
        counter += Time.DELTA_TIME;
        if(counter > 8000) {
            counter = 0;
            PaintingSpot spot = getRandomPaintingSpot();
            Matrix4f relativeMatrix = spot.getTransform().getParent().getRelativeMatrix(this.getTransform());
            Vector4f v4 = new Vector4f(spot.getObservePosition(), 1.0f).mul(relativeMatrix);
            Vector3f v = new Vector3f(v4.x, v4.y, v4.z).div(v4.w);
            float angle = spot.getTransform().getParent().getRotation().y;
            observer.goTo(v, angle);
        }
    }

    private PaintingSpot getRandomPaintingSpot() {
        int index = Rand.RANDOM.nextInt(6);
        if(index < 3) {
            return paintingWalls[0].getPaintingSpots()[index];
        }
        else {
            return paintingWalls[1].getPaintingSpots()[index - 3];
        }
    }
}
