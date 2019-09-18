package artgellary.room;


import artgellary.room.paintings.PaintingSpot;
import artgellary.room.paintings.PaintingWall;
import engine.components.Camera;
import engine.components.LightSource;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.graphics.shader.ShaderManager;
import engine.models.Component;
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
    private RoomLight roomLight;

    @Override
    public void start() {
        try {
            addComponent(new MeshRenderer(ShaderManager.getInstance().getNormalMappingShader(),"obj/room/Room.mtl"));
            addComponent(new MeshFilter("obj/room/Room.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        roomLight = new RoomLight();
        addChild(roomLight);

        observer = new Observer();
        addChild(observer);

        paintingWalls = new PaintingWall[2];
        for(int i = 0; i < 2; i++) {
            paintingWalls[i] = new PaintingWall();
            addChild(paintingWalls[i]);
            paintingWalls[i].getTransform().setRotation(new Vector3f(0.0f, (i + 1)*(float)Math.PI/2.0f, 0.0f));
        }

        //Inicjalizacja kolizji ścian które są wzdłóż osi X

        //            float [] wallsCollidersSize = { 4f,4f, 1.6f, 2f, 2f,1.6f,1.6f,2f,2f,1.6f };
        //            float [] wallsCollidersPosition = { 0f,-2f  ,2f,0f   ,1.2f,2f   ,0.4f,3f   ,-0.4f,3f    ,-1.2f,2f    -2f,1.2f    ,-3f,0.4f   ,-3f,-0.4f  ,-2f,-1.2f };

        float [] XwallsCollidersLength = { 4f };
        float [] XwallsCollidersPosition = { 0f,-2f };
        initializeRoomCollision(XwallsCollidersLength, XwallsCollidersPosition, false);

        //Inicjalizacja kolizji ścian które są wzdłóż osi Z

        float [] ZwallsCollidersLength = { 4f };
        float [] ZwallsCollidersPosition = { 2f,0f };
        initializeRoomCollision(ZwallsCollidersLength, ZwallsCollidersPosition, true);
    }

    public void prepareLight(Camera camera) {
        LightSource lightSource = (LightSource)roomLight.getComponent(Component.Type.LIGHT_SOURCE);
        lightSource.renderCubeMap(this, camera);
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

    private void initializeRoomCollision(float [] wallsCollidersLength,float [] wallsCollidersPosition,boolean calculateAlongZAxis){
        int j=0;
        while(j<wallsCollidersPosition.length) {
            System.out.println(j+ "   "+j/2);
            ColliderWall colliderWall = new ColliderWall(wallsCollidersLength[j/2]);
            addChild(colliderWall);
            colliderWall.getTransform().setPosition(new Vector3f(wallsCollidersPosition[j++], 0f, wallsCollidersPosition[j++]));

            //Jesli jest to oś Z to obracamy wszystkie kolidery o 90 stopni (taka jest po prostu w naszym modelu matematycznym)
            if (calculateAlongZAxis) colliderWall.getTransform().setRotation(new Vector3f(0.0f, (float)Math.PI/2.0f, 0.0f));
        }
    }
}
