package artgellary;

import engine.utils.InputDetector;
import engine.components.Camera;
import engine.models.GameObject;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MainCamera extends GameObject {
    @Getter
    private Camera camera;

    private boolean first = true;
    private float lastMouseX, lastMouseY;
    private float offsetX, offsetY;
    private float yaw, pitch;
    private final float mouse_sensitivity = 0.15f;


    @Override
    public void start() {
        camera = new Camera();
        addComponent(camera);
    }

    @Override
    public void update() {
        updateMouse();
        //updateKeyboard();
    }

    private void updateMouse(){

        Vector2f vector2f = InputDetector.getMousePosition();
        float mouseX = vector2f.x;
        float mouseY = vector2f.y;


        if (first){
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            first = false;
        }
        offsetX = mouseX - lastMouseX;
        offsetY = lastMouseY - mouseY;
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        offsetX *= mouse_sensitivity;
        offsetY *= mouse_sensitivity;

        yaw += offsetX;
        pitch += offsetY;

        if(pitch > 89.0f)
            pitch = 89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;

        float x = (float)Math.toRadians((double) pitch);
        float y = (float)Math.toRadians((double) yaw);
        getTransform().setRotation(new Vector3f(x, y, 0.0f));
    }
}
