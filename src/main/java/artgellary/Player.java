package artgellary;

import engine.components.Camera;
import engine.components.collision.CircleCollision;
import engine.components.collision.Collider;
import engine.models.Component;
import engine.models.GameObject;
import engine.utils.InputDetector;
import engine.utils.Time;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

// Obiekt opisujący postać którym steruje gracz.
public class Player extends GameObject {

    @Setter
    private GameObject rootObject;
    private CircleCollision playerCollider;
    MainCamera mainCamera;
    float speed = 0.0035f;

    public Player(GameObject rootObject) {
        super();
        this.rootObject = rootObject;
    }

    @Override
    public void start() {
        mainCamera = new MainCamera();
        addChild(mainCamera);
        playerCollider = new CircleCollision(0.3f);
        addComponent(playerCollider);

        getTransform().setPosition(new Vector3f(4.0f, 1.0f, -4.0f));
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        Camera camera = mainCamera.getCamera();
        Vector3f oldPosition = new Vector3f().add(getTransform().getAbsolutePosition());

        Vector3f front3D = new Vector3f();
        // Pobranie wektora na wprost kamery.
        front3D.add(camera.generateFrontVector());
        // Przeniesienie go na dwa wymiary
        Vector2f front2D = new Vector2f(front3D.x, front3D.z).normalize();
        // Pomnożenie go przez prędkość
        front2D.mul(Time.DELTA_TIME*speed);
        Vector3f shift = new Vector3f();
        // Współrzędna y jest niezmieniana (oznaczało by to że postać może latać!)
        if(InputDetector.isKeyPressed(GLFW_KEY_W)) {
            shift.add(new Vector3f(front2D.x , 0.0f, front2D.y));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_S)) {
            shift.add(new Vector3f(-front2D.x , 0.0f, -front2D.y));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_D)) {
            shift.add(new Vector3f(-front2D.y , 0.0f, front2D.x));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_A)) {
            shift.add(new Vector3f(front2D.y , 0.0f, -front2D.x));
        }

        getTransform().shiftBy(shift);


        //COLLIDER ENGINE
        rootObject.executeForEvery((GameObject gameObject)->{
            if (gameObject != this){
                Component c = gameObject.getComponent(Component.Type.COLLIDER);
                if(c != null) {
                    Collider testCollider = (Collider) c;
                    if(playerCollider.isCollision(testCollider)){
                        System.out.println("KOLIZJA");
                        getTransform().setPosition(oldPosition);
                    }
                }
            }
        });
    }
}
