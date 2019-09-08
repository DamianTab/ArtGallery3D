package artgellary;

import engine.components.Camera;
import engine.models.GameObject;
import engine.utils.InputDetector;
import engine.utils.Time;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

// Obiekt opisujący postać którym steruje gracz.
public class Player extends GameObject {

    MainCamera mainCamera;
    float speed = 0.005f;

    @Override
    public void start() {
        mainCamera = new MainCamera();
        addChild(mainCamera);
        getTransform().setPosition(new Vector3f(3.0f, 1.0f, 0.0f));
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        Camera camera = mainCamera.getCamera();
        Vector3f front = new Vector3f();
        // Pobranie wektora na wprost kamery.
        front.add(camera.generateFrontVector());
        // Pomnożenie go przez prędkość
        front.mul(Time.DELTA_TIME*speed);
        Vector3f shift = new Vector3f();
        // Współrzędna y jest niezmieniana (oznaczało by to że postać może latać!)
        if(InputDetector.isKeyPressed(GLFW_KEY_W)) {
            shift.add(new Vector3f(front.x , 0.0f, front.z));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_S)) {
            shift.add(new Vector3f(-front.x , 0.0f, -front.z));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_D)) {
            shift.add(new Vector3f(-front.z , 0.0f, front.x));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_A)) {
            shift.add(new Vector3f(front.z , 0.0f, -front.x));
        }
        getTransform().shiftBy(shift);
    }
}
