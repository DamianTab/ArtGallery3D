package artgellary;

import engine.utils.InputDetector;
import engine.components.Camera;
import engine.models.GameObject;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MainCamera extends GameObject {

    Camera camera;

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

    private void updateKeyboard(){

        //TODO NIE WIEM JAK OGARNAC camRight itp.


//        movementSpeed = baseMovementSpeed;
//
//        if (InputDetector.isKeyPressed(GLFW_KEY_LEFT)) speed_x=-PI/2;
//        else if (InputDetector.isKeyPressed(GLFW_KEY_RIGHT)) speed_x=PI/2;
//        else speed_x=0;
//
//        if (InputDetector.isKeyPressed(GLFW_KEY_UP)) speed_y=PI/2;
//        else if (InputDetector.isKeyPressed(GLFW_KEY_DOWN)) speed_y=-PI/2;
//        else speed_y=0;
//
//        //Because of the camFront vector getting close to 0 while looking 90 degrees up or down it's hard to get any kind of forward/backwards movement
//        //Therefore we are recalculating the camRight vector on every frame, then taking the cross product of camRight and camUp vector
//        //and using that as the direction vector instead
//
//        camRight.set(camFront).cross(camUp).normalize();
//        if (InputDetector.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
//            movementSpeed+= 50.0f;
//
//        //W
//        if (InputDetector.isKeyPressed(GLFW_KEY_W) && !InputDetector.isKeyPressed(GLFW_KEY_D) && !InputDetector.isKeyPressed(GLFW_KEY_A) && !InputDetector.isKeyPressed(GLFW_KEY_S)) {
//            camPos.x -= new Vector3f().set(camRight).cross(camUp).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z -= new Vector3f().set(camRight).cross(camUp).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //W+D
//        if (InputDetector.isKeyPressed(GLFW_KEY_W) && InputDetector.isKeyPressed(GLFW_KEY_D) && !InputDetector.isKeyPressed(GLFW_KEY_S)) {
//            camPos.x -= new Vector3f().set(camRight).cross(camUp).sub(camRight).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z -= new Vector3f().set(camRight).cross(camUp).sub(camRight).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //W+A
//        if (InputDetector.isKeyPressed(GLFW_KEY_W) && InputDetector.isKeyPressed(GLFW_KEY_A) && !InputDetector.isKeyPressed(GLFW_KEY_S)) {
//            camPos.x -= new Vector3f().set(camRight).cross(camUp).add(camRight).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z -= new Vector3f().set(camRight).cross(camUp).add(camRight).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //S
//        if (InputDetector.isKeyPressed(GLFW_KEY_S) && !InputDetector.isKeyPressed(GLFW_KEY_D) && !InputDetector.isKeyPressed(GLFW_KEY_A) && !InputDetector.isKeyPressed(GLFW_KEY_W)) {
//            camPos.x += new Vector3f().set(camRight).cross(camUp).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z += new Vector3f().set(camRight).cross(camUp).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //S+D
//        if (InputDetector.isKeyPressed(GLFW_KEY_S) && InputDetector.isKeyPressed(GLFW_KEY_D) && !InputDetector.isKeyPressed(GLFW_KEY_W)) {
//            camPos.x += new Vector3f().set(camRight).cross(camUp).add(camRight).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z += new Vector3f().set(camRight).cross(camUp).add(camRight).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //S+A
//        if (InputDetector.isKeyPressed(GLFW_KEY_S) && InputDetector.isKeyPressed(GLFW_KEY_A) && !InputDetector.isKeyPressed(GLFW_KEY_W)) {
//            camPos.x += new Vector3f().set(camRight).cross(camUp).sub(camRight).normalize().x * (float) glfwGetTime() * movementSpeed;
//            camPos.z += new Vector3f().set(camRight).cross(camUp).sub(camRight).normalize().z * (float) glfwGetTime() * movementSpeed;
//        }
//        //D
//        if (InputDetector.isKeyPressed(GLFW_KEY_D) && !InputDetector.isKeyPressed(GLFW_KEY_W) && !InputDetector.isKeyPressed(GLFW_KEY_S) && !InputDetector.isKeyPressed(GLFW_KEY_A)) {
//            camPos.add(new Vector3f().set(camRight).mul((float) glfwGetTime() * movementSpeed));
//        }
//        //A
//        if (InputDetector.isKeyPressed(GLFW_KEY_A) && !InputDetector.isKeyPressed(GLFW_KEY_W) && !InputDetector.isKeyPressed(GLFW_KEY_S) && !InputDetector.isKeyPressed(GLFW_KEY_D)) {
//            camPos.sub(new Vector3f().set(camRight).mul((float) glfwGetTime() * movementSpeed));
//        }
//            //UP
//        if (InputDetector.isKeyPressed(GLFW_KEY_SPACE) && !InputDetector.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
//            camPos.add(new Vector3f().set(camUp).normalize().mul((float)glfwGetTime()*movementSpeed));
//            //DOWN
//        if (InputDetector.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && !InputDetector.isKeyPressed(GLFW_KEY_SPACE))
//            camPos.sub(new Vector3f().set(camUp).normalize().mul((float)glfwGetTime()*movementSpeed));


    }
}
