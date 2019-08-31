package artgellary;

import engine.models.GameObject;
import engine.utils.InputDetector;
import engine.utils.Time;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class Player extends GameObject {

    MainCamera mainCamera;
    float speed = 0.005f;

    @Override
    public void start() {
        mainCamera = new MainCamera();
        addChild(mainCamera);
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        Vector3f cameraRotation = mainCamera.getTransform().getRotation();
        Vector3f shift = new Vector3f();
        if(InputDetector.isKeyPressed(GLFW_KEY_W)) {
            float x = (float)Math.cos(cameraRotation.y)*speed* Time.DELTA_TIME;
            float z = (float)Math.sin(cameraRotation.y)*speed* Time.DELTA_TIME;
            shift.add(new Vector3f(x , 0.0f, z));
        }
        if(InputDetector.isKeyPressed(GLFW_KEY_S)) {
            float x = -(float)Math.cos(cameraRotation.y)*speed* Time.DELTA_TIME;
            float z = -(float)Math.sin(cameraRotation.y)*speed* Time.DELTA_TIME;
            shift.add(new Vector3f(x , 0.0f, z));
        }
        getTransform().shiftBy(shift);
    }
}
